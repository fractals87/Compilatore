package test;

import static org.junit.Assert.*;

import org.junit.Test;

import token.Token;
import token.TokenType;

public class TestToken {

	@Test
	public void test() {
		Token t = new Token(TokenType.ASSIGN,9);
		
		assertEquals(9, t.getRiga());
	}

}
