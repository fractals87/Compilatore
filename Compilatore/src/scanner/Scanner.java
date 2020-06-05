package scanner;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import exception.*;
import token.*;
/*
 * 	Token Pattern Classe rappresentata
	INT [0-9]+ Costante
	FLOAT [0-9]+.[0-9]{1,5} Costante
	ID [a-z]+ Identificatore
	TYINT int Parola chiave
	TYFLOAT float Parola chiave
	ASSIGN = Operatore
	PRINT print Operatore
	PLUS + Operatore
	MINUS - Operatore
	TIMES * Operatore
	DIVIDE / Operatore
	SEMI ; Delimitatore
	EOF (char) -1 Fine Input
 */
public class Scanner {
	final char EOF = (char) -1; // int 65535
	final double MAX_DIGIT = 4;
    final double MIN_DIGIT = 0;
	
    private int riga;
	private PushbackReader buffer;
	private String log;
	
	public List<Token> tokens = new ArrayList<Token>();

	private List<Character> skipChars = Arrays.asList(' ', '\n', '\t', '\r', EOF);
	private List<Character> letters = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm','n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
	private List<Character> numbers = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
	
	HashMap<Character, TokenType> hmapChar = new HashMap<Character, TokenType>();
	HashMap<String, TokenType> hmapKey = new HashMap<String, TokenType>();
	
	private Token peeked = null;

	public Scanner(String fileName) throws CompilerException {
		//init hmap 
		hmapChar.put('+',TokenType.PLUS);
		hmapChar.put('-',TokenType.MINUS);
		hmapChar.put('*',TokenType.TIMES);
		hmapChar.put('/',TokenType.DIV);
		hmapChar.put('=',TokenType.ASSIGN);
		hmapChar.put(';',TokenType.SEMI);
		
		hmapKey.put("print",TokenType.PRINT);
		hmapKey.put("float",TokenType.TYFLOAT);
		hmapKey.put("int",TokenType.TYINT);
		//end int hmap
		
		try {
			this.buffer = new PushbackReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			throw new CompilerException(e.getMessage());
		}
		riga = 1;

	}
	
	public Token peekToken() throws CompilerException  {
		return peeked =nextToken();
	}

	public Token nextToken() throws CompilerException {
		if(peeked!=null) {
			Token nextToken = peeked;
			peeked = null;
			return nextToken;
		}

		char nextChar = peekChar();

		if(skipChars.contains(nextChar)) {
			if(nextChar == '\n')
				riga+=1;
			if(nextChar == EOF) 
				return new Token(TokenType.EOF,riga);
			readChar();
			return nextToken();
		}

		if(numbers.contains(nextChar) || nextChar == '.') {
			return scanNumber();
		}
		
		if(letters.contains(nextChar)) {
			return scanId();
		}

		if(hmapChar.containsKey(nextChar)) {
			readChar();
			return new Token(hmapChar.get(nextChar), riga);
		}
		
		throw new CompilerException("Carattere illegale : " + nextChar + " riga " + riga);
	}

	private Token scanId() throws CompilerException {
			String tmp = Character.toString(readChar());
			while(letters.contains(peekChar())) {
				tmp+=Character.toString(readChar());
			}
			
			if(hmapKey.containsKey(tmp)) {
				return new Token(hmapKey.get(tmp), riga);
			}
	
			return new Token(TokenType.ID, riga, tmp);
	}

	private Token scanNumber() throws CompilerException {
		String s="";
		while(numbers.contains(peekChar())) {
			s+=readChar();
		}
		if(peekChar()!='.') 
			return new Token(TokenType.INT, riga, s);
		
		s+=readChar();
		int nC = 0;
		while(numbers.contains(peekChar())) {
			nC++;
			s+=readChar();
		}
		if((nC<=MAX_DIGIT) && (nC >= MIN_DIGIT)) 
			return new Token(TokenType.FLOAT, riga, s);
		else
			throw new CompilerException("Float overflow");
	}

	private char readChar() throws CompilerException {
		try {
			return ((char) this.buffer.read());
		} catch (IOException e) {
			throw new CompilerException(e.getMessage());
		}
	}

	private char peekChar() throws CompilerException {
		try {
			char c = (char) buffer.read();
			buffer.unread(c);
			return c;
		} catch (IOException e) {
			throw new CompilerException(e.getMessage());
		}
	}
	
	public String getLog() {
		return log.toString();
	}
}