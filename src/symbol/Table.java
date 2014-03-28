package symbol;

public class Table {
    public static final boolean DEBUG = false;

    private HashT hashTable;

    public Table() {
        hashTable = new HashT();
    }

    public void put(Symbol key, Object value) {
        if(DEBUG) System.out.println("    PUT: " + key.toString() + ", " + value.getClass()); // DEBUG
        hashTable.insert(key.toString(), value);
    }

    public Object get(Symbol key) {
        if(DEBUG) System.out.println("    GET: " + key.toString()); // DEBUG
        return hashTable.lookup(key.toString());
    }

    // TODO Is this what is supposed to be returned?
    public java.util.Enumeration keys() {
        return Symbol.getKeys();
    }
}
