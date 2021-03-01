package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.*;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.PostIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.backend.PredefinedFunctions;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.exprnodes.ArrayElementNode;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.nodes.exprnodes.Literals.IntLiteralNode;
import ic.doc.frontend.nodes.exprnodes.PairElementNode;
import ic.doc.frontend.nodes.exprnodes.VariableNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.*;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

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

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

    if (isDeclaration) {
      VariableNode lhsVar = (VariableNode) lhs;
      String name = lhsVar.getName();
      SymbolKey key = new SymbolKey(name, false);
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
    RegisterOperand base = RegisterOperand.SP();
    if (lhs instanceof ArrayElementNode) {
      base = translateArrayElementNode(context);
      offset = 0;
    } else {
      if (isDeclaration) {
        offset = translateLHSDeclaration(context);
      } else {
        offset = translateLHSNonDeclaration(context);
      }
    }
    rhs.translate(context);

    if (lhs.getType().getVarSize() == 1) {
      storeInstr =
          SingleDataTransfer.STR(
              "B",
              rhs.getRegister(),
              PreIndexedAddressOperand.PreIndexedAddressFixedOffset(
                  base, new ImmediateOperand<>(true, offset)));
    } else {
      storeInstr =
          SingleDataTransfer.STR(
              rhs.getRegister(),
              PreIndexedAddressOperand.PreIndexedAddressFixedOffset(
                  base, new ImmediateOperand<>(true, offset)));
    }

    context.addToCurrentLabel(storeInstr);
    context.freeRegister(rhs.getRegister().getValue());
    if (lhs instanceof ArrayElementNode) {
      context.freeRegister(base.getValue());
    }
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
    SymbolKey key = new SymbolKey(name, false);
    VariableIdentifier id = (VariableIdentifier) symbolTable.lookupAll(key);

    /* Special case of char type declaration on lhs. In this case,
     * we need to add the char to the dataLabels list. */
    if (lhs.getType() instanceof CharType) {
      int length = id.toString().length();
      String str = id.toString();

      String dataLabelName = context.getNextDataLabelString();
      Label<Data> newLabel = new Label<>(dataLabelName);
      newLabel.addToBody(new Data(length, str));
      context.addToDataLabels(newLabel);
    }

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
            RegisterOperand.SP(),
            RegisterOperand.SP(),
            new ImmediateOperand<>(true, sizeOfVarOnStack)));

    return 0;
  }

  private RegisterOperand translateArrayElementNode(Context context) {
    Label<Instruction> label = context.getCurrentLabel();

    VariableNode lhsVar = ((ArrayElementNode) lhs).getIdentNode();
    String name = lhsVar.getName();
    SymbolKey key = new SymbolKey(name, false);
    VariableIdentifier id = (VariableIdentifier) symbolTable.lookupAll(key);

    int offsetArray = id.getOffsetStack(symbolTable, key);
    RegisterOperand arrayReg = new RegisterOperand(context.getFreeRegister());
    RegisterOperand indexReg = new RegisterOperand(context.getFreeRegister());
    List<ExprNode> arrays = ((ArrayElementNode) lhs).getExprNodes();
    for (int i = 0; i < arrays.size(); i++) {
      if (arrays.get(i) instanceof IntLiteralNode) {
        label.addToBody(
            SingleDataTransfer.LDR(
                indexReg,
                new ImmediateOperand<>(
                    ((IntLiteralNode) arrays.get(i)).getValue().intValue()))); // Load index literal
      } else {

        String indexVarName = lhsVar.getName();
        SymbolKey indexVarkey = new SymbolKey(indexVarName, false);
        VariableIdentifier indexId = (VariableIdentifier) symbolTable.lookupAll(indexVarkey);
        int offset = indexId.getOffsetStack(symbolTable, indexVarkey);
        label.addToBody(
            SingleDataTransfer.STR(
                rhs.getRegister(),
                PreIndexedAddressOperand.PreIndexedAddressFixedOffset(
                    RegisterOperand.SP(),
                    new ImmediateOperand<>(true, offset)))); // Load index from memory
      }
      label.addToBody(
          SingleDataTransfer.LDR(
              arrayReg, // Load array
              PreIndexedAddressOperand.PreIndexedAddressFixedOffset(
                  RegisterOperand.SP(), new ImmediateOperand<>(true, offsetArray))));
      label.addToBody(Move.MOV(new RegisterOperand(0), indexReg));
      label.addToBody(Move.MOV(new RegisterOperand(1), arrayReg));
      PredefinedFunctions.addCheckArrayBoundsFunction(context);
      label.addToBody(Branch.BL(PredefinedFunctions.CHECK_ARRAY_BOUNDS_FUNC));
      label.addToBody(DataProcessing.ADD(arrayReg, arrayReg, new ImmediateOperand<>(true, 4)));
      label.addToBody(
          DataProcessing.SHIFTADD(
              arrayReg, arrayReg, indexReg, PreIndexedAddressOperand.ShiftTypes.LSL, new ImmediateOperand<>(true,2)));
    }
    context.freeRegister(indexReg.getValue());
    return arrayReg;
  }

  /* e.g. i = 5 where i has already been pre-defined */
  private int translateLHSNonDeclaration(Context context) {

    /* FST p = 5 where p is a predefined pair where the first element is of
     * type int. Return the location of this variable on the stack plus
     * an offset, depending if it's the FST or SND pair. */
    if (lhs instanceof PairElementNode) {
      VariableNode lhsVar = (VariableNode) ((PairElementNode) lhs).getExpr();
      String name = lhsVar.getName();
      SymbolKey key = new SymbolKey(name, false);
      VariableIdentifier id = (VariableIdentifier) symbolTable.lookupAll(key);
      int positionOffset =
          ((PairElementNode) lhs).getPos().equals(PairElementNode.PairPosition.FST) ? 0 : 4;
      return id.getOffsetStack(symbolTable, key) + positionOffset;
    }

    /* All other types, LHS MUST be a VariableNode. Return the location of
     * this variable on the stack. */
    VariableNode lhsVar = (VariableNode) lhs;
    String name = lhsVar.getName();
    SymbolKey key = new SymbolKey(name, false);
    VariableIdentifier id = (VariableIdentifier) symbolTable.lookupAll(key);
    return id.getOffsetStack(context.getCurrentSymbolTable(), key);
  }
}
