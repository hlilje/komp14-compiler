/* MiniJavaParser.java */
/* Generated By:JavaCC: Do not edit this line. MiniJavaParser.java */
package parser;
import syntaxtree.*;
import visitor.*;

public class MiniJavaParser implements MiniJavaParserConstants {
    public MiniJavaParser() {}

/* GRAMMAR */
  final public Program Program() throws ParseException {MainClass mc; ClassDeclList cdl = new ClassDeclList(); ClassDecl cd;
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
{if ("" != null) return new Program(mc, cdl);}
    throw new Error("Missing return statement in function");
  }

// TODO Handle VarDecl?
  final public MainClass MainClass() throws ParseException {Identifier i1, i2; StatementList sl = new StatementList(); Statement s = null;
    jj_consume_token(CLASS);
    i1 = Identifier();
    jj_consume_token(LBRACE);
    jj_consume_token(PUBLIC);
    jj_consume_token(STATIC);
    jj_consume_token(VOID);
    jj_consume_token(IDENTIFIER);
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
      VarDecl();
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
{if ("" != null) return new MainClass(i1, i2, s);}
    throw new Error("Missing return statement in function");
  }

// ClassDeclExtends is an extension to the grammar
  final public ClassDecl ClassDecl() throws ParseException {Identifier i; VarDecl vd; VarDeclList vdl = new VarDeclList(); MethodDecl md;
    MethodDeclList mdl = new MethodDeclList();
    jj_consume_token(CLASS);
    i = Identifier();
    jj_consume_token(LBRACE);
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INT:
      case BOOLEAN:
      case IDENTIFIER:{
        ;
        break;
        }
      default:
        jj_la1[2] = jj_gen;
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
        jj_la1[3] = jj_gen;
        break label_5;
      }
      md = MethodDecl();
mdl.addElement(md);
    }
    jj_consume_token(RBRACE);
{if ("" != null) return new ClassDeclSimple(i, vdl, mdl);}
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
        jj_la1[4] = jj_gen;
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

// TODO Is this the correct creation of new Formals?
  final public FormalList FormalList() throws ParseException {Formal f; FormalList fl = new FormalList(); Type t; Identifier i;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:
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
          jj_la1[5] = jj_gen;
          break label_8;
        }
        f = FormalRest();
fl.addElement(f);
      }
      break;
      }
    default:
      jj_la1[6] = jj_gen;

    }
{if ("" != null) return fl;}
    throw new Error("Missing return statement in function");
  }

