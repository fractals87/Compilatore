package ast;

import exception.RegisterException;
import visitor.IVisitor;

public class NodeConvert extends NodeExpr {

    private NodeExpr node;

    public NodeExpr getNode() {
		return node;
	}

	public void setNode(NodeExpr node) {
		this.node = node;
	}

	public NodeConvert(NodeExpr node) {
        this.node = node;
    }
    
    @Override
    public String toString() {
        return "<Convert," + node.toString() + ">";
    }

	@Override
	public void accept(IVisitor visitor) throws RegisterException {
        visitor.visit(this);	
	}
}