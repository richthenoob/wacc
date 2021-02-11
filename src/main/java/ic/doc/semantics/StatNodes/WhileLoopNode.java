package ic.doc.semantics.StatNodes;

import ic.doc.semantics.ExprNodes.ExprNode;
import ic.doc.semantics.Types.BoolType;
import ic.doc.semantics.Types.ErrorType;
import ic.doc.semantics.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class WhileLoopNode extends StatNode {
	private ExprNode cond;
	private StatNode body;

	public WhileLoopNode(ExprNode cond, StatNode body){
		this.cond = cond;
		this.body = body;
	}

	public ExprNode getCond(){
		return cond;
	}

	public StatNode getBody(){
		return body;
	}

	@Override
	public void check(Visitor visitor, ParserRuleContext ctx) {
		// Expr must be a bool.
		// There is no need to print the type error message
		// if the condition was not present in the symbol table - i.e. if the type was Error.
		if (!(cond.getType() instanceof BoolType
				|| cond.getType() instanceof ErrorType)) {
			visitor.addTypeException(ctx, cond.getInput(), "BOOL", cond.getType().toString());
		}
	}

}
