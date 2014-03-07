package Symbol;

public class Table {
    private static final String MARKER = "_MARKER_";
    private HashT ht;
    private Symbol top, prevtop;

    public Table() {
        ht = new HashT();
        top = null;
        prevtop = null;
    }

    public void put(Symbol key, Object value) {
        ht.insert(key.toString(), value);

        prevtop = top;
        top = key;
    }

    public Object get(Symbol key) {
        return ht.lookup(key.toString());
    }

    // Push special marker onto stack
    public void beginScope() {
        prevtop = top;
        top = Symbol.symbol(MARKER);
    }

    public void endScope() {
        // TODO How should this actually work?
    }

    //public java.util.Enumeration keys() {
    //    return;
    //}
}
