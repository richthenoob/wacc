package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.*;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.backend.PredefinedFunctions;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.exprnodes.Literals.IntLiteralNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolTable;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.ArrayType;
import ic.doc.frontend.types.ErrorType;
import ic.doc.frontend.types.IntType;
import ic.doc.frontend.types.Type;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;

/* Parser definition: arrayElem: IDENT (OPEN_BRACKETS expr CLOSE_BRACKETS)+
 * IDENT MUST be of type T[], expr MUST be of type INT
 * Valid examples: arr[1] (arr is of type INT[]),
 *                 arr[1][2] (assuming arr is of type INT[][]) */
public class ArrayElementNode extends ExprNode {

  private final List<ExprNode> exprNodes;
  private final VariableNode identNode;
  private boolean isErrored = false;

  public List<ExprNode> getExprNodes() {
    return exprNodes;
  }

  public ArrayElementNode(List<ExprNode> exprNodes, VariableNode identNode) {
    this.exprNodes = exprNodes;
    this.identNode = identNode;
  }

  public int getDimensions() {
    return exprNodes.size();
  }

  public ExprNode getFirstIndex() {

    return exprNodes.get(0);
  }

  public VariableNode getIdentNode() {
    return identNode;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

    /* Should never have empty exprNode list,
     * if not it should be a syntactic error. */
    if (exprNodes.isEmpty()) {
      throw new IllegalStateException("");
    }

    /* Identifier checks. */
    SymbolKey key = new SymbolKey(identNode.getName(), false);
    Identifier entry = visitor.getCurrentSymbolTable().lookupAll(key);
    if (entry == null) {
      isErrored = true;
      /* Identifier should have already been defined. */
      visitor
          .getSemanticErrorList()
          .addScopeException(ctx, false, "Variable", identNode.getInput());
    } else if (!(entry.getType() instanceof ArrayType)) {
      isErrored = true;
      /* Identifier should be of type "Array". */
      visitor
          .getSemanticErrorList()
          .addTypeException(ctx, identNode.getInput(), "T[]", identNode.getType().toString(), "");
    }

    /* Go through all exprNodes to check index is of correct type, INT. */
    List<ExprNode> mismatchedTypeNodes =
        exprNodes.stream()
            .filter(x -> !(x.getType() instanceof IntType))
            .collect(Collectors.toList());

    for (ExprNode mismatchedTypeNode : mismatchedTypeNodes) {
      isErrored = true;
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              mismatchedTypeNode.getInput(),
              "INT",
              mismatchedTypeNode.getType().toString(),
              "");
    }

    if (isErrored) {
      setType(new ErrorType());
    } else {
      Type type = identNode.getType();

      /* Iterate through number of expressions to find underlying
       * expression type. */
      for (int i = 0; i < exprNodes.size(); i++) {
        if (type instanceof ArrayType) {
          type = ((ArrayType) type).getInternalType();
        } else {
          visitor
              .getSemanticErrorList()
              .addTypeException(ctx, getInput(), "T[]", type.toString(), "");
        }
      }
      setType(type);
    }
  }

  public static RegisterOperand translateArray(Context context, ArrayElementNode array) {
    Label<Instruction> label = context.getCurrentLabel();
    SymbolTable symbolTable = context.getCurrentSymbolTable();

    // Find offset for array pointer
    VariableNode lhsVar = array.getIdentNode();
    String name = lhsVar.getName();
    SymbolKey key = new SymbolKey(name, false);
    VariableIdentifier id = (VariableIdentifier) symbolTable.lookupAll(key);
    int offsetArray = id.getOffsetStack(symbolTable, key);

    RegisterOperand arrayReg = new RegisterOperand(context.getFreeRegister());
    RegisterOperand indexReg = new RegisterOperand(context.getFreeRegister());
    List<ExprNode> arrays = array.getExprNodes();

    for (int i = 0; i < arrays.size(); i++) {
      // Load location of array into ArrayReg, only needed for first layer as for nested arrays the
      // nested array will
      // already be in arrayReg
      if (i == 0) {
        label.addToBody(
            DataProcessing.ADD(
                arrayReg, RegisterOperand.SP(), new ImmediateOperand<>(true, offsetArray)));
      }
      if (arrays.get(i) instanceof IntLiteralNode) {
        label.addToBody(
            SingleDataTransfer.LDR(
                indexReg,
                new ImmediateOperand<>(
                    ((IntLiteralNode) arrays.get(i)).getValue().intValue()))); // Load index literal
      } else {
        // find offset of index pointer if its a variable
        String indexVarName = arrays.get(i).getInput();
        SymbolKey indexVarkey = new SymbolKey(indexVarName, false);
        VariableIdentifier indexId = (VariableIdentifier) symbolTable.lookupAll(indexVarkey);
        int offset = indexId.getOffsetStack(symbolTable, indexVarkey);

        label.addToBody(
            SingleDataTransfer.LDR(
                indexReg,
                PreIndexedAddressOperand.PreIndexedAddressFixedOffset(
                    RegisterOperand.SP(),
                    new ImmediateOperand<>(true, offset)))); // Load index from memory
      }
      // load array
      label.addToBody(
          SingleDataTransfer.LDR(
              arrayReg, PreIndexedAddressOperand.PreIndexedAddressZeroOffset(arrayReg)));

      // Move values into correct registers to call predefined function
      label.addToBody(Move.MOV(new RegisterOperand(0), indexReg));
      label.addToBody(Move.MOV(new RegisterOperand(1), arrayReg));
      PredefinedFunctions.addCheckArrayBoundsFunction(context);
      label.addToBody(Branch.BL(PredefinedFunctions.CHECK_ARRAY_BOUNDS_FUNC));

      // address of value = address of first value + value of index * 4
      label.addToBody(DataProcessing.ADD(arrayReg, arrayReg, new ImmediateOperand<>(true, 4)));
      label.addToBody(
          DataProcessing.SHIFTADD(
              arrayReg,
              arrayReg,
              indexReg,
              PreIndexedAddressOperand.ShiftTypes.LSL,
              new ImmediateOperand<>(true, 2)));
    }
    context.freeRegister(indexReg.getValue());
    return arrayReg;
  }

  @Override
  public void translate(Context context) {
    RegisterOperand arrayRegister = translateArray(context, this);
    setRegister(arrayRegister);
    context
        .getCurrentLabel()
        .addToBody(
            SingleDataTransfer.LDR(
                arrayRegister,
                PreIndexedAddressOperand.PreIndexedAddressZeroOffset(
                    arrayRegister))); // Load index from memory;
  }

  @Override
  public String getInput() {
    return identNode.getInput();
  }
}
