package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class NewLongArray extends Exp {
    public Exp e;

    public NewLongArray(Exp ae) {
        e = ae; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}
