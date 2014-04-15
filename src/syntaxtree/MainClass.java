package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class MainClass {
  public Identifier i1,i2,i3;
  public StatementList sl;
  public VarDeclList vl;

  // Added VarDeclList and changed Stmt to StmlList
  public MainClass(Identifier ai1, Identifier ai2, Identifier ai3, StatementList asl, VarDeclList avl) {
    i1=ai1; i2=ai2; i3=ai3; sl=asl; vl=avl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}

