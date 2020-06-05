package token;

public class Token {

	private int riga;
	private TokenType tipo;
	private String val;
	
	public Token(TokenType tipo, int riga, String val) {
		this.tipo = tipo;
		this.riga = riga;
		this.val = val;
	}
	
	public Token(TokenType tipo, int riga) {
		this.tipo = tipo;
		this.riga = riga;
	}

    
	public int getRiga() {
		return riga;
	}
     
	public TokenType getType() {
		return this.tipo;
	}

	public String getVal() {
        return this.val;
	}
	
	public String toString() {
		return "<" + tipo.toString() + "," + val + "," + riga + ">";
	}
	
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Token) {
            Token token = (Token) obj;
            if (this.riga == token.getRiga() && this.tipo == token.getType()) {
                if (this.val == null && token.getVal() == null)
                    return true;
                if(val.equals(token.getVal()))
                    return true;
            }
        }

        return false;
    }
}
