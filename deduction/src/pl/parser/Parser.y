%{
/* -*- Mode: yacc; -*-
 *
 * File: Parser.y
 * Creator: George Ferguson
 * Created: Tue Mar 13 14:46:31 2012
 * Time-stamp: <Thu Feb 25 17:53:30 EST 2016 ferguson>
 *
 * Grammar for propositional logic written in yacc/bison format with
 * semantic actions in Java. Created by cutting down the original
 * FOL parser.
 */

package pl.parser;
import parser.core.*;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
%}

/*
 * Bison declarations
 */
%define parser_class_name "Parser"
%define public

/* YACC Declarations */
%token NAME SEMICOLON
%left IMPLIES IFF
%left AND OR
%left NOT

%type <Sentence> Sentence
%type <UnaryConnective> UnaryConnective NOT
%type <BinaryConnective> BinaryConnective AND OR IMPLIES IFF
%type <PropositionSymbol> PropositionSymbol
%type <String> NAME

/*
 * Lexer code
 */
%code lexer {
    /**
     * Lexer method: Used to report an error message.
     * As has been the case since the days of yacc, this is pretty useless
     * since it only ever gets "syntax error" and can't throw an exception
     * anyway because of the signature in the Lexer interface.
     * But has to be defined.
     */
    public void yyerror(String s) {
    }

    /**
     * The semantic value associated with the current lexical token.
     */
    protected Object yylval;

    /**
     * Return the semantic value associated with the current lexical token.
     */
    public Object getLVal() {
	return yylval;
    }

    /**
     * Buffer used while reading tokens.
     */
    protected StringBuilder buf = new StringBuilder();

    /**
     * Lexer method: Return the next token and set yylval appropriately.
     * Returns 0 on EOF, -1 or error, otherwise a token code (>0).
     */
    public int yylex() throws IOException {
	int i;
	char ch;
	// Whitespace and comments
	while (true) {
	    skipWhitespace();
	    i = readChar();
	    if (i == -1) {
		//System.err.println("yylex: EOF");
		// Normally we would return EOF to let the parser finish.
		// However since we want a per-sentence parser, we need
		// to handle that differently...
		//return EOF;
		throw new EOFException();
	    }
	    ch = (char)i;
	    if (ch == '#') {
		// Comment to end of line
		do {
		    i = readChar();
		} while (i != -1 && i != '\n');
	    } else {
		break;
	    }
	}
	//System.err.println("yylex: ch='" + ch + "'");
	// Handle single and multi-char tokens
	if (ch == ';') {
	    return SEMICOLON;
	} else if (ch == '(' || ch == ')' || ch == '[' || ch == ']') {
	    return ch;
	} else if (ch == '~' || ch == '!') {
	    yylval = UnaryConnective.NOT;
	    return NOT;
	} else if (ch == '&') {
	    yylval = BinaryConnective.AND;
	    return AND;
	} else if (ch == '|') {
	    yylval = BinaryConnective.OR;
	    return OR;
	} else if (ch == '=') {
	    i = readChar();
	    if (i == '>') {
		yylval = BinaryConnective.IMPLIES;
		return IMPLIES;
	    }
	    error("bad token: =" + (char)i);
	} else if (ch == '<') {
	    i = readChar();
	    if (i == '=') {
		i = readChar();
		if (i == '>') {
		    yylval = BinaryConnective.IFF;
		    return IFF;
		} else {
		  error("bad token: <=" + (char)i);
		}
	    } else {
		error("bad token: <" + (char)i);
	    }
	} else if (!isIdentifierStart(ch))  {
	    // Otherwise if not start of identifier, error
	    error("expected start of identifier: " + ch);
	}
	// Need to read an identifier
	buf.setLength(0);
	buf.append(ch);
	while (true) {
	    i = readChar();
	    if (i == -1) {
		break;
	    }
	    ch = (char)i;
	    if (!isIdentifierChar(ch)) {
		input.unread(ch);
		break;
	    }
	    buf.append(ch);
	}
	String str = buf.toString();
	// Check for ``reserved'' identifiers
	if (str.equalsIgnoreCase("NOT")) {
	    yylval = UnaryConnective.NOT;
	    return NOT;
	} else if (str.equalsIgnoreCase("AND")) {
	    yylval = BinaryConnective.AND;
	    return AND;
	} else if (str.equalsIgnoreCase("OR")) {
	    yylval = BinaryConnective.OR;
	    return OR;
	} else if (str.equalsIgnoreCase("IMPLIES")) {
	    yylval = BinaryConnective.IMPLIES;
	    return IMPLIES;
	} else if (str.equalsIgnoreCase("IFF")) {
	    yylval = BinaryConnective.IFF;
	    return IFF;
	}
	// Otherwise its an identifier
	yylval = str;
	return NAME;
    }

    /**
     * Report an error by throwing a ParserException.
     * Probably should do something different now that we're in bison.
     */
    protected void error(String msg) throws ParserException {
	msg = Integer.toString(lineNum) + ":" + Integer.toString(charNum) + ": " + msg;
	throw new ParserException(msg);
    }

    protected int lineNum = 1;
    protected int charNum = 0;
    protected int lastLineLen = 0;

    protected int readChar() throws IOException {
	int i = input.read();
	if (i == -1) {
	    //System.err.println("readChar: EOF");
	    return i;
	} else if (i == '\n') {
	    lineNum += 1;
	    lastLineLen = charNum;
	    charNum = 0;
	} else {
	    charNum += 1;
	}
	//System.err.println("readChar: '" + (char)i + "' @ " + lineNum + ":" + charNum);
	return i;
    }

    protected void unreadChar(int i) throws IOException {
	unreadChar((char)i);
    }

    protected void unreadChar(char ch) throws IOException {
	input.unread(ch);
	if (ch == '\n') {
	    lineNum -=1;
	    charNum = lastLineLen;
	} else {
	    charNum -= 1;
	}
	//System.err.println("unreadChar: " + lineNum + ":" + charNum + ": " + ch);
    }

    protected void skipWhitespace() throws IOException {
	int i;
	do {
	    i = readChar();
	} while (i != -1 && isWhitespace((char)i));
	if (i != -1) {
	    unreadChar(i);
	}
    }

    protected boolean isWhitespace(char ch) {
	return ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r';
    }

    protected boolean isOpenParen(Object token) {
	return token.equals("(") || token.equals("[");
    }

    protected boolean isCloseParen(Object token) {
	return token.equals(")") || token.equals("]");
    }

    protected boolean isCloseParen(Object token, Object opener) {
	return (token.equals(")") && opener.equals("(")) ||
	    (token.equals("]") && opener.equals("["));
    }

    protected boolean isIdentifierStart(char ch) {
	return Character.isLetterOrDigit(ch) || ch == '?';
    }

    protected boolean isIdentifierChar(char ch) {
	return Character.isLetterOrDigit(ch) || ch == '-' || ch == '_' || ch == '^';
    }

    protected boolean isIdentifier(Object token) {
	return (token instanceof String) && !isOpenParen(token) && !isCloseParen(token);
    }

    /**
     * Attempt to recover from the previous error, whetever it was.
     * The strategy is to read up to and including a semi-colon. It
     * might work (for simple errors).
     */
    public void recoverFromError() throws IOException {
	int i;
	do {
	    i = readChar();
	} while (i != -1 && i != ';');
    }

}

