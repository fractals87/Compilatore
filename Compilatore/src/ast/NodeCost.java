package ast;

import visitor.IVisitor;

public class NodeCost extends NodeExpr {
    private String value;
    
    private LangType type;
    
    public String getValue() {
		return value;
	}
    
    public LangType getType() {
		return type;
	}

	public void setType(LangType type) {
		this.type = type;
	}

	public NodeCost(String value, LangType type){
        this.value = value;
        this.type = type;
    }
    
    @Override
    public String toString(){
        return "<value: " + value + ", type: " + type + ">";
    }

	@Override
	public void accept(IVisitor visitor) {
        visitor.visit(this);
		
	}
    
}
