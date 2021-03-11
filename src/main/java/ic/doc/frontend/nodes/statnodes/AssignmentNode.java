package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data;
import ic.doc.backend.instructions.*;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.backend.PredefinedFunctions;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.exprnodes.ArrayElementNode;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.nodes.exprnodes.PairElementNode;
import ic.doc.frontend.nodes.exprnodes.VariableNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.*;

import org.antlr.v4.runtime.ParserRuleContext;

import static ic.doc.backend.instructions.Branch.BL;
import static ic.doc.backend.instructions.Move.MOV;
import static ic.doc.backend.instructions.SingleDataTransfer.LDR;

public class AssignmentNode extends StatNode {

  private final ExprNode lhs;
  private final ExprNode rhs;
  private final boolean isDeclaration;
  private final SymbolTable symbolTable;

  public AssignmentNode(
      ExprNode lhs, ExprNode rhs, boolean isDeclaration, SymbolTable symbolTable) {
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
        visitor.getSemanticErrorList().addScopeException(ctx, true, "Variable", name);
      } else {
        symbolTable.add(key, new VariableIdentifier(lhs.getType()));
      }
    }

    /* Checks if types of lhs and rhs are the same.
     * If their types are errors, an error should already have been thrown
     * for the variable not being defined in the scope
     * and thus further error throwing is unnecessary */
    if (!(Type.checkTypeCompatibility(lhs.getType(), rhs.getType()))
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
    SingleDataTransfer storeInstr;
    RegisterOperand base = RegisterOperand.SP;

    if (lhs instanceof ArrayElementNode) {
      /* Special case when lhs is an arrayElementNode */
      base = translateArrayElementNode(context);
      offset = 0;
    } else {
      /* Need to distinguish between declarative statement and non declarative statement */
      if (isDeclaration) {
        offset = translateLHSDeclaration(context);
      } else {
        offset = translateLHSNonDeclaration(context);
      }
    }
    rhs.translate(context);

    if (rhs instanceof PairElementNode) {
      /* Load address of pair element to rhs */
      context.addToCurrentLabel(
          LDR(rhs.getRegister(), new PreIndexedAddressOperand(rhs.getRegister())));
    }

    if (lhs instanceof PairElementNode) {
      /* Loads pair element address to a free register */
      translatePairElementNode(base, offset, context);
      return;
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

    /* Frees unused registers after assignment */
    context.freeRegister(rhs.getRegister().getValue());
    /* Only frees base register for arrayElementNode as it is SP for other cases */
    if (lhs instanceof ArrayElementNode) {
      context.freeRegister(base.getValue());
    }
  }

  /* Helper method for translating pair element node */
  private void translatePairElementNode(RegisterOperand base, int offset, Context context) {
    SingleDataTransfer storeInstr;
    PairElementNode pairElementNode = (PairElementNode) lhs;
    boolean isFst = pairElementNode.getPos().equals(PairElementNode.PairPosition.FST);
    RegisterOperand tempReg = new RegisterOperand(context.getFreeRegister());

    /* Load memory address of the pair into a temporary register */
    context.addToCurrentLabel(
        LDR(tempReg,
            new PreIndexedAddressOperand(base)
                .withExpr(new ImmediateOperand<>(offset).withPrefixSymbol("#"))));

    /* Check whether the address of the pair points to a null value */
    context.addToCurrentLabel(MOV(RegisterOperand.R0, tempReg));
    PredefinedFunctions.addCheckNullPointerFunction(context);
    context.addToCurrentLabel(BL(PredefinedFunctions.CHECK_NULL_POINTER_FUNC));

    /* Load memory address of the pair element into a temporary register */
    context.addToCurrentLabel(
        LDR(tempReg,
            new PreIndexedAddressOperand(tempReg)
                .withExpr(new ImmediateOperand<>(isFst ? 0 : 4).withPrefixSymbol("#"))));

    /* Stores 1 byte if char or bool with STRB, and 4 bytes otherwise with STR */
    String strCond = lhs.getType().getVarSize() == 1 ? "B" : "";
    context.addToCurrentLabel(
        SingleDataTransfer.STR(
            rhs.getRegister(),
            new PreIndexedAddressOperand(tempReg))
            .withCond(strCond));

    context.freeRegister(tempReg.getValue());
    context.freeRegister(rhs.getRegister().getValue());
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

    return 0;
  }

  /* Helper method for translating array element node */
  private RegisterOperand translateArrayElementNode(Context context) {
    ArrayElementNode arrayElementNode = (ArrayElementNode) lhs;
    return ArrayElementNode.translateArray(context, arrayElementNode);
  }

  /* Helper method for translating assignments into variables previously defined
   * e.g. i = 5 where i has already been pre-defined */
  private int translateLHSNonDeclaration(Context context) {

    /* FST p = 5 where p is a predefined pair where the first element is of
     * type int. Return the location of this variable on the stack plus
     * an offset, depending if it's the FST or SND pair. */
    if (lhs instanceof PairElementNode) {
      VariableNode lhsVar = (VariableNode) ((PairElementNode) lhs).getExpr();
      String name = lhsVar.getName();
      SymbolKey key = new SymbolKey(name, KeyTypes.VARIABLE);
      VariableIdentifier id = (VariableIdentifier) symbolTable.lookupAll(key);
      return id.getOffsetStack(symbolTable, key);
    }

    /* All other types, LHS MUST be a VariableNode. Return the location of
     * this variable on the stack. */
    VariableNode lhsVar = (VariableNode) lhs;
    String name = lhsVar.getName();
    SymbolKey key = new SymbolKey(name, KeyTypes.VARIABLE);
    VariableIdentifier id = (VariableIdentifier) symbolTable.lookupAll(key);
    return id.getOffsetStack(context.getCurrentSymbolTable(), key);
  }
}
