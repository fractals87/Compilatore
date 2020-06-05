package ast;

import visitor.IVisitor;

public class NodePrint extends NodeStm {
	public NodeId id;
	
    public NodePrint(NodeId nodeID){
        this.id = nodeID;
    }
    
    public NodeId getId(){
        return this.id;
    }
    
    @Override
    public String toString(){
        return "<NodePrint, " + "id: " + this.id.toString() + ">";
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof  NodePrint){
            NodePrint node = (NodePrint)obj;
            return this.id.equals(node.id);
        }
        return false;
    }

	@Override
	public void accept(IVisitor visitor) {
        visitor.visit(this);		
	}
}
