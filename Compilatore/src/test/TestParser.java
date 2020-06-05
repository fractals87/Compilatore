package test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;

import org.junit.Test;

import ast.NodeProgram;
import exception.*;
import scanner.*;
import parser.*;

public class TestParser {
	@Test
	public void testCorrect() throws CompilerException {
		String path ="src/test/data/testParserCorrect.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		parser.parse();
	}

	@Test
	public void testWrong() throws CompilerException {
		String path ="src/test/data/testParserWrong.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		assertThrows(CompilerException.class, ()->parser.parse());
	}

	@Test
	public void testASTDeclare() throws CompilerException {
		String path ="src/test/data/testParserASTDeclare.txt";
		Scanner scanner = new Scanner(path);
        Parser parser = new Parser(scanner);
        NodeProgram node = parser.parsePrg();
        //System.out.println(node.toString());
        assertEquals(
                "<NodeProgram, decSts: [<NodeDecl, id: a, type: INT>, <NodeDecl, id: dec, type: INT>, <NodePrint, id: a>]>",
                node.toString());
	}

	@Test
	public void testASTExp() throws CompilerException {
		try {
		String path ="src/test/data/testParserASTExp.txt";
		Scanner scanner = new Scanner(path);
        Parser parser = new Parser(scanner);
        NodeProgram node = parser.parsePrg();
        //System.out.println(node.toString());
        assertEquals(
                "<NodeProgram, decSts: [<NodeId: d, NodeExpr: <op: TIMES, left: <NodeId: e>, right: <op: TIMES, left: <NodeId: f>, right: <NodeId: g>>>>]>",
                node.toString());
		}catch(CompilerException ex) {
			System.out.println(ex.getMessage());
		}
	}	
}
