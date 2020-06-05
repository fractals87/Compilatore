package test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;

import org.junit.Test;

import ast.*;
import exception.*;
import scanner.*;
import symbolTable.*;
import visitor.*;
import parser.*;

public class TestVisitor {

	//TypeCheking

	@Test
	public void testTypeCheckingDuplicate() throws CompilerException, RegisterException {
		String path ="src/test/data/testParserSymbolTableDuplicate.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = parser.parsePrg();
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		SymbolTable.init();
		nodeProgram.accept(visitor);

        boolean isCorrect = true;
        for (NodeDecSt node : nodeProgram.getDecSts()) {
            if (node.getResType() == TypeDescriptor.ERROR)
                isCorrect = false;
        }
        //if(isCorrect)
        //	System.out.println("SI");
        //else
        //	System.out.println("NO " + visitor.log() );
        
        assertEquals(false, isCorrect);
	}
	@Test
	public void testTypeCheckingNotDeclare() throws CompilerException, RegisterException {
		String path ="src/test/data/testVisitorNotDeclare.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = parser.parsePrg();
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		SymbolTable.init();
		nodeProgram.accept(visitor);
		//System.out.println(nodeProgram.toString());
        boolean isCorrect = true;
        for (NodeDecSt node : nodeProgram.getDecSts()) {
            if (node.getResType() == TypeDescriptor.ERROR)
                isCorrect = false;
        }
        //if(isCorrect)
        //	System.out.println("SI");
        //else
        //	System.out.println("NO " + visitor.log() );
        
        assertEquals(false, isCorrect);
	}
	@Test
	public void testTypeCheckingAssign() throws CompilerException, RegisterException {
		String path ="src/test/data/testVisitorAssignIncompatible.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = parser.parsePrg();
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		SymbolTable.init();
		nodeProgram.accept(visitor);
		//System.out.println(nodeProgram.toString());
        boolean isCorrect = true;
        for (NodeDecSt node : nodeProgram.getDecSts()) {
            if (node.getResType() == TypeDescriptor.ERROR)
                isCorrect = false;
        }
        //if(isCorrect)
        //	System.out.println("SI");
        //else
        //	System.out.println("NO " + visitor.log() );
        
        //System.out.println(SymbolTable.toStr());
        
        assertEquals(false, isCorrect);
	}
	@Test
	public void testTypeCheckingConvert() throws CompilerException, RegisterException {
		String path ="src/test/data/testVisitorConvert.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = parser.parsePrg();
		TypeCheckingVisitor visitor = new TypeCheckingVisitor();
		SymbolTable.init();
		nodeProgram.accept(visitor);
		//System.out.println(nodeProgram.toString());
        boolean isCorrect = true;
        boolean existConv = false;
        for (NodeDecSt node : nodeProgram.getDecSts()) {
            if (node.getResType() == TypeDescriptor.ERROR)
                isCorrect = false;
            //System.out.println(node.getClass().getCanonicalName());
            if(node instanceof NodeAssign ) {
            	if(((NodeAssign)node).getExpr() instanceof NodeConvert) {
            		existConv = true;
            	}
            		
            }
            	
        }
        //if(isCorrect)
        //	System.out.println("SI");
        //else
        //	System.out.println("NO " + visitor.log() );
        assertEquals(true, existConv);       
        assertEquals(true, isCorrect);
	}

	//CodeGenerator
	@Test	
	public void testCodeGeneretorCorrect() throws CompilerException, RegisterException {
		String path ="src/test/data/testVisitorCodeGen.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		NodeProgram nodeProgram = parser.parsePrg();
		TypeCheckingVisitor typeVisitor = new TypeCheckingVisitor();
		CodeGeneratorVisitor codeVisitor = new CodeGeneratorVisitor();
		SymbolTable.init();
		nodeProgram.accept(typeVisitor);
		//System.out.println(nodeProgram.toString());
        boolean isCorrect = true;
        for (NodeDecSt node : nodeProgram.getDecSts()) {
            if (node.getResType() == TypeDescriptor.ERROR)
                isCorrect = false;
        }
        //if(isCorrect)
        //	System.out.println("SI");
        //else
        //	System.out.println("NO " + visitor.log() );
        
        assertEquals(true, isCorrect);
        
		nodeProgram.accept(codeVisitor);
        //System.out.println(SymbolTable.toStr());
		String expected = "1.0 6 5 k / sb 0 k lb p P 1 6 / sa 0 k la p P ";
		//System.out.println(expected);
		//System.out.println(codeVisitor.codice.toString());
		assertEquals(expected, codeVisitor.codice.toString());
	}
}
