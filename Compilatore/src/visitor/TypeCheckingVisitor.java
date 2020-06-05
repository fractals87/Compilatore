package visitor;

import ast.*;
import exception.RegisterException;
import symbolTable.*;

public class TypeCheckingVisitor implements IVisitor{
	private StringBuilder log = new StringBuilder();
	
	@Override
	public void visit(NodeProgram node) throws RegisterException {
        for (NodeDecSt st : node.getDecSts()) {
            st.accept(this);
        }		
	}
	
	@Override
	public void visit(NodeAssign node) throws RegisterException {
		node.getId().accept(this);
		node.getExpr().accept(this);
		if(!compatible(node.getId().getResType(), node.getExpr().getResType())) {
			log.append(String.format("Node Assign - incompatible type between %s and %s", node.getId().getName(), node.getExpr().toString()));
			node.setResType(TypeDescriptor.ERROR);
		} else {
			NodeExpr newNode = node.getExpr();
            if (node.getId().getResType() == TypeDescriptor.FLOAT && node.getExpr().getResType() == TypeDescriptor.INT) {
                newNode = convertExpr(node.getExpr());
            }
            node.setResType(newNode.getResType());
            node.setExpr(newNode);
		}
		
	}

	@Override
	public void visit(NodeBinOp node) throws RegisterException {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        if(node.getRight().getResType() == TypeDescriptor.ERROR || node.getLeft().getResType() == TypeDescriptor.ERROR) {
            node.setResType(TypeDescriptor.ERROR);
        }else if (node.getLeft().getResType() == node.getRight().getResType()) {
            node.setResType(node.getRight().getResType());
        } else {
            node.setLeft(convertExpr(node.getLeft()));
            node.setRight(convertExpr(node.getRight()));
            node.setResType(TypeDescriptor.FLOAT);
        }
	}
	
	@Override
	public void visit(NodeConvert node) throws RegisterException {
		node.getNode().accept(this);
	    if(node.getNode().getResType() != TypeDescriptor.INT){
	    	node.setResType(TypeDescriptor.ERROR);
	    }else{
	        node.setResType(TypeDescriptor.FLOAT);
	    }
	}
	
	@Override
	public void visit(NodeCost node) {
        if (node.getType() == LangType.INT) {
            node.setResType(TypeDescriptor.INT);
        } else {
            node.setResType(TypeDescriptor.FLOAT);
        }
	}

	@Override
	public void visit(NodeDecl node) {
		if(SymbolTable.lookup(node.getId().getName())!=null) {
		//if(table.lookup(node.getId().getName())!=null) {
			log.append(String.format("NodeDecl - %s - already declare\n", node.getId().getName()));
            node.setResType(TypeDescriptor.ERROR);
		}else {
			Attributes att = new Attributes();
            if(node.getType() == LangType.INT) {
                att.setType(TypeDescriptor.INT);
            }else {
                att.setType(TypeDescriptor.FLOAT);
            }
            //table.enter(node.getId().getName(), att);
            SymbolTable.enter(node.getId().getName(), att);
            node.setResType(att.getType());
            node.getId().setDefinition(att);            
		}
	}

	@Override
	public void visit(NodeDeref node) {
        node.getId().accept(this);
        node.setResType(node.getId().getResType());
	}

	@Override
	public void visit(NodeId node) {
        //Attributes att = table.lookup(node.getName());
		Attributes att = SymbolTable.lookup(node.getName());
        if (att == null) {
            log.append(String.format("NodeDecl - %s - not defined\n", node.getName()));
            node.setResType(TypeDescriptor.ERROR);
        } else {
            node.setResType(att.getType());
            node.setDefinition(att);
        }
	}

	@Override
	public void visit(NodePrint node) {
        node.getId().accept(this);
        node.setResType(node.getId().getResType());
	}
	
	private boolean compatible(TypeDescriptor t1, TypeDescriptor t2) {
        if(t1 != TypeDescriptor.ERROR && t2 != TypeDescriptor.ERROR && t1 == t2 ){
            return true;
        }
        if(t1 == TypeDescriptor.FLOAT && t2 == TypeDescriptor.INT){
            return true;
        }
        return false;		
	}
	
	private NodeExpr convertExpr(NodeExpr node) {
        if(node.getResType() == TypeDescriptor.FLOAT){
            return node;
        } else {
            NodeConvert nodeConv = new NodeConvert(node);
            nodeConv.setResType(TypeDescriptor.FLOAT);
            return nodeConv;
        }
	}
	
	public String log() {
		return log.toString();
	}
}