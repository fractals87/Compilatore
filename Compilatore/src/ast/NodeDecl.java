package ast;

import exception.*;
import visitor.IVisitor;

public class NodeDecl extends NodeDecSt {
	private NodeId id;
	private LangType type;
	
    public NodeDecl(NodeId id, LangType type){
        this.id = id;
        this.type = type;
    }
	
	public LangType getType() {
		return type;
	}
	public NodeId getId() {
		return id;
	}
	
	@Override
    public String toString(){
        return "<NodeDecl, " + "id: " + this.id.toString() + ", type: " + this.type + ">";
    }
	
    @Override
    public boolean equals(Object obj){
        if(obj instanceof NodeDecl){
            NodeDecl node = (NodeDecl) obj;
            return this.type == node.type && this.id.equals(node.id);
        }
        return false;
    }

	@Override
	public void accept(IVisitor visitor) throws RegisterException {
        visitor.visit(this);	
	}
	
	
    public void setReg() throws RegisterException {
    	id.getDefinition().setReg();
    }
}
