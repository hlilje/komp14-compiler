package symbol;

import syntaxtree.*;

public class BlockTable extends AbstractTable {
    private Symbol s;
    private Type t;
    private AbstractTable bt;
    private Table locals;

    public BlockTable(AbstractTable bt) {
        this.bt = bt;
        locals = new Table();
    }

    public boolean addVar(Symbol s, Type t) {
        if(locals.get(s) != null)
            return false;
        else {
            locals.put(s, new Binding(s, t));
            return true;
        }
    }

    public Binding getVar(Symbol s) {
        Binding b = (Binding)locals.get(s);
        if(b == null)
            return bt.getVar(s);
        return b;
    }
}
