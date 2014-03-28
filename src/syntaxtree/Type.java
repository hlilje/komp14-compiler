package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public abstract class Type {
    public  boolean equals(Type tp) {
        return getClass().equals(tp.getClass());
    }
    
    public abstract void accept(Visitor v);
    public abstract Type accept(TypeVisitor v);

    // Added method for error messages, discard package names
    public String toString() {
        String c = getClass().getName();
        String[] subStr = c.split("\\.");
        int l = subStr.length;
        return l > 0 ? subStr[l-1] : c;
    }
}
