package symbol;

public class Table {
    private HashT hashTable;

    public Table() {
        hashTable = new HashT();
    }

    public void put(Symbol key, Object value) {
        System.out.println("PUT: " + key.toString()); // DEBUG
        hashTable.insert(key.toString(), value);
    }

    public Object get(Symbol key) {
        System.out.println("GET: " + key.toString()); // DEBUG
        return hashTable.lookup(key.toString());
    }

    // TODO Is this what is supposed to be returned?
    public java.util.Enumeration keys() {
        return Symbol.getKeys();
    }
}
