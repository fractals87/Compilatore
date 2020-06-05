package ast;

import exception.RegisterException;
import visitor.IVisitor;

public abstract class NodeAST {
    private TypeDescriptor resType;
    public abstract void accept(IVisitor visitor) throws RegisterException;
    
	public TypeDescriptor getResType() {
		return resType;
	}

	public void setResType(TypeDescriptor resType) {
		this.resType = resType;
	}
}
