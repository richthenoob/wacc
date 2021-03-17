package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.*;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;

import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.exprnodes.ArrayElementNode;
import ic.doc.frontend.nodes.exprnodes.ClassFieldVariableNode;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.nodes.exprnodes.PairElementNode;
import ic.doc.frontend.nodes.exprnodes.VariableNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.*;

import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.instructions.SingleDataTransfer.LDR;

public class AssignmentNode extends StatNode {

  private final ExprNode lhs;
  private final ExprNode rhs;
  private final boolean isDeclaration;
  private final SymbolTable symbolTable;

  public AssignmentNode(
      ExprNode lhs, ExprNode rhs, boolean isDeclaration,
      SymbolTable symbolTable) {
    this.lhs = lhs;
    this.rhs = rhs;
    this.isDeclaration = isDeclaration;
    this.symbolTable = symbolTable;
  }

  public ExprNode getLhs() {
    return lhs;
  }

  public ExprNode getRhs() {
    return rhs;
  }

  public SymbolTable getSymbolTable() {
    return symbolTable;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

    if (isDeclaration) {
      VariableNode lhsVar = (VariableNode) lhs;
      String name = lhsVar.getName();
      SymbolKey key = new SymbolKey(name, KeyTypes.VARIABLE);
      /* If node corresponds to declarative assignment,
      variable must not have been already defined earlier */
      if (symbolTable.lookup(key) != null) {
        visitor.getSemanticErrorList()
            .addScopeException(ctx, true, "Variable", name);
      } else {
        symbolTable.add(key, new VariableIdentifier(lhs.getType()));
      }
    }

    /* Checks if types of lhs and rhs are the same.
     * If their types are errors, an error should already have been thrown
     * for the variable not being defined in the scope
     * and thus further error throwing is unnecessary */
    if (!(Type.checkTypeCompatibility(rhs.getType(), lhs.getType(),
        visitor.getCurrentSymbolTable()))
        && !(lhs.getType() instanceof ErrorType)
        && !(rhs.getType() instanceof ErrorType)) {

      String firstApostrophe = "";
      String secondApostrophe = "";
      boolean suggest = false;

      /* Suggesting fixes to frequently occurring mistakes
       * that would lead to type mismatches */
      if (rhs.getType() instanceof StringType) {
        /* String wrongly provided */
        secondApostrophe = "\"";
        if (lhs.getType() instanceof CharType && rhs.getInput().length() == 1) {
          /* e.g. char c = "a" */
          firstApostrophe = "'";
        } else {
          /* e.g. bool c = "true" */
          firstApostrophe = "";
        }
        suggest = true;
      } else if (lhs.getType() instanceof StringType) {
        /* String expected */
        firstApostrophe = "\"";
        if (rhs.getType() instanceof CharType) {
          /* e.g. String s = 'a' */
          secondApostrophe = "'";
        } else {
          /* e.g. String greeting = hey */
          secondApostrophe = "'";
        }
        suggest = true;
      }

      String suggestion =
          suggest
              ? ("Did you mean "
              + firstApostrophe
              + rhs.getInput()
              + firstApostrophe
              + " instead of "
              + secondApostrophe
              + rhs.getInput()
              + secondApostrophe
              + "?")
              : "";

      /* Prints out error message with suggestions
       * if suggestions are applicable */
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              rhs.getInput(),
              lhs.getType().toString(),
              rhs.getType().toString(),
              suggestion,
              "assignment");
    }
  }

  @Override
  public void translate(Context context) {

    int offset;
    RegisterOperand base;
    if (lhs instanceof PairElementNode) {
      translateRHS(context);
      offset = translateLHS(context);
      base = lhs.getRegister();
    } else {
      offset = translateLHS(context);
      base = lhs.getRegister();
      translateRHS(context);
    }

    /* Stores 1 byte if char or bool with STRB, and 4 bytes otherwise with STR */
    String strCond = lhs.getType().getVarSize() == 1 ? "B" : "";
    context.addToCurrentLabel(
        SingleDataTransfer.STR(
            rhs.getRegister(),
            new PreIndexedAddressOperand(base)
                .withExpr(new ImmediateOperand<>(offset)
                    .withPrefixSymbol("#")))
            .withCond(strCond));

    if (!lhs.getRegister().equals(RegisterOperand.SP)) {
      context.freeRegister(base.getValue());
    }
    context.freeRegister(rhs.getRegister().getValue());
  }

  /* Translate LHS must always return an offset and set its own node's
   * register.
   * The main translate function will copy values from RHS into
   * nodeRegister + offset. */
  private int translateLHS(Context context) {
    if (lhs instanceof ClassFieldVariableNode) {
      return ((ClassFieldVariableNode) lhs)
          .translateClassFieldVariableLHS(context);
    }

    if (lhs instanceof ArrayElementNode) {
      ((ArrayElementNode) lhs).translateArrayElemLHS(context);
      return 0;
    }

    if (lhs instanceof PairElementNode) {
      return ((PairElementNode) lhs).translatePairElementNodeLHS(context);
    }

    if (isDeclaration) {
      return translateLHSDeclaration(context);
    } else {
      return translateLHSNonDeclaration(context);
    }
  }

  /* Must always set register of RHS and load it with the correct value. */
  private void translateRHS(Context context) {
    if (rhs instanceof ClassFieldVariableNode) {
      ((ClassFieldVariableNode) rhs).translateClassFieldVariableRHS(context);
      return;
    }

    if (rhs instanceof ArrayElementNode) {
      ((ArrayElementNode) rhs).translateArrayElemRHS(context);
      return;
    }

    if (rhs instanceof PairElementNode) {
      ((PairElementNode) rhs).translatePairElementNodeRHS(context);

      /* Dereference heap address to find actual value. */
      RegisterOperand exprRegister = rhs.getRegister();
      context.addToCurrentLabel(LDR(exprRegister,
          new PreIndexedAddressOperand(exprRegister)));
      return;
    }

    rhs.translate(context);
  }

  /* New declaration, e.g. int i = 5
   * Returns the offset from the SP at which the RHS shd be moved into.
   * (Should always return 0 since we are always pushing into SP directly) */
  private int translateLHSDeclaration(Context context) {

    /* Sanity check. */
    assert (lhs instanceof VariableNode);

    /* First parse the LHS as a variable node and look it up. This should
     * already have been declared in the symbol table when we constructed
     * the AST. */
    VariableNode lhsVar = (VariableNode) lhs;
    String name = lhsVar.getName();
    SymbolKey key = new SymbolKey(name, KeyTypes.VARIABLE);
    VariableIdentifier id = (VariableIdentifier) symbolTable.lookupAll(key);

    /* Regardless of type, we need to increment offsets in this scope,
     * since we are going to be storing a new item on the stack. */
    int sizeOfVarOnStack = lhs.getType().getVarSize();
    symbolTable.incrementOffset(sizeOfVarOnStack);
    symbolTable.incrementTableSizeInBytes(sizeOfVarOnStack);
    id.setActivated();

    /* Finally, add an instruction to move the stack pointer and "allocate"
     * space for our new variable. */
    context.addToCurrentLabel(
        DataProcessing.SUB(
            RegisterOperand.SP,
            RegisterOperand.SP,
            new ImmediateOperand<>(sizeOfVarOnStack).withPrefixSymbol("#")));

    lhsVar.setRegister(RegisterOperand.SP);
    return 0;
  }

  /* Helper method for translating assignments into variables previously defined
   * e.g. i = 5 where i has already been pre-defined */
  private int translateLHSNonDeclaration(Context context) {
    /* LHS MUST be a VariableNode, other cases are already
     * caught before the call to this function. Return the location of
     * this variable on the stack. */
    assert (lhs instanceof VariableNode);

    SymbolTable currentSymbolTable = context.getCurrentSymbolTable();
    VariableNode lhsVar = (VariableNode) lhs;
    String name = lhsVar.getName();
    SymbolKey key = new SymbolKey(name, KeyTypes.VARIABLE);
    VariableIdentifier id = (VariableIdentifier) symbolTable.lookupAll(key);

    /* Special case of class2 = class1 where class2 is a superclass instance of
     * class1. Then, we need to change class2's type to class1's type,
     * including its type in the symbol table. */
    if (rhs.getType() instanceof ClassType) {
      assert (rhs instanceof VariableNode);
      Type newLHSType = rhs.getType();
      lhs.setType(newLHSType);
      id.setType(newLHSType);
    }

    if (id.isClassVariable()) {
      RegisterOperand classInstReg = new RegisterOperand(context.getFreeRegister());

      SymbolKey classInstanceKey = new SymbolKey("specialname", KeyTypes.VARIABLE);
      VariableIdentifier classInstanceIdentifier = (VariableIdentifier) currentSymbolTable.lookupAll(classInstanceKey);
      SingleDataTransfer loadClassInstance = SingleDataTransfer.LDR(classInstReg,
          new PreIndexedAddressOperand(RegisterOperand.SP)
              .withExpr(new ImmediateOperand<>(classInstanceIdentifier
                  .getOffsetStack(currentSymbolTable, classInstanceKey))
                  .withPrefixSymbol("#")));
      context.addToCurrentLabel(loadClassInstance);
      lhsVar.setRegister(classInstReg);
    } else {
      lhsVar.setRegister(RegisterOperand.SP);
    }

    return id.getOffsetStack(currentSymbolTable, key);
  }
}
