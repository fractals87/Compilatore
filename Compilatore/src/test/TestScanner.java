package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import scanner.Scanner;
import token.Token;
import token.TokenType;

public class TestScanner {
	
	
	@Test
	public void test1() throws IOException, Exception {
		Scanner scanner;
		String path ="src/test/data/testScanner.txt";
		scanner = new Scanner(path);

		assertTrue(scanner.nextToken().equals(new Token(TokenType.TYINT, 1, null)));
		assertTrue(scanner.nextToken().equals(new Token(TokenType.ID, 1, "pippo")));
		assertTrue(scanner.nextToken().equals(new Token(TokenType.SEMI, 1, null)));
		assertTrue(scanner.nextToken().equals(new Token(TokenType.INT, 2, "4")));
		assertTrue(scanner.nextToken().equals(new Token(TokenType.FLOAT, 3, "4.5")));
		assertTrue(scanner.nextToken().equals(new Token(TokenType.FLOAT, 4, ".6")));
		assertTrue(scanner.nextToken().equals(new Token(TokenType.FLOAT, 5, "7.")));
	}
}