/*
 * Parser class code
 */
%code {

    /**
     * The input to this Parser, as (or wrapped in) a PushbackReader.
     */
    protected PushbackReader input;

    /**
     * Construct and return a new Parser whose input is the given
     * PushbackReader.
     */
    public Parser(PushbackReader reader) {
	this();
	this.input = reader;
    }

    /**
     * Construct and return a new Parser whose input is the given
     * Reader.
     */
    public Parser(Reader reader) {
	this(new PushbackReader(reader));
    }

    /**
     * Construct and return a new Parser whose input is the given
     * InputStream.
     */
    public Parser(InputStream input) {
	this(new PushbackReader(new InputStreamReader(input)));
    }

    /**
     * Set the input for this Parser to the given PushbackReader.
     */
    public void setInput(PushbackReader reader) {
	this.input = reader;
    }

    /**
     * Set the input for this Parser to the given Reader.
     */
    public void setInput(Reader reader) {
	this.input = new PushbackReader(reader);
    }

    /**
     * Set the input for this Parser to the given InputStream.
     */
    public void setInput(InputStream ins) {
	this.input = new PushbackReader(new InputStreamReader(ins));
    }

    /**
     * The Sentence created by the last successful call to parse().
     */
    protected Sentence result;

    /**
     * Return the Sentence created by the last successful call to parse().
     */
    public Sentence getResult() {
	return result;
    }

    /**
     * Parse and return the next Sentence from the input, or null if
     * there is some kind of error.
     */
    public Sentence nextSentence() throws IOException {
	if (parse()) {
	    return getResult();
	} else {
	    return null;
	}
    }

    public void recoverFromError() throws IOException {
	((YYLexer)yylexer).recoverFromError();
    }

    public static void main(String argv[]) throws IOException {
	Parser parser;
	if (argv.length == 0) {
	    parser = new Parser(System.in);
	} else {
	    parser = new Parser(new FileReader(argv[0]));
	}
	int n = 1;
	while (true) {
	    try {
		Sentence s = parser.nextSentence();
		System.out.println(n++ + ": " + s.toString());
	    } catch (ParserException e) {
		System.err.println("parse error: " + e.getMessage());
		parser.recoverFromError();
	    } catch (EOFException e) {
		break;
	    } catch (IOException e) {
		System.err.println(e);
		break;
	    }
	}
    }

    // Separated Symbols and SymbolsTables so have to do this here now
    protected SymbolTable symtab = new SymbolTable();

    // Methods needed for parsing since I made some of the classes abstract...
    
    public newUnaryCompoundSentence(UnaryConnective connective, Sentence sentence) {
	switch (connective) {
	case NOT: return new Negation(sentence);
	default:
	    throw new IllegalArgumentException(connective.toString());
	}
    }

    public BinaryCompoundSentence newBinaryCompoundSentence(BinaryConnective connective, Sentence lhs, Sentence rhs) {
    	switch (connective) {
    	case AND: return new Conjunction(lhs, rhs);
    	case OR: return new Disjunction(lhs, rhs);
    	case IMPLIES: return new Implication(lhs, rhs);
    	case IFF: return new Biconditional(lhs, rhs);
    	default:
    		throw new IllegalArgumentException(connective.toString());
    	}
    }

}

%%
/*
 * Grammar follows
 */

Entry: Sentence SEMICOLON		{ result = $1; return YYACCEPT; }
  ;

Sentence:
    '(' Sentence ')'			{ $$ = $2; }
  | '[' Sentence ']'			{ $$ = $2; }
  | UnaryConnective Sentence %prec NOT	{ $$ = newUnaryCompoundSentence($1,$2); }
  | Sentence BinaryConnective Sentence	{ $$ = newBinaryCompoundSentence($2,$1,$3); }
  | PropositionSymbol			{ $$ = $1; }
  ;

UnaryConnective: NOT;

BinaryConnective: AND | OR | IMPLIES | IFF;

PropositionSymbol: NAME			{ $$ = symtab.intern($1); }
  ;

%%
