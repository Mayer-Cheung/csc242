/* A Bison parser, made by GNU Bison 2.4.3.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java
   
      Copyright (C) 2007, 2008, 2009, 2010 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* First part of user declarations.  */

/* Line 32 of lalr1.java  */
/* Line 1 of "Parser.y"  */

/* -*- Mode: yacc; -*-
 *
 * File: Parser.y
 * Creator: George Ferguson
 * Created: Tue Mar 13 14:46:31 2012
 * Time-stamp: <Wed Mar 14 17:50:27 EDT 2012 ferguson>
 *
 * Grammar for propositional logic written in yacc/bison format with
 * semantic actions in Java. Created by cutting down the original
 * FOL parser.
 */

package pl.parser;
import pl.core.*;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;


/**
 * A Bison parser, automatically generated from <tt>Parser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public class Parser
{
    /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "2.4.3";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";


  /** True if verbose error messages are enabled.  */
  public boolean errorVerbose = false;



  /** Token returned by the scanner to signal the end of its input.  */
  public static final int EOF = 0;

/* Tokens.  */
  /** Token number, to be returned by the scanner.  */
  public static final int NAME = 258;
  /** Token number, to be returned by the scanner.  */
  public static final int SEMICOLON = 259;
  /** Token number, to be returned by the scanner.  */
  public static final int IFF = 260;
  /** Token number, to be returned by the scanner.  */
  public static final int IMPLIES = 261;
  /** Token number, to be returned by the scanner.  */
  public static final int OR = 262;
  /** Token number, to be returned by the scanner.  */
  public static final int AND = 263;
  /** Token number, to be returned by the scanner.  */
  public static final int NOT = 264;



  

  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>Parser</tt>.
   */
  public interface Lexer {
    

    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.  */
    Object getLVal ();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token. */
    int yylex () throws java.io.IOException;

    /**
     * Entry point for error reporting.  Emits an error
     * in a user-defined way.
     *
     * 
     * @param s The string for the error message.  */
     void yyerror (String s);
  }

  private class YYLexer implements Lexer {
/* "%code lexer" blocks.  */

/* Line 151 of lalr1.java  */
/* Line 40 of "Parser.y"  */

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




/* Line 151 of lalr1.java  */
/* Line 364 of "Parser.java"  */

  }

  /** The object doing lexical analysis for us.  */
  private Lexer yylexer;
  
  


  /**
   * Instantiates the Bison-generated parser.
   */
  public Parser () {
    this.yylexer = new YYLexer();
    
  }


  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  protected Parser (Lexer yylexer) {
    this.yylexer = yylexer;
    
  }

  private java.io.PrintStream yyDebugStream = System.err;

  /**
   * Return the <tt>PrintStream</tt> on which the debugging output is
   * printed.
   */
  public final java.io.PrintStream getDebugStream () { return yyDebugStream; }

  /**
   * Set the <tt>PrintStream</tt> on which the debug output is printed.
   * @param s The stream that is used for debugging output.
   */
  public final void setDebugStream(java.io.PrintStream s) { yyDebugStream = s; }

  private int yydebug = 0;

  /**
   * Answer the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   */
  public final int getDebugLevel() { return yydebug; }

  /**
   * Set the verbosity of the debugging output; 0 means that all kinds of
   * output from the parser are suppressed.
   * @param level The verbosity level for debugging output.
   */
  public final void setDebugLevel(int level) { yydebug = level; }

  private final int yylex () throws java.io.IOException {
    return yylexer.yylex ();
  }
  protected final void yyerror (String s) {
    yylexer.yyerror (s);
  }

  

  protected final void yycdebug (String s) {
    if (yydebug > 0)
      yyDebugStream.println (s);
  }

  private final class YYStack {
    private int[] stateStack = new int[16];
    
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push (int state, Object value			    ) {
      height++;
      if (size == height)
        {
	  int[] newStateStack = new int[size * 2];
	  System.arraycopy (stateStack, 0, newStateStack, 0, height);
	  stateStack = newStateStack;
	  

	  Object[] newValueStack = new Object[size * 2];
	  System.arraycopy (valueStack, 0, newValueStack, 0, height);
	  valueStack = newValueStack;

	  size *= 2;
	}

      stateStack[height] = state;
      
      valueStack[height] = value;
    }

    public final void pop () {
      height--;
    }

    public final void pop (int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (num > 0) {
	java.util.Arrays.fill (valueStack, height - num + 1, height, null);
        
      }
      height -= num;
    }

    public final int stateAt (int i) {
      return stateStack[height - i];
    }

    public final Object valueAt (int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print (java.io.PrintStream out)
    {
      out.print ("Stack now");

      for (int i = 0; i < height; i++)
        {
	  out.print (' ');
	  out.print (stateStack[i]);
        }
      out.println ();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).  */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).  */
  public static final int YYABORT = 1;

  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.  */
  public static final int YYERROR = 2;

  /**
   * Returned by a Bison action in order to print an error message and start
   * error recovery.  Formally deprecated in Bison 2.4.2's NEWS entry, where
   * a plan to phase it out is discussed.  */
  public static final int YYFAIL = 3;

  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;

  private int yyerrstatus_ = 0;

  /**
   * Return whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.  */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  private int yyaction (int yyn, YYStack yystack, int yylen) 
  {
    Object yyval;
    

    /* If YYLEN is nonzero, implement the default value of the action:
       `$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    if (yylen > 0)
      yyval = yystack.valueAt (yylen - 1);
    else
      yyval = yystack.valueAt (0);

    yy_reduce_print (yyn, yystack);

    switch (yyn)
      {
	  case 2:
  if (yyn == 2)
    
/* Line 354 of lalr1.java  */
/* Line 386 of "Parser.y"  */
    { result = ((Sentence)(yystack.valueAt (2-(1)))); return YYACCEPT; };
  break;
    

  case 3:
  if (yyn == 3)
    
/* Line 354 of lalr1.java  */
/* Line 390 of "Parser.y"  */
    { yyval = ((Sentence)(yystack.valueAt (3-(2)))); };
  break;
    

  case 4:
  if (yyn == 4)
    
/* Line 354 of lalr1.java  */
/* Line 391 of "Parser.y"  */
    { yyval = ((Sentence)(yystack.valueAt (3-(2)))); };
  break;
    

  case 5:
  if (yyn == 5)
    
/* Line 354 of lalr1.java  */
/* Line 392 of "Parser.y"  */
    { yyval = newUnaryCompoundSentence(((UnaryConnective)(yystack.valueAt (2-(1)))),((Sentence)(yystack.valueAt (2-(2))))); };
  break;
    

  case 6:
  if (yyn == 6)
    
/* Line 354 of lalr1.java  */
/* Line 393 of "Parser.y"  */
    { yyval = newBinaryCompoundSentence(((BinaryConnective)(yystack.valueAt (3-(2)))),((Sentence)(yystack.valueAt (3-(1)))),((Sentence)(yystack.valueAt (3-(3))))); };
  break;
    

  case 7:
  if (yyn == 7)
    
/* Line 354 of lalr1.java  */
/* Line 394 of "Parser.y"  */
    { yyval = ((Symbol)(yystack.valueAt (1-(1)))); };
  break;
    

  case 13:
  if (yyn == 13)
    
/* Line 354 of lalr1.java  */
/* Line 401 of "Parser.y"  */
    { yyval = symtab.intern(((String)(yystack.valueAt (1-(1))))); };
  break;
    



/* Line 354 of lalr1.java  */
/* Line 623 of "Parser.java"  */
	default: break;
      }

    yy_symbol_print ("-> $$ =", yyr1_[yyn], yyval);

    yystack.pop (yylen);
    yylen = 0;

    /* Shift the result of the reduction.  */
    yyn = yyr1_[yyn];
    int yystate = yypgoto_[yyn - yyntokens_] + yystack.stateAt (0);
    if (0 <= yystate && yystate <= yylast_
	&& yycheck_[yystate] == yystack.stateAt (0))
      yystate = yytable_[yystate];
    else
      yystate = yydefgoto_[yyn - yyntokens_];

    yystack.push (yystate, yyval);
    return YYNEWSTATE;
  }

  /* Return YYSTR after stripping away unnecessary quotes and
     backslashes, so that it's suitable for yyerror.  The heuristic is
     that double-quoting is unnecessary unless the string contains an
     apostrophe, a comma, or backslash (other than backslash-backslash).
     YYSTR is taken from yytname.  */
  private final String yytnamerr_ (String yystr)
  {
    if (yystr.charAt (0) == '"')
      {
        StringBuffer yyr = new StringBuffer ();
        strip_quotes: for (int i = 1; i < yystr.length (); i++)
          switch (yystr.charAt (i))
            {
            case '\'':
            case ',':
              break strip_quotes;

            case '\\':
	      if (yystr.charAt(++i) != '\\')
                break strip_quotes;
              /* Fall through.  */
            default:
              yyr.append (yystr.charAt (i));
              break;

            case '"':
              return yyr.toString ();
            }
      }
    else if (yystr.equals ("$end"))
      return "end of input";

    return yystr;
  }

  /*--------------------------------.
  | Print this symbol on YYOUTPUT.  |
  `--------------------------------*/

  private void yy_symbol_print (String s, int yytype,
			         Object yyvaluep				 )
  {
    if (yydebug > 0)
    yycdebug (s + (yytype < yyntokens_ ? " token " : " nterm ")
	      + yytname_[yytype] + " ("
	      + (yyvaluep == null ? "(null)" : yyvaluep.toString ()) + ")");
  }

  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse () throws java.io.IOException
  {
    /// Lookahead and lookahead in internal form.
    int yychar = yyempty_;
    int yytoken = 0;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;

    YYStack yystack = new YYStack ();

    /* Error handling.  */
    int yynerrs_ = 0;
    

    /// Semantic value of the lookahead.
    Object yylval = null;

    int yyresult;

    yycdebug ("Starting parse\n");
    yyerrstatus_ = 0;


    /* Initialize the stack.  */
    yystack.push (yystate, yylval);

    int label = YYNEWSTATE;
    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
	   pushed when we come here.  */
      case YYNEWSTATE:
        yycdebug ("Entering state " + yystate + "\n");
        if (yydebug > 0)
          yystack.print (yyDebugStream);

        /* Accept?  */
        if (yystate == yyfinal_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yyn == yypact_ninf_)
          {
            label = YYDEFAULT;
	    break;
          }

        /* Read a lookahead token.  */
        if (yychar == yyempty_)
          {
	    yycdebug ("Reading a token: ");
	    yychar = yylex ();
            
            yylval = yylexer.getLVal ();
          }

        /* Convert token to internal form.  */
        if (yychar <= EOF)
          {
	    yychar = yytoken = EOF;
	    yycdebug ("Now at end of input.\n");
          }
        else
          {
	    yytoken = yytranslate_ (yychar);
	    yy_symbol_print ("Next token is", yytoken,
			     yylval);
          }

        /* If the proper action on seeing token YYTOKEN is to reduce or to
           detect an error, take that action.  */
        yyn += yytoken;
        if (yyn < 0 || yylast_ < yyn || yycheck_[yyn] != yytoken)
          label = YYDEFAULT;

        /* <= 0 means reduce or error.  */
        else if ((yyn = yytable_[yyn]) <= 0)
          {
	    if (yyn == 0 || yyn == yytable_ninf_)
	      label = YYFAIL;
	    else
	      {
	        yyn = -yyn;
	        label = YYREDUCE;
	      }
          }

        else
          {
            /* Shift the lookahead token.  */
	    yy_symbol_print ("Shifting", yytoken,
			     yylval);

            /* Discard the token being shifted.  */
            yychar = yyempty_;

            /* Count tokens shifted since error; after three, turn off error
               status.  */
            if (yyerrstatus_ > 0)
              --yyerrstatus_;

            yystate = yyn;
            yystack.push (yystate, yylval);
            label = YYNEWSTATE;
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYFAIL;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction (yyn, yystack, yylen);
	yystate = yystack.stateAt (0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYFAIL:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
	    ++yynerrs_;
	    yyerror (yysyntax_error (yystate, yytoken));
          }

        
        if (yyerrstatus_ == 3)
          {
	    /* If just tried and failed to reuse lookahead token after an
	     error, discard it.  */

	    if (yychar <= EOF)
	      {
	      /* Return failure if at end of input.  */
	      if (yychar == EOF)
	        return false;
	      }
	    else
	      yychar = yyempty_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*---------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `---------------------------------------------------*/
      case YYERROR:

        
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt (0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;	/* Each real token shifted decrements this.  */

        for (;;)
          {
	    yyn = yypact_[yystate];
	    if (yyn != yypact_ninf_)
	      {
	        yyn += yyterror_;
	        if (0 <= yyn && yyn <= yylast_ && yycheck_[yyn] == yyterror_)
	          {
	            yyn = yytable_[yyn];
	            if (0 < yyn)
		      break;
	          }
	      }

	    /* Pop the current state because it cannot handle the error token.  */
	    if (yystack.height == 1)
	      return false;

	    
	    yystack.pop ();
	    yystate = yystack.stateAt (0);
	    if (yydebug > 0)
	      yystack.print (yyDebugStream);
          }

	

        /* Shift the error token.  */
        yy_symbol_print ("Shifting", yystos_[yyn],
			 yylval);

        yystate = yyn;
	yystack.push (yyn, yylval);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
  }

  // Generate an error message.
  private String yysyntax_error (int yystate, int tok)
  {
    if (errorVerbose)
      {
        int yyn = yypact_[yystate];
        if (yypact_ninf_ < yyn && yyn <= yylast_)
          {
	    StringBuffer res;

	    /* Start YYX at -YYN if negative to avoid negative indexes in
	       YYCHECK.  */
	    int yyxbegin = yyn < 0 ? -yyn : 0;

	    /* Stay within bounds of both yycheck and yytname.  */
	    int yychecklim = yylast_ - yyn + 1;
	    int yyxend = yychecklim < yyntokens_ ? yychecklim : yyntokens_;
	    int count = 0;
	    for (int x = yyxbegin; x < yyxend; ++x)
	      if (yycheck_[x + yyn] == x && x != yyterror_)
	        ++count;

	    // FIXME: This method of building the message is not compatible
	    // with internationalization.
	    res = new StringBuffer ("syntax error, unexpected ");
	    res.append (yytnamerr_ (yytname_[tok]));
	    if (count < 5)
	      {
	        count = 0;
	        for (int x = yyxbegin; x < yyxend; ++x)
	          if (yycheck_[x + yyn] == x && x != yyterror_)
		    {
		      res.append (count++ == 0 ? ", expecting " : " or ");
		      res.append (yytnamerr_ (yytname_[x]));
		    }
	      }
	    return res.toString ();
          }
      }

    return "syntax error";
  }


  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
  private static final byte yypact_ninf_ = -4;
  private static final byte yypact_[] =
  {
        -1,    -4,    -4,    -1,    -1,     3,    22,    -1,    -4,    14,
      10,    -4,    -4,    -4,    -4,    -4,    -4,    -1,    -4,    -4,
      -4,    26
  };

  /* YYDEFACT[S] -- default rule to reduce with in state S when YYTABLE
     doesn't specify something else to do.  Zero means the default is an
     error.  */
  private static final byte yydefact_[] =
  {
         0,    13,     8,     0,     0,     0,     0,     0,     7,     0,
       0,     1,     2,    12,    11,    10,     9,     0,     5,     3,
       4,     6
  };

  /* YYPGOTO[NTERM-NUM].  */
  private static final byte yypgoto_[] =
  {
        -4,    -4,    -3,    -4,    -4,    -4
  };

  /* YYDEFGOTO[NTERM-NUM].  */
  private static final byte
  yydefgoto_[] =
  {
        -1,     5,     6,     7,    17,     8
  };

  /* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule which
     number is the opposite.  If zero, do what YYDEFACT says.  */
  private static final byte yytable_ninf_ = -1;
  private static final byte
  yytable_[] =
  {
         9,    10,     1,    11,    18,     0,     0,     0,     2,     3,
       0,     4,     0,     0,    21,    13,    14,    15,    16,    13,
      14,    15,    16,    20,     0,    19,    12,    13,    14,    15,
      16,    13,    14,    15,    16
  };

  /* YYCHECK.  */
  private static final byte
  yycheck_[] =
  {
         3,     4,     3,     0,     7,    -1,    -1,    -1,     9,    10,
      -1,    12,    -1,    -1,    17,     5,     6,     7,     8,     5,
       6,     7,     8,    13,    -1,    11,     4,     5,     6,     7,
       8,     5,     6,     7,     8
  };

  /* STOS_[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
  private static final byte
  yystos_[] =
  {
         0,     3,     9,    10,    12,    15,    16,    17,    19,    16,
      16,     0,     4,     5,     6,     7,     8,    18,    16,    11,
      13,    16
  };

  /* TOKEN_NUMBER_[YYLEX-NUM] -- Internal symbol number corresponding
     to YYLEX-NUM.  */
  private static final short
  yytoken_number_[] =
  {
         0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
      40,    41,    91,    93
  };

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
  private static final byte
  yyr1_[] =
  {
         0,    14,    15,    16,    16,    16,    16,    16,    17,    18,
      18,    18,    18,    19
  };

  /* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
  private static final byte
  yyr2_[] =
  {
         0,     2,     2,     3,     3,     2,     3,     1,     1,     1,
       1,     1,     1,     1
  };

  /* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
     First, the terminals, then, starting at \a yyntokens_, nonterminals.  */
  private static final String yytname_[] =
  {
    "$end", "error", "$undefined", "NAME", "SEMICOLON", "IFF", "IMPLIES",
  "OR", "AND", "NOT", "'('", "')'", "'['", "']'", "$accept", "Entry",
  "Sentence", "UnaryConnective", "BinaryConnective", "PropositionSymbol", null
  };

  /* YYRHS -- A `-1'-separated list of the rules' RHS.  */
  private static final byte yyrhs_[] =
  {
        15,     0,    -1,    16,     4,    -1,    10,    16,    11,    -1,
      12,    16,    13,    -1,    17,    16,    -1,    16,    18,    16,
      -1,    19,    -1,     9,    -1,     8,    -1,     7,    -1,     6,
      -1,     5,    -1,     3,    -1
  };

  /* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
     YYRHS.  */
  private static final byte yyprhs_[] =
  {
         0,     0,     3,     6,    10,    14,    17,    21,    23,    25,
      27,    29,    31,    33
  };

  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
  private static final short yyrline_[] =
  {
         0,   386,   386,   390,   391,   392,   393,   394,   397,   399,
     399,   399,   399,   401
  };

  // Report on the debug stream that the rule yyrule is going to be reduced.
  private void yy_reduce_print (int yyrule, YYStack yystack)
  {
    if (yydebug == 0)
      return;

    int yylno = yyrline_[yyrule];
    int yynrhs = yyr2_[yyrule];
    /* Print the symbols being reduced, and their result.  */
    yycdebug ("Reducing stack by rule " + (yyrule - 1)
	      + " (line " + yylno + "), ");

    /* The symbols being reduced.  */
    for (int yyi = 0; yyi < yynrhs; yyi++)
      yy_symbol_print ("   $" + (yyi + 1) + " =",
		       yyrhs_[yyprhs_[yyrule] + yyi],
		       ((yystack.valueAt (yynrhs-(yyi + 1)))));
  }

  /* YYTRANSLATE(YYLEX) -- Bison symbol number corresponding to YYLEX.  */
  private static final byte yytranslate_table_[] =
  {
         0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
      10,    11,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,    12,     2,    13,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9
  };

  private static final byte yytranslate_ (int t)
  {
    if (t >= 0 && t <= yyuser_token_number_max_)
      return yytranslate_table_[t];
    else
      return yyundef_token_;
  }

  private static final int yylast_ = 34;
  private static final int yynnts_ = 6;
  private static final int yyempty_ = -2;
  private static final int yyfinal_ = 11;
  private static final int yyterror_ = 1;
  private static final int yyerrcode_ = 256;
  private static final int yyntokens_ = 14;

  private static final int yyuser_token_number_max_ = 264;
  private static final int yyundef_token_ = 2;

/* User implementation code.  */
/* Unqualified %code blocks.  */

/* Line 876 of lalr1.java  */
/* Line 274 of "Parser.y"  */


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
    
    public UnaryCompoundSentence newUnaryCompoundSentence(UnaryConnective connective, Sentence sentence) {
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


/* Line 876 of lalr1.java  */
/* Line 1282 of "Parser.java"  */

}


/* Line 880 of lalr1.java  */
/* Line 404 of "Parser.y"  */


