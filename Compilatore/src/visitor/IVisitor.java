package visitor;

import ast.*;
import exception.RegisterException;

public interface IVisitor {
	public abstract void visit(NodeProgram node) throws RegisterException;
	public abstract void visit(NodeAssign node) throws RegisterException;
	public abstract void visit(NodeBinOp node) throws RegisterException;
	public abstract void visit(NodeCost node);
	public abstract void visit(NodeDecl node) throws RegisterException;
	public abstract void visit(NodeDeref node);
	public abstract void visit(NodeId node);
	public abstract void visit(NodePrint node);
	public abstract void visit(NodeConvert node) throws RegisterException;
}
