package Symbol;

public class Symbol {
    public String toString();
    public static Symbol symbol(String s);
}

public class Table {
    public Table();
    public void put(Symbol key, Object value);
    public Object get(Symbol key);
    public void beginScope();
    public void endScope();
    public java.util.Enumeration keys();
}
