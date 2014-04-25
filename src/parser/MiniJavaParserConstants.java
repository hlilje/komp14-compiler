/* Generated By:JavaCC: Do not edit this line. MiniJavaParserConstants.java */
package parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface MiniJavaParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int SINGLE_LINE_COMMENT = 6;
  /** RegularExpression Id. */
  int MULTI_LINE_COMMENT = 7;
  /** RegularExpression Id. */
  int CLASS = 10;
  /** RegularExpression Id. */
  int PUBLIC = 11;
  /** RegularExpression Id. */
  int STATIC = 12;
  /** RegularExpression Id. */
  int VOID = 13;
  /** RegularExpression Id. */
  int STRING = 14;
  /** RegularExpression Id. */
  int RETURN = 15;
  /** RegularExpression Id. */
  int INT = 16;
  /** RegularExpression Id. */
  int BOOLEAN = 17;
  /** RegularExpression Id. */
  int IF = 18;
  /** RegularExpression Id. */
  int ELSE = 19;
  /** RegularExpression Id. */
  int WHILE = 20;
  /** RegularExpression Id. */
  int PRINT = 21;
  /** RegularExpression Id. */
  int LENGTH = 22;
  /** RegularExpression Id. */
  int TRUE = 23;
  /** RegularExpression Id. */
  int FALSE = 24;
  /** RegularExpression Id. */
  int THIS = 25;
  /** RegularExpression Id. */
  int NEW = 26;
  /** RegularExpression Id. */
  int AND = 27;
  /** RegularExpression Id. */
  int LT = 28;
  /** RegularExpression Id. */
  int PLUS = 29;
  /** RegularExpression Id. */
  int MINUS = 30;
  /** RegularExpression Id. */
  int STAR = 31;
  /** RegularExpression Id. */
  int BANG = 32;
  /** RegularExpression Id. */
  int ASSIGN = 33;
  /** RegularExpression Id. */
  int GT = 34;
  /** RegularExpression Id. */
  int LEQ = 35;
  /** RegularExpression Id. */
  int GEQ = 36;
  /** RegularExpression Id. */
  int EQ = 37;
  /** RegularExpression Id. */
  int NEQ = 38;
  /** RegularExpression Id. */
  int OR = 39;
  /** RegularExpression Id. */
  int LBRACE = 40;
  /** RegularExpression Id. */
  int RBRACE = 41;
  /** RegularExpression Id. */
  int LPAREN = 42;
  /** RegularExpression Id. */
  int RPAREN = 43;
  /** RegularExpression Id. */
  int LBRACKET = 44;
  /** RegularExpression Id. */
  int RBRACKET = 45;
  /** RegularExpression Id. */
  int SEMICOLON = 46;
  /** RegularExpression Id. */
  int COMMA = 47;
  /** RegularExpression Id. */
  int DOT = 48;
  /** RegularExpression Id. */
  int IDENTIFIER = 49;
  /** RegularExpression Id. */
  int INTEGER_LITERAL = 50;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int WithinComment = 1;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\f\"",
    "<SINGLE_LINE_COMMENT>",
    "\"/*\"",
    "\"*/\"",
    "<token of kind 9>",
    "\"class\"",
    "\"public\"",
    "\"static\"",
    "\"void\"",
    "\"String\"",
    "\"return\"",
    "\"int\"",
    "\"boolean\"",
    "\"if\"",
    "\"else\"",
    "\"while\"",
    "\"System.out.println\"",
    "\"length\"",
    "\"true\"",
    "\"false\"",
    "\"this\"",
    "\"new\"",
    "\"&&\"",
    "\"<\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"!\"",
    "\"=\"",
    "\">\"",
    "\"<=\"",
    "\">=\"",
    "\"==\"",
    "\"!=\"",
    "\"||\"",
    "\"{\"",
    "\"}\"",
    "\"(\"",
    "\")\"",
    "\"[\"",
    "\"]\"",
    "\";\"",
    "\",\"",
    "\".\"",
    "<IDENTIFIER>",
    "<INTEGER_LITERAL>",
  };

}
