package token;
/*
 * 	Token Pattern Classe rappresentata
	-INT [0-9]+ Costante
	-FLOAT [0-9]+.[0-9]{1,5} Costante
	-ID [a-z]+ Identificatore
	-TYINT int Parola chiave
	-TYFLOAT float Parola chiave
	-ASSIGN = Operatore
	-PRINT print Operatore
	-PLUS + Operatore
	-MINUS - Operatore
	-TIMES * Operatore
	-DIVIDE / Operatore
	-SEMI ; Delimitatore
	EOF (char) -1 Fine Input
 */
public enum TokenType {
	TYFLOAT,
	TYINT,
	PRINT,
	ID,
	INT,
	FLOAT,
	ASSIGN,
	PLUS,
	MINUS,
	TIMES,
	DIV,
	SEMI,
	EOF;
}
