/* MiniJavaParser.java */
/* Generated By:JavaCC: Do not edit this line. MiniJavaParser.java */
package parser;

import syntaxtree.*;
import visitor.*;

public class MiniJavaParser implements MiniJavaParserConstants {
    public MiniJavaParser() {}

/* GRAMMAR */
  final public Program Program() throws ParseException {MainClass mc = null; ClassDeclList cdl = new ClassDeclList(); ClassDecl cd = null;
    try {
      mc = MainClass();
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case CLASS:{
          ;
          break;
          }
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
        cd = ClassDecl();
cdl.addElement(cd);
      }
      jj_consume_token(0);
    } catch (TokenMgrError e) {
System.err.println(e);
        System.exit(1); // According to Tigris spec

    }
{if ("" != null) return new Program(mc, cdl);}
    throw new Error("Missing return statement in function");
  }

  final public MainClass MainClass() throws ParseException {Identifier i1, i2, i3; StatementList sl = new StatementList(); Statement s;
    VarDeclList vdl = new VarDeclList(); VarDecl vd;
    jj_consume_token(CLASS);
    i1 = Identifier();
    jj_consume_token(LBRACE);
    jj_consume_token(PUBLIC);
    jj_consume_token(STATIC);
    jj_consume_token(VOID);
    i3 = Identifier();
    jj_consume_token(LPAREN);
    jj_consume_token(STRING);
    jj_consume_token(LBRACKET);
    jj_consume_token(RBRACKET);
    i2 = Identifier();
    jj_consume_token(RPAREN);
    jj_consume_token(LBRACE);
    label_2:
    while (true) {
      if (jj_2_1(2)) {
        ;
      } else {
        break label_2;
      }
      vd = VarDecl();
vdl.addElement(vd);
    }
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IF:
      case WHILE:
      case PRINT:
      case LBRACE:
      case IDENTIFIER:{
        ;
        break;
        }
      default:
        jj_la1[1] = jj_gen;
        break label_3;
      }
      s = Stmt();
sl.addElement(s);
    }
    jj_consume_token(RBRACE);
    jj_consume_token(RBRACE);
{if ("" != null) return new MainClass(i1, i2, i3, sl, vdl);}
    throw new Error("Missing return statement in function");
  }

// NOTE ClassDeclExtends is an extension to the grammar
  final public ClassDecl ClassDecl() throws ParseException {Identifier i, j = null; VarDecl vd; VarDeclList vdl = new VarDeclList(); MethodDecl md;
    MethodDeclList mdl = new MethodDeclList();
    jj_consume_token(CLASS);
    i = Identifier();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EXTENDS:{
      jj_consume_token(EXTENDS);
      j = Identifier();
      break;
      }
    default:
      jj_la1[2] = jj_gen;
      ;
    }
    jj_consume_token(LBRACE);
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INT:
      case LONG:
      case BOOLEAN:
      case IDENTIFIER:{
        ;
        break;
        }
      default:
        jj_la1[3] = jj_gen;
        break label_4;
      }
      vd = VarDecl();
vdl.addElement(vd);
    }
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PUBLIC:{
        ;
        break;
        }
      default:
        jj_la1[4] = jj_gen;
        break label_5;
      }
      md = MethodDecl();
mdl.addElement(md);
    }
    jj_consume_token(RBRACE);
{if ("" != null) return j == null ? new ClassDeclSimple(i, vdl, mdl)
        : new ClassDeclExtends(i, j, vdl, mdl);}
    throw new Error("Missing return statement in function");
  }

  final public VarDecl VarDecl() throws ParseException {Type t; Identifier i;
    t = Type();
    i = Identifier();
    jj_consume_token(SEMICOLON);
{if ("" != null) return new VarDecl(t, i);}
    throw new Error("Missing return statement in function");
  }

  final public MethodDecl MethodDecl() throws ParseException {Type t; Identifier i; FormalList fl = new FormalList(); VarDecl vd; VarDeclList vdl  = new VarDeclList();
    Statement s; StatementList sl = new StatementList(); Exp e;
    jj_consume_token(PUBLIC);
    t = Type();
    i = Identifier();
    jj_consume_token(LPAREN);
    fl = FormalList();
    jj_consume_token(RPAREN);
    jj_consume_token(LBRACE);
    label_6:
    while (true) {
      if (jj_2_2(2)) {
        ;
      } else {
        break label_6;
      }
      vd = VarDecl();
vdl.addElement(vd);
    }
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IF:
      case WHILE:
      case PRINT:
      case LBRACE:
      case IDENTIFIER:{
        ;
        break;
        }
      default:
        jj_la1[5] = jj_gen;
        break label_7;
      }
      s = Stmt();