// TODO Is this the correct creation of new Formals?
  final public Formal FormalRest() throws ParseException {Type t; Identifier i;
    jj_consume_token(COMMA);
    t = Type();
    i = Identifier();
{if ("" != null) return new Formal(t, i);}
    throw new Error("Missing return statement in function");
  }

  final public Type Type() throws ParseException {Type t; Token tok;
    if (jj_2_3(2)) {
      jj_consume_token(INT);
      jj_consume_token(LBRACKET);
      jj_consume_token(RBRACKET);
t = new IntegerType();
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
      case IDENTIFIER:{
        tok = jj_consume_token(IDENTIFIER);
t  = new IdentifierType(tok.toString());
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
{if ("" != null) return t;}
    throw new Error("Missing return statement in function");
  }

  final public Statement Stmt() throws ParseException {StatementList sl = new StatementList(); Statement s, s1, s2;
    Exp e1, e2; Identifier i;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LBRACE:{
      jj_consume_token(LBRACE);
      label_9:
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
          jj_la1[8] = jj_gen;
          break label_9;
        }
        s1 = Stmt();
sl.addElement(s1);
      }
      jj_consume_token(RBRACE);
s = new Block(sl);
      break;
      }
    case IF:{
      jj_consume_token(IF);
      jj_consume_token(LPAREN);
      e1 = Exp();
      jj_consume_token(RPAREN);
      s1 = Stmt();
      jj_consume_token(ELSE);
      s2 = Stmt();
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
      jj_la1[9] = jj_gen;
      if (jj_2_4(2)) {
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
          jj_la1[10] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
{if ("" != null) return s;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Exp() throws ParseException {Exp e, ie, ep; Token i; Identifier id;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INTEGER_LITERAL:{
      e = IntegerLiteral();
      ep = ExpPrim(e);
      break;
      }
    case TRUE:{
      jj_consume_token(TRUE);
e = new True();
      ep = ExpPrim(e);
      break;
      }
    case FALSE:{
      jj_consume_token(FALSE);
e = new False();
      ep = ExpPrim(e);
      break;
      }
    case IDENTIFIER:{
      i = jj_consume_token(IDENTIFIER);
e = new IdentifierExp(i.toString());
      ep = ExpPrim(e);
      break;
      }
    case THIS:{
      jj_consume_token(THIS);
e = new This();
      ep = ExpPrim(e);
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      if (jj_2_5(2)) {
        jj_consume_token(NEW);
        jj_consume_token(INT);
        jj_consume_token(LBRACKET);
        ie = Exp();
        jj_consume_token(RBRACKET);
e = new NewArray(ie);
        ep = ExpPrim(e);
      } else {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case NEW:{
          jj_consume_token(NEW);
          id = Identifier();
          jj_consume_token(LPAREN);
          jj_consume_token(RPAREN);
e = new NewObject(id);
          ep = ExpPrim(e);
          break;
          }
        case BANG:{
          jj_consume_token(BANG);
          ie = Exp();
e = new Not(ie);
          ep = ExpPrim(e);
          break;
          }
        case LPAREN:{
          jj_consume_token(LPAREN);
          e = Exp();
          jj_consume_token(RPAREN);
          ep = ExpPrim(e);
          break;
          }
        default:
          jj_la1[12] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
{if ("" != null) return ep;}
    throw new Error("Missing return statement in function");
  }

// Eliminate left recursion
// ExpPrim has been removed
  final public Exp ExpPrim(Exp eb) throws ParseException {Exp e, ep, ea; Identifier i; ExpList el;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case AND:
    case LT:
    case PLUS:
    case MINUS:
    case STAR:
    case LEQ:
    case GT:
    case GEQ:
    case EQ:
    case NEQ:
    case OR:{
      ep = Op(eb);
      break;
      }
    case LBRACKET:{
      jj_consume_token(LBRACKET);
      ea = Exp();
      jj_consume_token(RBRACKET);
e = new ArrayLookup(eb, ea);
      ep = ExpPrim(e);
      break;
      }
    default:
      jj_la1[13] = jj_gen;
      if (jj_2_6(2)) {
        jj_consume_token(DOT);
        jj_consume_token(LENGTH);
e = new ArrayLength(eb);
        ep = ExpPrim(e);
      } else {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case DOT:{
          jj_consume_token(DOT);
          i = Identifier();
          jj_consume_token(LPAREN);
          el = ExpList();
          jj_consume_token(RPAREN);
e = new Call(eb, i, el);
          ep = ExpPrim(e);
          break;
          }
        default:
          jj_la1[14] = jj_gen;
ep = eb;
        }
      }
    }
{if ("" != null) return ep;}
    throw new Error("Missing return statement in function");
  }

// Exp moved here to get the right evaluation order
  final public Exp Op(Exp eb) throws ParseException {Exp e, ea;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case AND:{
      jj_consume_token(AND);
      ea = Exp();
e = new And(eb, ea);
      break;
      }
    case LT:{
      jj_consume_token(LT);
      ea = Exp();
e = new LessThan(eb, ea);
      break;
      }
    case PLUS:{
      jj_consume_token(PLUS);
      ea = Exp();
e = new Plus(eb, ea);
      break;
      }
    case MINUS:{
      jj_consume_token(MINUS);
      ea = Exp();
e = new Minus(eb, ea);
      break;
      }
    case STAR:{
      jj_consume_token(STAR);
      ea = Exp();
e = new Times(eb, ea);
      break;
      }
    case LEQ:{
      jj_consume_token(LEQ);
      ea = Exp();
e = new LessThanEquals(eb, ea);
      break;
      }
    case GT:{
      jj_consume_token(GT);
      ea = Exp();
e = new GreaterThan(eb, ea);
      break;
      }
    case GEQ:{
      jj_consume_token(GEQ);
      ea = Exp();
e = new GreaterThanEquals(eb, ea);
      break;
      }
    case EQ:{
      jj_consume_token(EQ);
      ea = Exp();
e = new Equals(eb, ea);
      break;
      }
    case NEQ:{
      jj_consume_token(NEQ);
      ea = Exp();
e = new EqualsNot(eb, ea);
      break;
      }
    case OR:{
      jj_consume_token(OR);
      ea = Exp();
e = new Or(eb, ea);
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return e;}
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
    case INTEGER_LITERAL:{
      e1 = Exp();
el.addElement(e1);
      label_10:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[16] = jj_gen;
          break label_10;
        }
        e2 = ExpRest();
el.addElement(e2);
      }
      break;
      }
    default:
      jj_la1[17] = jj_gen;

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

  private boolean jj_3_1()
 {
    if (jj_3R_11()) return true;
    return false;
  }

  private boolean jj_3_5()
 {
    if (jj_scan_token(NEW)) return true;
    if (jj_scan_token(INT)) return true;
    return false;
  }

  private boolean jj_3_6()
 {
    if (jj_scan_token(DOT)) return true;
    if (jj_scan_token(LENGTH)) return true;
    return false;
  }

  private boolean jj_3R_12()
 {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3R_11()
 {
    if (jj_3R_13()) return true;
    if (jj_3R_12()) return true;
    return false;
  }

  private boolean jj_3R_16()
 {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3R_15()
 {
    if (jj_scan_token(INT)) return true;
    return false;
  }

  private boolean jj_3R_14()
 {
    if (jj_scan_token(BOOLEAN)) return true;
    return false;
  }

  private boolean jj_3_3()
 {
    if (jj_scan_token(INT)) return true;
    if (jj_scan_token(LBRACKET)) return true;
    return false;
  }

  private boolean jj_3R_13()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_3()) {
    jj_scanpos = xsp;
    if (jj_3R_14()) {
    jj_scanpos = xsp;
    if (jj_3R_15()) {
    jj_scanpos = xsp;
    if (jj_3R_16()) return true;
    }
    }
    }
    return false;
  }

  private boolean jj_3_4()
 {
    if (jj_3R_12()) return true;
    if (jj_scan_token(ASSIGN)) return true;
    return false;
  }

  private boolean jj_3_2()
 {
    if (jj_3R_11()) return true;
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
  final private int[] jj_la1 = new int[18];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x100,0xd0000,0xc000,0x200,0xd0000,0x0,0xc000,0xc000,0xd0000,0xd0000,0x0,0xe00000,0x41000000,0x3e000000,0x0,0x3e000000,0x0,0x41e00000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x8040,0x8000,0x0,0x8040,0x2000,0x8000,0x8000,0x8040,0x40,0x8000,0x18000,0x100,0x43f,0x4000,0x3f,0x2000,0x18100,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[6];
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
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public MiniJavaParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new MiniJavaParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public MiniJavaParser(MiniJavaParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(MiniJavaParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 18; i++) jj_la1[i] = -1;
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
    boolean[] la1tokens = new boolean[49];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 18; i++) {
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
    for (int i = 0; i < 49; i++) {
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
    for (int i = 0; i < 6; i++) {
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
