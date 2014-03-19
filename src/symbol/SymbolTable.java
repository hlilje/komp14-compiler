package symbol;

public class SymbolTable {
    private Table table;

    public SymbolTable() {
        table = new Table();
    }

    public boolean addClass(Symbol s, ClassTable ct) {
        if(table.get(s) != null) {
            return false;
        } else {
            table.put(s, ct);
            return true;
        }
    }

    public ClassTable getClass(Symbol s) {
        return (ClassTable)table.get(s);
    }
}
