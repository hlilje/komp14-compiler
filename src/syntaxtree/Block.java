package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class Block extends Statement {
  public StatementList sl;
  public VarDeclList vl;

  // Extended with var decls
  public Block(StatementList asl, VarDeclList avl) {
    sl=asl;
    vl=avl;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}

