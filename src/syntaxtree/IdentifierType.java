package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class IdentifierType extends Type {
    public static final boolean DEBUG = false;

    public String s;

    public boolean equals(Type tp)
    {
        if(DEBUG) {
            System.out.println("  Check equals for IdentifierType");
            if(tp == null)
                System.out.println("    WAS NULL");
        }
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

    // Added method to get type name
    public String toString() {
        return s;
    }
}
