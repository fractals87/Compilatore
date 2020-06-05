package ast;

import exception.RegisterException;
import visitor.IVisitor;

public class NodeBinOp extends NodeExpr{
    private LangOper op;
    private NodeExpr left;
    private NodeExpr right;
    
    public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right){
        this.op = op;
        this.left = left;
        this.right = right;
    }
   
    
    public LangOper getOp() {
		return op;
	}


	public void setOp(LangOper op) {
		this.op = op;
	}


	public NodeExpr getLeft() {
		return left;
	}


	public void setLeft(NodeExpr left) {
		this.left = left;
	}


	public NodeExpr getRight() {
		return right;
	}


	public void setRight(NodeExpr right) {
		this.right = right;
	}


	@Override
    public String toString(){
        return "<op: " + op + ", left: " + left.toString() + ", right: " + right.toString() + ">";
    }

	@Override
	public void accept(IVisitor visitor) throws RegisterException {
        visitor.visit(this);
	}
}
