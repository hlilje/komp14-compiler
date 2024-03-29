package syntaxtree;
import visitor.Visitor;
import visitor.TypeVisitor;

public class LongLiteral extends Exp {
    public long i;

    public LongLiteral(long ai) {
        i=ai;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}
