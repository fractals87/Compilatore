package ast;

import exception.RegisterException;
import visitor.IVisitor;

public class NodeAssign extends NodeStm {
    private NodeId id;
    private NodeExpr expr;

    public NodeAssign(NodeId id, NodeExpr expr){
        this.id = id;
        this.expr = expr;
    }
    
    public NodeId getId() {
		return id;
	}

	public void setId(NodeId id) {
		this.id = id;
	}

	public NodeExpr getExpr() {
		return expr;
	}

	public void setExpr(NodeExpr expr) {
		this.expr = expr;
	}

	@Override
    public String toString(){
        return "<NodeId: " + id.toString() + ", NodeExpr: " + expr.toString() + ">";
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof  NodeAssign) {
            NodeAssign node = (NodeAssign) obj;
            return this.expr.equals(node.expr) && this.id.equals(node.id);
        }
        return false;
    }

	@Override
	public void accept(IVisitor visitor) throws RegisterException {
        visitor.visit(this);	
	}
    
    
}
