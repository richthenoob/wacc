package ic.doc.frontend.nodes.exprnodes;

import ic.doc.backend.Context;
import ic.doc.backend.instructions.*;
import ic.doc.backend.instructions.operands.ImmediateOperand;
import ic.doc.backend.instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.backend.PredefinedFunctions;
import ic.doc.frontend.identifiers.Identifier;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.exprnodes.Literals.IntLiteralNode;
import ic.doc.frontend.semantics.SymbolKey;
import ic.doc.frontend.semantics.SymbolKey.KeyTypes;
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
    SymbolKey key = new SymbolKey(identNode.getName(), KeyTypes.VARIABLE);
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
          .addTypeException(ctx, identNode.getInput(), "T[]",
              identNode.getType().toString(), "");
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

  /* Takes care of loading the correct elements of the array in the proper locations relative to the array pointer */
  public static RegisterOperand translateArray(Context context,
      ArrayElementNode array) {
    Label<Instruction> label = context.getCurrentLabel();
    SymbolTable symbolTable = context.getCurrentSymbolTable();

    /* Find stack offset for array pointer */
    VariableNode lhsVar = array.getIdentNode();
    String name = lhsVar.getName();
    SymbolKey key = new SymbolKey(name, KeyTypes.VARIABLE);
    VariableIdentifier id = (VariableIdentifier) symbolTable.lookupAll(key);
    int offsetArray = id.getOffsetStack(symbolTable, key);

    RegisterOperand arrayReg = new RegisterOperand(context.getFreeRegister());
    RegisterOperand indexReg = new RegisterOperand(context.getFreeRegister());
    List<ExprNode> arrays = array.getExprNodes();

    for (int i = 0; i < arrays.size(); i++) {
      if (i == 0) {
        /* Load location of array into arrayReg.
         * Only needed for first layer as for nested arrays the
         * nested array will already be in arrayReg */
        label.addToBody(
            DataProcessing.ADD(
                arrayReg,
                RegisterOperand.SP,
                new ImmediateOperand<>(offsetArray).withPrefixSymbol("#")));
      }

      if (arrays.get(i) instanceof IntLiteralNode) {
        /* Load index literal */
        label.addToBody(
            SingleDataTransfer.LDR(
                indexReg,
                new ImmediateOperand<>(
                    ((IntLiteralNode) arrays.get(i)).getValue().intValue())
                    .withPrefixSymbol("=")));
      } else {
        /* Find offset of index pointer if its a variable */
        String indexVarName = arrays.get(i).getInput();
        SymbolKey indexVarkey = new SymbolKey(indexVarName, KeyTypes.VARIABLE);
        VariableIdentifier indexId = (VariableIdentifier) symbolTable
            .lookupAll(indexVarkey);
        int offset = indexId.getOffsetStack(symbolTable, indexVarkey);

        /* Load index from memory */
        label.addToBody(
            SingleDataTransfer.LDR(
                indexReg,
                new PreIndexedAddressOperand(RegisterOperand.SP)
                    .withExpr(
                        new ImmediateOperand<>(offset).withPrefixSymbol("#"))));
      }

      /* Load array */
      label.addToBody(SingleDataTransfer
          .LDR(arrayReg, new PreIndexedAddressOperand(arrayReg)));

      /* Move values into correct registers to call array bounds checking predefined function */
      label.addToBody(Move.MOV(new RegisterOperand(0), indexReg));
      label.addToBody(Move.MOV(new RegisterOperand(1), arrayReg));
      PredefinedFunctions.addCheckArrayBoundsFunction(context);
      label.addToBody(Branch.BL(PredefinedFunctions.CHECK_ARRAY_BOUNDS_FUNC));

      /* Address of arrayelem  = address of first element + index * 4 */
      label.addToBody(
          DataProcessing.ADD(arrayReg, arrayReg,
              new ImmediateOperand<>(Context.SIZE_OF_ADDRESS).withPrefixSymbol("#")));
      Type internalType = ((ArrayType) (array.identNode.getType()))
          .getInternalType();
      if (internalType.getVarSize() == 1) {
        label.addToBody(DataProcessing.ADD(arrayReg, arrayReg, indexReg));
      } else {
        label.addToBody(
            DataProcessing.SHIFTADD(
                arrayReg,
                arrayReg,
                indexReg,
                PreIndexedAddressOperand.ShiftTypes.LSL,
                new ImmediateOperand<>(2).withPrefixSymbol("#")));
      }
    }
    context.freeRegister(indexReg.getValue());
    return arrayReg;
  }

  @Override
  public void translate(Context context) {
    RegisterOperand arrayRegister = translateArray(context, this);
    setRegister(arrayRegister);

    /* Load value at memory location */
    Type internalType = ((ArrayType) (identNode.getType())).getInternalType();

    /* If type of variable is Char or Bool, it only needs a stack size of 1.
     * Then we need to use LDRSB to load it. Otherwise, we use LDR. */
    String cond = internalType.getVarSize() == 1 ? "SB" : "";

    context
        .getCurrentLabel()
        .addToBody(
            SingleDataTransfer
                .LDR(arrayRegister, new PreIndexedAddressOperand(arrayRegister))
                .withCond(cond));
  }

  @Override
  public String getInput() {
    return identNode.getInput();
  }
}
