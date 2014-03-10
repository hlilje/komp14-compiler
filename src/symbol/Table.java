package Symbol;

public class Table {
    private static final String MARKER = "_MARKER_";
    private Symbol symbolMarker;
    private HashT hashTable;
    //private Symbol top, prevtop;
    private java.util.Stack<Symbol> stack;

    public Table() {
        hashTable = new HashT();
        //top = null;
        //prevtop = null;

        stack = new java.util.Stack<Symbol>();
        symbolMarker = Symbol.symbol(MARKER);
    }

    public void put(Symbol key, Object value) {
        hashTable.insert(key.toString(), value);
        //prevtop = top;
        //top = key;

        stack.push(key);
    }

    public Object get(Symbol key) {
        return hashTable.lookup(key.toString());
    }

    // Push special marker onto stack
    public void beginScope() {
        //prevtop = top;
        //top = Symbol.symbol(MARKER);

        stack.push(symbolMarker);
    }

    public void endScope() {
        Symbol s;
        while(!stack.empty()) {
            s = stack.pop();
            if(s == symbolMarker)
                break;
            hashTable.pop(s.toString());
        }
    }

    // TODO Is this what is supposed to be returned?
    public java.util.Enumeration keys() {
        return Symbol.getKeys();
    }
}
