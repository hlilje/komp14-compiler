package symbol;

public class Table {
    public static final boolean DEBUG = false;

    private HashT hashTable;

    public Table() {
        hashTable = new HashT();
    }

    public void put(Symbol key, Object value) {
        if(DEBUG) System.out.println("    PUT: " + key.toString() + ", " + value.getClass() + " in " + this);
        hashTable.insert(key.toString(), value);
    }

    public Object get(Symbol key) {
        if(DEBUG) System.out.println("    GET: " + key.toString() + " in " + this);
        return hashTable.lookup(key.toString());
    }

    public java.util.Enumeration keys() {
        return Symbol.getKeys();
    }
}
