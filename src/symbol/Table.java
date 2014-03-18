package symbol;

import syntaxtree.*;

public class Table {
    //private static final String MARKER = "_MARKER_";
    //private Symbol symbolMarker;
    //private java.util.Stack<Symbol> stack;
    private HashT hashTable;
    private Table table;

    public Table(Table table) {
        hashTable = new HashT();
        this.table = table;

        //stack = new java.util.Stack<Symbol>();
        //symbolMarker = Symbol.symbol(MARKER);
    }

    public void put(Symbol key, Object value) {
        System.out.println("PUT: " + key.toString()); // DEBUG
        hashTable.insert(key.toString(), value);

        //stack.push(key);
    }

    // Added method to avoid method name collisions
    // Creates a unique method name based on id, type and params
    public void putMethod(MethodDecl md, Table t) {
        String uniqueName = md.i + ":" + md.t;
        FormalList fl = md.fl;
        for(int i=0; i<fl.size(); i++) {
            uniqueName += ":" + fl.elementAt(i).t;
        }

        hashTable.insert(uniqueName, t);
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

        //stack.push(symbolMarker);
    }

    public void endScope() {
        System.out.println("======= END SCOPE ======="); // DEBUG
        //Symbol s;
        //while(!stack.empty()) {
        //    s = stack.pop();
        //    if(s == symbolMarker)
        //        break;
        //    hashTable.pop(s.toString()); // TODO Don't type check need this?
        //}
    }

    // TODO Is this what is supposed to be returned?
    public java.util.Enumeration keys() {
        //System.out.println("GET KEYS"); // DEBUG
        return Symbol.getKeys();
    }

    // Added method
    // If you are using variables outside of method you must declare it with 'this'
    public boolean inScope(Symbol key) {
        //java.util.ListIterator li = stack.listIterator(stack.size());
        //Symbol s;
        //while(li.hasPrevious()) { // Due to Iterator iterating the stack 'backwards'
        //    s = (Symbol)li.previous();
        //    if(s == key)
        //        return true;
        //    else if(s == symbolMarker) // Scope above already checked
        //        break;
        //}

        Table currTable = this;
        while(currTable != null) {
            if(currTable.get(key) != null) {
                return true;
            }
            currTable = currTable.table;
        }
        return false;
    }

    // Special case to handle the scope of 'blocks'
    public boolean inScopeAbove(Symbol key) {
        //java.util.ListIterator li = stack.listIterator(stack.size());
        //Symbol s;
        //boolean scopeAbove = false;
        //while(li.hasPrevious()) {
        //    s = (Symbol)li.previous();
        //    if(s == key)
        //        return true;
        //    else if(s == symbolMarker) {
        //        if(!scopeAbove)
        //            scopeAbove = true;
        //        else
        //            break;
        //    }
        //}
        return false;
    }

    // Added method
    // TODO Might not be needed?
    public boolean inFullScope(Symbol key) {
        //java.util.ListIterator li = stack.listIterator(stack.size());
        //Symbol s;
        //while(li.hasPrevious()) {
        //    s = (Symbol)li.previous();
        //    if(s == key) 
        //        return true;
        //}
        return false;
    }
}