sl.addElement(s);
    }
    jj_consume_token(RETURN);
    e = Exp();
    jj_consume_token(SEMICOLON);
    jj_consume_token(RBRACE);
{if ("" != null) return new MethodDecl(t, i, fl, vdl, sl, e);}
    throw new Error("Missing return statement in function");
  }

  final public FormalList FormalList() throws ParseException {Formal f; FormalList fl = new FormalList(); Type t; Identifier i;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:
    case LONG:
    case BOOLEAN:
    case IDENTIFIER:{
      t = Type();
      i = Identifier();
fl.addElement(new Formal(t, i));
      label_8:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[6] = jj_gen;
          break label_8;
        }
        f = FormalRest();
fl.addElement(f);
      }
      break;
      }
    default:
      jj_la1[7] = jj_gen;

    }
{if ("" != null) return fl;}
    throw new Error("Missing return statement in function");
  }

  final public Formal FormalRest() throws ParseException {Type t; Identifier i;
    jj_consume_token(COMMA);
    t = Type();
    i = Identifier();
{if ("" != null) return new Formal(t, i);}
    throw new Error("Missing return statement in function");
  }

  final public Type Type() throws ParseException {Type t; Identifier i;
    if (jj_2_3(2)) {
      jj_consume_token(INT);
      jj_consume_token(LBRACKET);
      jj_consume_token(RBRACKET);
t = new IntArrayType();
    } else if (jj_2_4(2)) {
      jj_consume_token(LONG);
      jj_consume_token(LBRACKET);
      jj_consume_token(RBRACKET);
t = new LongArrayType();
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case BOOLEAN:{
        jj_consume_token(BOOLEAN);
t = new BooleanType();
        break;
        }
      case INT:{
        jj_consume_token(INT);
t = new IntegerType();
        break;
        }
      case LONG:{
        jj_consume_token(LONG);
t = new LongType();
        break;
        }
      case IDENTIFIER:{
        i = Identifier();
t  = new IdentifierType(i.s);
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return t;}
    throw new Error("Missing return statement in function");
  }

  final public Statement Stmt() throws ParseException {StatementList sl = new StatementList(); Statement s, s1; Statement s2 = null;
    Exp e1, e2; Identifier i; VarDeclList vdl = new VarDeclList();
    VarDecl vd;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LBRACE:{
      jj_consume_token(LBRACE);
      label_9:
      while (true) {
        if (jj_2_5(2)) {
          ;
        } else {
          break label_9;
        }
        vd = VarDecl();
vdl.addElement(vd);
      }
      label_10:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case IF:
        case WHILE:
        case PRINT:
        case LBRACE:
        case IDENTIFIER:{
          ;
          break;
          }
        default:
          jj_la1[9] = jj_gen;
          break label_10;
        }
        s1 = Stmt();
sl.addElement(s1);
      }
      jj_consume_token(RBRACE);
s = new Block(sl, vdl);
      break;
      }
    case IF:{
      jj_consume_token(IF);
      jj_consume_token(LPAREN);
      e1 = Exp();
      jj_consume_token(RPAREN);
      s1 = Stmt();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ELSE:{
        jj_consume_token(ELSE);
        s2 = Stmt();
        break;
        }
      default:
        jj_la1[10] = jj_gen;
        ;
      }
