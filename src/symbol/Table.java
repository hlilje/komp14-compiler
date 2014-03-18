package symbol;

import syntaxtree.*;

public class Table {
    private HashT hashTable;
    private Table table;

    public Table(Table table) {
        hashTable = new HashT();
        this.table = table;
    }

    public void put(Symbol key, Object value) {
        System.out.println("PUT: " + key.toString()); // DEBUG
        hashTable.insert(key.toString(), value);
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
        System.out.println("GET: " + key.toString()); // DEBUG
        return hashTable.lookup(key.toString());
    }

    // TODO Is this what is supposed to be returned?
    public java.util.Enumeration keys() {
        return Symbol.getKeys();
    }

    // Added method
    // If you are using variables outside of method you must declare it with 'this'
    public boolean inScope(Symbol key) {
        Table currTable = this;
        while(currTable != null) {
            if(currTable.get(key) != null) {
                return true;
            }
            currTable = currTable.table;
        }
        return false;
    }
}
