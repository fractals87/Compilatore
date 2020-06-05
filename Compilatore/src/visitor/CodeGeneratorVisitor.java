package visitor;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConvert;
import ast.NodeCost;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import exception.RegisterException;

/*
 * COMMAND LIST
 * 	p	Prints the value on the top of the stack and ends the statement with a newline.
	n	Prints the value on the top of the stack and ends the line with a null statement.
	f	Prints the entire stack, without any alteration.
	P	Pops the value from the top of the stack.
	c	Clear the stack.
	d	Duplicates the top value and push it into the main stack.
	r	Reverses the order of top two elements in the stack.
	Z	Pops the value from the stack, calculate the number of digits in it and pushes that number.
	X	Pops the value from the stack, calculate the number of fraction digits in it and pushes that number.
	z	Pushes the stack length into the stack.
	i	Pops the value from the stack and uses it as input radix.
	o	Pops the value from the stack and uses it as output radix.
	k	Pops the values from the stack and uses it to set precision.
	I	Pushes the value of input radix into the stack.
	O	Pushes the value of output radix into tje stack
	K	Pushes the precission value into the stack.
 * 
 */
public class CodeGeneratorVisitor implements IVisitor {
	public StringBuffer codice = new StringBuffer();

	@Override
	public void visit(NodeProgram node) throws RegisterException {
        for (NodeDecSt st : node.getDecSts()) {
            st.accept(this);
        }
	}

	@Override
	public void visit(NodeAssign node) throws RegisterException {
        node.getExpr().accept(this);
        codice.append("s").append(node.getId().getDefinition().getReg()).append(" 0 k ");
	}

	@Override
	public void visit(NodeBinOp node) throws RegisterException {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        
        switch (node.getOp()){
        case PLUS:
            codice.append("+ ");
            break;
        case MINUS:
        	codice.append("- ");
            break;
        case TIMES:
        	codice.append("* ");
            break;
        case DIV:
        	codice.append("/ ");
    }
		
	}

	@Override
	public void visit(NodeCost node) {
        codice.append(node.getValue()).append(" ");
	}

	@Override
	public void visit(NodeDecl node) throws RegisterException {
		node.setReg();
	}

	@Override
	public void visit(NodeDeref node) {
        codice.append("l").append(node.getId().getDefinition().getReg()).append(" ");
	}

	@Override
	public void visit(NodeId node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NodePrint node) {
        codice.append("l")
  	  	      .append(node.getId().getDefinition().getReg()).append(" ")
  	  	      .append("p ")
  	  	      .append("P ");
		
	}

	@Override
	public void visit(NodeConvert node) throws RegisterException {
        node.getNode().accept(this);
        codice.append("5 k ");
	}
}
