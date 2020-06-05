package parser;

import token.*;

import java.util.ArrayList;

import exception.*;
import ast.*;
import scanner.*;

public class Parser {
	
	private Scanner scanner;
    private Token currentToken;
	
	public Parser(Scanner scanner) throws CompilerException {
		this.scanner = scanner;
	}
	
	public NodeAST parse() throws CompilerException{
		return parsePrg();
	}
	
	public NodeProgram parsePrg() throws CompilerException {
		Token tk = scanner.peekToken();
		switch(tk.getType()) {
			case TYFLOAT:
			case TYINT:
			case ID:
			case PRINT:
			case EOF:
				NodeProgram node = new NodeProgram(parseDSs());
				match(TokenType.EOF);
				return node;
			default:
				throw new CompilerException("parsePrg - Error at line: " + tk.getRiga());				
		}
	}
	
	private ArrayList<NodeDecSt> parseDSs() throws CompilerException {
		Token tk = scanner.peekToken();
		switch(tk.getType()) {
			case TYFLOAT:
			case TYINT:
			{
				NodeDecl nodeDec = parseDcl();
				ArrayList<NodeDecSt> nodes = parseDSs();
				nodes.add(0, nodeDec);
				return nodes;
			}
			case ID:
			case PRINT:
			{
				NodeStm nodeStm = parseStm();
				ArrayList<NodeDecSt> nodes = parseDSs();
				nodes.add(0, nodeStm);
				return nodes;
			}
			case EOF:
				return new ArrayList<NodeDecSt>();
			default: 
				throw new CompilerException("parseDSs - Error at line: " + tk.getRiga());				
		}
	}
	
	private NodeDecl parseDcl() throws CompilerException {
		Token tk = scanner.peekToken();
		switch(tk.getType()) {
			case TYFLOAT:
			{
				match(TokenType.TYFLOAT);
				match(TokenType.ID);
				NodeDecl node = new NodeDecl(new NodeId(currentToken.getVal()), LangType.FLOAT);
				match(TokenType.SEMI);		
                return node;
			}
			case TYINT:
			{
				match(TokenType.TYINT);
				match(TokenType.ID);
				NodeDecl node = new NodeDecl(new NodeId(currentToken.getVal()), LangType.INT);
				match(TokenType.SEMI);		
                return node;
			}
			default: 
				throw new CompilerException("parseDcl - Error at line: " + tk.getRiga());									
		}
	}

	private NodeStm parseStm() throws CompilerException {
		Token tk = scanner.peekToken();
		switch(tk.getType()) {
			case ID:
			{
				match(TokenType.ID);
				match(TokenType.ASSIGN);
				NodeExpr nodeExpr = parseExp(); 
				NodeStm nodeStm = new NodeAssign(new NodeId(tk.getVal()), nodeExpr);
				match(TokenType.SEMI);		
				return nodeStm;
			}
			case PRINT:
			{
				match(TokenType.PRINT);
                match(TokenType.ID);		
                NodeStm node = new NodePrint(new NodeId(currentToken.getVal()));
                match(TokenType.SEMI);		
                return node;
			}
			default: 
				throw new CompilerException("parseStm - Error at line: " + tk.getRiga());			                
		}

	}
	
	private NodeExpr parseExp() throws CompilerException {
		Token tk = scanner.peekToken();
		switch(tk.getType()) {
			case INT:
			case FLOAT:
			case ID:
				NodeExpr nodeExp = parseTr();
				return parseExpP(nodeExp);
			default: 
				throw new CompilerException("parseExp - Error at line: " + tk.getRiga());							
		}

	}
	
	private NodeExpr parseExpP(NodeExpr nodeExpLeft) throws CompilerException {
		Token tk = scanner.peekToken();
		//System.out.println(nodeExpLeft.toString());
		//System.out.println(tk.toString());
		switch(tk.getType()) {
			case PLUS:
			{
				match(TokenType.PLUS);
				NodeExpr nodeExpRight = parseExp();					
                return new NodeBinOp(LangOper.PLUS, nodeExpLeft, nodeExpRight);
			}
			case MINUS:
			{
				match(TokenType.MINUS);
				NodeExpr nodeExpRight = parseExp();					
                return new NodeBinOp(LangOper.MINUS, nodeExpLeft, nodeExpRight);
			}
			case SEMI:
				return nodeExpLeft;
			default: 
				throw new CompilerException("parseExpP - Error at line: " + tk.getRiga() + " token: " + tk.toString());							
		}
	}
	
	private NodeExpr parseTr() throws CompilerException {
		Token tk = scanner.peekToken();
		switch(tk.getType()) {
			case INT:
			case FLOAT:
			case ID:
				NodeExpr node = parseVal();
				return parseTrP(node);
			default: 
				throw new CompilerException("parseTr - Error at line: " + tk.getRiga());							
		}
	}
	
	private NodeExpr parseTrP(NodeExpr nodeExpLeft) throws CompilerException {
		Token tk = scanner.peekToken();
		switch(tk.getType()) {
			case TIMES:
			{
                match(TokenType.TIMES);
                NodeExpr nodeExpRight = parseTr();
                return new NodeBinOp(LangOper.TIMES, nodeExpLeft, nodeExpRight);
			}
			case DIV:
			{
				match(TokenType.DIV);
                NodeExpr nodeExpRight = parseTr();
                return new NodeBinOp(LangOper.DIV, nodeExpLeft, nodeExpRight);
			}
			case SEMI:
                return nodeExpLeft;
			default: 
				throw new CompilerException("parseTrP - Error at line: " + tk.getRiga() + " token:" + tk.toString());	
		}

	}
	
	private NodeExpr parseVal() throws CompilerException {
		Token tk = scanner.peekToken();
		switch(tk.getType()) {
			case INT:
                match(TokenType.INT);
                return new NodeCost(tk.getVal(), LangType.INT);
			case FLOAT:
                match(TokenType.FLOAT);
                return new NodeCost(tk.getVal(), LangType.FLOAT);
			case ID:
                match(TokenType.ID);
                return new NodeDeref(new NodeId(tk.getVal()));
			default: 
				throw new CompilerException("parseVal - Error at line: " + tk.getRiga());							
		}
	}
	
	private void match(TokenType tType) throws CompilerException {
		if(scanner.peekToken().getType() != tType)
			throw new CompilerException("match - Error at line: " + scanner.peekToken().getRiga());
		currentToken = scanner.nextToken();
	}
}
