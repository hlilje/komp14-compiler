package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class IdentifierType extends Type {
    public String s;
    
    public boolean equals(Type tp)
    {
        System.out.println("ID_TYPE: " + s + " called equals on : " + tp.getClass()); // DEBUG
	if (! (tp instanceof IdentifierType) ) return false;
	return ((IdentifierType)tp).s.equals(s);
    }
    
  public IdentifierType(String as) {
    s=as;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type accept(TypeVisitor v) {
    return v.visit(this);
  }
}
