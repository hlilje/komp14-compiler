package symbol;

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
        System.out.println("PUT: " + key.toString()); // DEBUG
        hashTable.insert(key.toString(), value);
        //prevtop = top;
        //top = key;

        stack.push(key);
    }

    public Object get(Symbol key) {
        //System.out.println("GET: " + key.toString()); // DEBUG
        return hashTable.lookup(key.toString());
    }

    // Push special marker onto stack
    public void beginScope() {
        System.out.println("====== BEGIN SCOPE ====== "); // DEBUG
        //prevtop = top;
        //top = Symbol.symbol(MARKER);

        stack.push(symbolMarker);
    }

    public void endScope() {
        System.out.println("======= END SCOPE ======="); // DEBUG
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
        //System.out.println("GET KEYS"); // DEBUG
        return Symbol.getKeys();
    }

    // Added method
    public boolean inScope(Symbol key) {
        java.util.ListIterator li = stack.listIterator(stack.size());
        Symbol s;
        while(li.hasPrevious()) { // Due to Iterator iterating the stack 'backwards'
            s = (Symbol)li.previous();
            if(s == key)
                return true;
            else if(s == symbolMarker) // Scope above already checked
                break;
        }
        return false;
    }

    // Added method
    // Special check to disallow name override in inner blocks
    // TODO Is a check of only the scope above sufficient?
    public boolean inOuterScope(Symbol key) {
        java.util.ListIterator li = stack.listIterator(stack.size());
        Symbol s;
        boolean outerScope = false;
        while(li.hasPrevious()) {
            s = (Symbol)li.previous();
            if(s == key) 
                return true;
            else if(s == symbolMarker) {
                if(outerScope)
                    break;
                else
                    outerScope = true;
            }
        }
        return false;
    }
}
