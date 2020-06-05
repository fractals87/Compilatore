package ast;

import java.util.ArrayList;

import exception.RegisterException;
import visitor.IVisitor;

public class NodeProgram extends NodeAST {
    private ArrayList<NodeDecSt> decSts;
    
	public  NodeProgram(ArrayList<NodeDecSt> NodeDecStmList){
        this.decSts = NodeDecStmList;
    }

    public ArrayList<NodeDecSt> getDecSts() {
		return decSts;
	}
	
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("<NodeProgram, decSts: [");
        boolean isFirst = true;
        for (NodeDecSt node : decSts) {
            if (!isFirst) {
                str.append(", ");
            }
            isFirst = false;
            str.append(node.toString());
        }
        str.append("]>");
        return str.toString();
    }

	@Override
	public void accept(IVisitor visitor) throws RegisterException {
        visitor.visit(this);
	}
}
