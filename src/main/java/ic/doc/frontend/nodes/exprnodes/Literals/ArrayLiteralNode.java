package ic.doc.frontend.nodes.exprnodes.Literals;

import ic.doc.backend.Context;
import ic.doc.backend.Instructions.*;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.semantics.Visitor;
import ic.doc.frontend.types.*;

import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;

/* Can only appear as assignment values. All elements must be of the same type.
 * Valid examples: [1,2,3,4]
 *                 ["hello", "world"]
 *                 [] */

public class ArrayLiteralNode extends LiteralNode {

  /* Refers to type of the node, not internal type of elements. */
  private final List<ExprNode> values;

  public ArrayLiteralNode(List<ExprNode> values) {
    this.values = values;
  }

  public List<ExprNode> getValues() {
    return values;
  }

  @Override
  public void check(Visitor visitor, ParserRuleContext ctx) {

    /* No elements in the list, so it is a generic array type. */
    if (values.isEmpty()) {
      setType(new ArrayType(new AnyType()));
      return;
    }

    /* Check that every list element type is the same as the first. */
    List<ExprNode> mismatchedTypeNodes =
        values.stream()
            .filter(x -> !(Type.checkTypeCompatibility(x.getType(), values.get(0).getType())))
            .collect(Collectors.toList());

    for (ExprNode mismatchedTypeNode : mismatchedTypeNodes) {
      visitor
          .getSemanticErrorList()
          .addTypeException(
              ctx,
              mismatchedTypeNode.getInput(),
              values.get(0).getType().toString(),
              mismatchedTypeNode.getType().toString(),
              "");
    }

    if (!mismatchedTypeNodes.isEmpty()) {
      setType(new ErrorType());
    } else {
      /* Since we have verified all the elements are of the same type, we can
       * now set the type of this array. */
      setType(new ArrayType(values.get(0).getType()));
    }
  }

  @Override
  public void translate(Context context) {
    Label<Instruction> label = context.getCurrentLabel();
    int bytesToAllocate = values.size() * 4 + 4;
    label
        .addToBody(
            SingleDataTransfer.LDR(new RegisterOperand(0), new ImmediateOperand(bytesToAllocate)))
        .addToBody(Branch.BL("malloc"))
        .addToBody(new Move(new RegisterOperand(4), new RegisterOperand(0), Condition.B));
    int offset = 4;
    for (ExprNode value : values) {
      value.translate(context);
      label.addToBody(
          SingleDataTransfer.STR(
              value.getRegister(),
              PreIndexedAddressOperand.PreIndexedAddressFixedOffset(
                  new RegisterOperand(4), new ImmediateOperand(offset))));
      offset += 4;
    }
  }

  @Override
  public String getInput() {
    return values.isEmpty() ? "[]" : values.get(0).getInput();
  }
}
