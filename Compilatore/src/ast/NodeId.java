package ast;

import java.util.Objects;

import symbolTable.Attributes;
import visitor.IVisitor;

public class NodeId extends NodeAST {
	private String name;
    private Attributes definition;
	
    public NodeId(String name){
    	this.name = name;
    }
	
    public NodeId(String name,Attributes definition){
    	this.name = name;
    	this.setDefinition(definition);
    }
    
	public String getName() {
		return name;
	}

	@Override
    public String toString(){
        return name;
    }
	
	@Override
    public boolean equals(Object obj){
        if(obj instanceof NodeId){
            NodeId node = (NodeId) obj;
            return Objects.equals(this.name, node.name);
        }
        return false;
    }

	@Override
	public void accept(IVisitor visitor) {
        visitor.visit(this);
	}

	public Attributes getDefinition() {
		return definition;
	}

	public void setDefinition(Attributes definition) {
		this.definition = definition;
	}
}


