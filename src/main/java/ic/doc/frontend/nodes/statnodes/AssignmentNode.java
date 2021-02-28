package ic.doc.frontend.nodes.statnodes;

import ic.doc.backend.Context;
import ic.doc.backend.Data.Data;
import ic.doc.backend.Instructions.DataProcessing;
import ic.doc.backend.Instructions.SingleDataTransfer;
import ic.doc.backend.Instructions.operands.ImmediateOperand;
import ic.doc.backend.Instructions.operands.PreIndexedAddressOperand;
import ic.doc.backend.Instructions.operands.RegisterOperand;
import ic.doc.backend.Label;
import ic.doc.frontend.identifiers.VariableIdentifier;
import ic.doc.frontend.nodes.exprnodes.ExprNode;
import ic.doc.frontend.nodes.exprnodes.Literals.CharacterLiteralNode;
import ic.doc.frontend.nodes.exprnodes.Literals.LiteralNode;
import ic.doc.frontend.nodes.exprnodes.Literals.StringLiteralNode;
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
    VariableNode lhsVar = (VariableNode) lhs;
    String name = lhsVar.getName();
    SymbolKey key = new SymbolKey(name, false);
    VariableIdentifier id = (VariableIdentifier) symbolTable.lookupAll(key);
    ImmediateOperand<Integer> offset;
    if (isDeclaration) { // if declaring, need to move stack pointer
      if (lhs.getType() instanceof CharType || lhs.getType() instanceof StringType) {
        String str;
        int length;
        if (lhs.getType() instanceof CharType) {
          CharacterLiteralNode literalNode = (CharacterLiteralNode) lhs;
          length = 1;
          str = literalNode.getValue().toString();
        } else {
          StringLiteralNode literalNode = (StringLiteralNode) lhs;
          length = literalNode.getValue().length();
          str = literalNode.getValue();
        }
        List<Label<Data>> dataLabels = context.getDataLabels();
        int newIndex = dataLabels.size();
        Label<Data> newLabel = new Label<>("msg_" + newIndex);
        dataLabels.add(newIndex, newLabel);
        newLabel.addToBody(new Data(length, str));
      } // other types
      else{
        symbolTable.incrementOffset();
        symbolTable.incrementTableSizeInBytes();
        id.setActivated();
      }
      offset = null;
      context.addToCurrentLabel(
          DataProcessing.SUB(
              RegisterOperand.SP(), RegisterOperand.SP(), new ImmediateOperand<>(true, 4)));

    } else { // if not declaration, find offset of previous declaration
      offset = new ImmediateOperand<>(id.getOffsetStack());
    }
    rhs.translate(context);
    context.addToCurrentLabel(
        SingleDataTransfer.STR(
            rhs.getRegister(),
            PreIndexedAddressOperand.PreIndexedAddressFixedOffset(RegisterOperand.SP(), offset)));
    context.freeRegister(rhs.getRegister().getValue());
  }
}