s = new If(e1, s1, s2);
      break;
      }
    case WHILE:{
      jj_consume_token(WHILE);
      jj_consume_token(LPAREN);
      e1 = Exp();
      jj_consume_token(RPAREN);
      s1 = Stmt();
s = new While(e1, s1);
      break;
      }
    case PRINT:{
      jj_consume_token(PRINT);
      jj_consume_token(LPAREN);
      e1 = Exp();
      jj_consume_token(RPAREN);
      jj_consume_token(SEMICOLON);
s = new Print(e1);
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      if (jj_2_6(2)) {
        i = Identifier();
        jj_consume_token(ASSIGN);
        e1 = Exp();
        jj_consume_token(SEMICOLON);
s = new Assign(i, e1);
      } else {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case IDENTIFIER:{
          i = Identifier();
          jj_consume_token(LBRACKET);
          e1 = Exp();
          jj_consume_token(RBRACKET);
          jj_consume_token(ASSIGN);
          e2 = Exp();
          jj_consume_token(SEMICOLON);
s = new ArrayAssign(i, e1, e2);
          break;
          }
        default:
          jj_la1[12] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
{if ("" != null) return s;}
    throw new Error("Missing return statement in function");
  }

// Fixes op precedence but not associativity, should be handled in AST
  final public Exp Exp() throws ParseException {Exp e;
    e = Or();
{if ("" != null) return e;}
    throw new Error("Missing return statement in function");
  }

// Binds the loosest
  final public Exp Or() throws ParseException {Exp e1, e2;
    e1 = And();
    label_11:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case OR:{
        ;
        break;
        }
      default:
        jj_la1[13] = jj_gen;
        break label_11;
      }
      jj_consume_token(OR);
      e2 = And();
e1 = new Or(e1, e2);
    }
{if ("" != null) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp And() throws ParseException {Exp e1, e2;
    e1 = Equality();
    label_12:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case AND:{
        ;
        break;
        }
      default:
        jj_la1[14] = jj_gen;
        break label_12;
      }
      jj_consume_token(AND);
      e2 = Equality();
e1 = new And(e1, e2);
    }
{if ("" != null) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Equality() throws ParseException {Exp e1, e2; String op;
    // NOTE This was changed from [] to ()*
        e1 = Relational();
    label_13:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case EQ:
      case NEQ:{
        ;
        break;
        }
      default:
        jj_la1[15] = jj_gen;
        break label_13;
      }
      op = EqualityOpHelper();
      e2 = Relational();
e1 = CompareOp(op, e1, e2);
    }
{if ("" != null) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public String EqualityOpHelper() throws ParseException {String op;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EQ:{
      jj_consume_token(EQ);
op = "==";
      break;
      }
    case NEQ:{
      jj_consume_token(NEQ);
op = "!=";
      break;
      }
    default:
      jj_la1[16] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return op;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Relational() throws ParseException {Exp e1, e2; String op;
    e1 = Additive();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LT:
    case GT:
    case LEQ:
    case GEQ:{
      op = RelationalOpHelper();
      e2 = Additive();
e1 = CompareOp(op, e1, e2);
      break;
      }
    default:
      jj_la1[17] = jj_gen;
      ;
    }
{if ("" != null) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public String RelationalOpHelper() throws ParseException {String op;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LT:{
      jj_consume_token(LT);
op = "<";
      break;
      }
    case GT:{
      jj_consume_token(GT);
op = ">";
      break;
      }
    case LEQ:{
      jj_consume_token(LEQ);
op = "<=";
      break;
      }
    case GEQ:{
      jj_consume_token(GEQ);
op = ">=";
      break;
      }
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return op;}
    throw new Error("Missing return statement in function");
  }

// Helper method to get the right operator object
  final public Exp CompareOp(String op, Exp e1, Exp e2) throws ParseException {Exp e;
switch(op) {
            case "==": e = new Equals(e1, e2);
                break;
            case "!=": e = new EqualsNot(e1, e2);
                break;
            case "<": e = new LessThan(e1, e2);
                break;
            case ">": e = new GreaterThan(e1, e2);
                break;
            case "<=": e = new LessThanEquals(e1, e2);
                break;
            case ">=": e = new GreaterThanEquals(e1, e2);
                break;
            default: e = null;
                break;
        }
{if ("" != null) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Additive() throws ParseException {Exp e1, e2; String op;
    e1 = Times();
    label_14:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PLUS:
      case MINUS:{
        ;
        break;
        }
      default:
        jj_la1[19] = jj_gen;
        break label_14;
      }
      op = AdditiveOpHelper();
      e2 = Times();
e1 = AdditiveOp(op, e1, e2);
    }
{if ("" != null) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public String AdditiveOpHelper() throws ParseException {String op;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PLUS:{
      jj_consume_token(PLUS);
op = "+";
      break;
      }
    case MINUS:{
      jj_consume_token(MINUS);
op = "-";
      break;
      }
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return op;}
    throw new Error("Missing return statement in function");
  }

  final public Exp AdditiveOp(String op, Exp e1, Exp e2) throws ParseException {Exp e;
switch(op) {
            case "+": e = new Plus(e1, e2);
                break;
            case "-": e = new Minus(e1, e2);
                break;
            default: e = null;
                break;
        }
{if ("" != null) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Times() throws ParseException {Exp e1, e2;
    e1 = PrefixExp();
    label_15:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case STAR:{
        ;
        break;
        }
      default:
        jj_la1[21] = jj_gen;
        break label_15;
      }
      jj_consume_token(STAR);
      e2 = PrefixExp();
e1 = new Times(e1, e2);
    }
{if ("" != null) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp PrefixExp() throws ParseException {Exp e;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case BANG:{
      e = Not();
      break;
      }
    case TRUE:
    case FALSE:
    case THIS:
    case NEW:
    case LPAREN:
    case IDENTIFIER:
    case INTEGER_LITERAL:
    case LONG_LITERAL:{
      e = PostfixExp();
      break;
      }
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Not() throws ParseException {Exp e; int nots = 0;
    label_16:
    while (true) {
      jj_consume_token(BANG);
nots++;
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case BANG:{
        ;
        break;
        }
      default:
        jj_la1[23] = jj_gen;
        break label_16;
      }
    }
    e = PostfixExp();
if(nots % 2 != 0) e = new Not(e);
{if ("" != null) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Exp PostfixExp() throws ParseException {Exp e, ie; Identifier i; ExpList el;
    e = PrimaryExp();
    label_17:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LBRACKET:
      case DOT:{
        ;
        break;
        }
      default:
        jj_la1[24] = jj_gen;
        break label_17;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LBRACKET:{
        jj_consume_token(LBRACKET);
        ie = Exp();
        jj_consume_token(RBRACKET);
e = new ArrayLookup(e, ie);
        break;
        }
      default:
        jj_la1[25] = jj_gen;
        if (jj_2_7(2)) {
          jj_consume_token(DOT);
          i = Identifier();
          jj_consume_token(LPAREN);
          el = ExpList();
          jj_consume_token(RPAREN);
e = new Call(e, i, el);
        } else if (jj_2_8(2)) {
          jj_consume_token(DOT);
          jj_consume_token(LENGTH);
e = new ArrayLength(e);
        } else {
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
{if ("" != null) return e;}
    throw new Error("Missing return statement in function");
  }

// Binds the tightest
  final public Exp PrimaryExp() throws ParseException {Exp e, ie, ep; Identifier i, id;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INTEGER_LITERAL:{
      e = IntegerLiteral();
      break;
      }
    case LONG_LITERAL:{
      e = LongLiteral();
      break;
      }
    case TRUE:{
      jj_consume_token(TRUE);
e = new True();
      break;
      }
    case FALSE:{
      jj_consume_token(FALSE);
e = new False();
      break;
      }
    case IDENTIFIER:{
      i = Identifier();
e = new IdentifierExp(i.s);
      break;
      }
    case THIS:{
      jj_consume_token(THIS);
e = new This();
      break;
      }
    case LPAREN:{
      jj_consume_token(LPAREN);
      e = Exp();
      jj_consume_token(RPAREN);
      break;
      }
    default:
      jj_la1[26] = jj_gen;
      if (jj_2_9(2)) {
        jj_consume_token(NEW);
        jj_consume_token(INT);
        jj_consume_token(LBRACKET);
        ie = Exp();
        jj_consume_token(RBRACKET);
        NewArrayRest(ie);
e = new NewArray(ie);
      } else if (jj_2_10(2)) {
        jj_consume_token(NEW);
        jj_consume_token(LONG);
        jj_consume_token(LBRACKET);
        ie = Exp();
        jj_consume_token(RBRACKET);
        NewLongArrayRest(ie);
e = new NewLongArray(ie);
      } else if (jj_2_11(2)) {
        jj_consume_token(NEW);
        id = Identifier();
        jj_consume_token(LPAREN);
        jj_consume_token(RPAREN);
e = new NewObject(id);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return e;}
    throw new Error("Missing return statement in function");
  }

// To avoid multidimensional array creation
  final public Exp NewArrayRest(Exp ie) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LBRACKET:{
      jj_consume_token(LBRACKET);
{if (true) throw new ParseException("Multidimensional array creation");}
      break;
      }
    default:
      jj_la1[27] = jj_gen;
{if ("" != null) return new NewArray(ie);}
    }
    throw new Error("Missing return statement in function");
  }

// To avoid long multidimensional array creation
  final public Exp NewLongArrayRest(Exp ie) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LBRACKET:{
      jj_consume_token(LBRACKET);
{if (true) throw new ParseException("Multidimensional long array creation");}
      break;
      }
    default:
      jj_la1[28] = jj_gen;
{if ("" != null) return new NewLongArray(ie);}
    }
    throw new Error("Missing return statement in function");
  }

  final public ExpList ExpList() throws ParseException {Exp e1, e2; ExpList el = new ExpList();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case TRUE:
    case FALSE:
    case THIS:
    case NEW:
    case BANG:
    case LPAREN:
    case IDENTIFIER:
    case INTEGER_LITERAL:
    case LONG_LITERAL:{
      e1 = Exp();
el.addElement(e1);
      label_18:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[29] = jj_gen;
          break label_18;
        }
        e2 = ExpRest();
el.addElement(e2);
      }
      break;
      }
    default:
      jj_la1[30] = jj_gen;

    }
{if ("" != null) return el;}
    throw new Error("Missing return statement in function");
  }

  final public Exp ExpRest() throws ParseException {Exp e;
    jj_consume_token(COMMA);
    e = Exp();
{if ("" != null) return e;}
    throw new Error("Missing return statement in function");
  }

// To be able to extract the identifier value
  final public Identifier Identifier() throws ParseException {Token i;
    i = jj_consume_token(IDENTIFIER);
{if ("" != null) return new Identifier(i.toString());}
    throw new Error("Missing return statement in function");
  }

// To be able to extract the int value
  final public IntegerLiteral IntegerLiteral() throws ParseException {Token il;
    il = jj_consume_token(INTEGER_LITERAL);
{if ("" != null) return new IntegerLiteral(Integer.parseInt(il.toString()));}
    throw new Error("Missing return statement in function");
  }

// To be able to extract the long value
  final public LongLiteral LongLiteral() throws ParseException {Token il; String str;
    il = jj_consume_token(LONG_LITERAL);
str = il.toString();
        {if ("" != null) return new LongLiteral(Long.parseLong(str.substring(0, str.length()-1)));}
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_2_3(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  private boolean jj_2_4(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  private boolean jj_2_5(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_5(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(4, xla); }
  }

  private boolean jj_2_6(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_6(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(5, xla); }
  }

  private boolean jj_2_7(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_7(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(6, xla); }
  }

  private boolean jj_2_8(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_8(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(7, xla); }
  }

  private boolean jj_2_9(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_9(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(8, xla); }
  }

  private boolean jj_2_10(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_10(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(9, xla); }
  }

  private boolean jj_2_11(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_11(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(10, xla); }
  }

  private boolean jj_3_1()
 {
    if (jj_3R_19()) return true;
    return false;
  }

  private boolean jj_3_11()
 {
    if (jj_scan_token(NEW)) return true;
    if (jj_3R_20()) return true;
    return false;
  }

  private boolean jj_3_10()
 {
    if (jj_scan_token(NEW)) return true;
    if (jj_scan_token(LONG)) return true;
    return false;
  }

  private boolean jj_3_9()
 {
    if (jj_scan_token(NEW)) return true;
    if (jj_scan_token(INT)) return true;
    return false;
  }

  private boolean jj_3R_19()
 {
    if (jj_3R_21()) return true;
    if (jj_3R_20()) return true;
    return false;
  }

  private boolean jj_3R_25()
 {
    if (jj_3R_20()) return true;
    return false;
  }

  private boolean jj_3R_24()
 {
    if (jj_scan_token(LONG)) return true;
    return false;
  }

  private boolean jj_3R_23()
 {
    if (jj_scan_token(INT)) return true;
    return false;
  }

  private boolean jj_3R_20()
 {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3R_22()
 {
    if (jj_scan_token(BOOLEAN)) return true;
    return false;
  }

  private boolean jj_3_4()
 {
    if (jj_scan_token(LONG)) return true;
    if (jj_scan_token(LBRACKET)) return true;
    return false;
  }

  private boolean jj_3_3()
 {
    if (jj_scan_token(INT)) return true;
    if (jj_scan_token(LBRACKET)) return true;
    return false;
  }

  private boolean jj_3R_21()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_3()) {
    jj_scanpos = xsp;
    if (jj_3_4()) {
    jj_scanpos = xsp;
    if (jj_3R_22()) {
    jj_scanpos = xsp;
    if (jj_3R_23()) {
    jj_scanpos = xsp;
    if (jj_3R_24()) {
    jj_scanpos = xsp;
    if (jj_3R_25()) return true;
    }
    }
    }
    }
    }
    return false;
  }

  private boolean jj_3_8()
 {
    if (jj_scan_token(DOT)) return true;
    if (jj_scan_token(LENGTH)) return true;
    return false;
  }

  private boolean jj_3_7()
 {
    if (jj_scan_token(DOT)) return true;
    if (jj_3R_20()) return true;
    return false;
  }

  private boolean jj_3_6()
 {
    if (jj_3R_20()) return true;
    if (jj_scan_token(ASSIGN)) return true;
    return false;
  }

  private boolean jj_3_5()
 {
    if (jj_3R_19()) return true;
    return false;
  }

  private boolean jj_3_2()
 {
    if (jj_3R_19()) return true;
    return false;
  }

  /** Generated Token Manager. */
  public MiniJavaParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[31];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x400,0x680000,0x10000000,0x70000,0x800,0x680000,0x0,0x70000,0x70000,0x680000,0x100000,0x680000,0x0,0x0,0x20000000,0x0,0x0,0x40000000,0x40000000,0x80000000,0x80000000,0x0,0xf000000,0x0,0x0,0x0,0x7000000,0x0,0x0,0x0,0xf000000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x80400,0x0,0x80000,0x0,0x80400,0x20000,0x80000,0x80000,0x80400,0x0,0x400,0x80000,0x200,0x0,0x180,0x180,0x70,0x70,0x1,0x1,0x2,0x381004,0x4,0x44000,0x4000,0x381000,0x4000,0x4000,0x20000,0x381004,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[11];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public MiniJavaParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public MiniJavaParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new MiniJavaParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public MiniJavaParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new MiniJavaParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public MiniJavaParser(MiniJavaParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(MiniJavaParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  @SuppressWarnings("serial")
  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[54];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 31; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 54; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 11; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            case 3: jj_3_4(); break;
            case 4: jj_3_5(); break;
            case 5: jj_3_6(); break;
            case 6: jj_3_7(); break;
            case 7: jj_3_8(); break;
            case 8: jj_3_9(); break;
            case 9: jj_3_10(); break;
            case 10: jj_3_11(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
