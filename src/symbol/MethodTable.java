package symbol;

import syntaxtree.*;

public class MethodTable {
    private Symbol s;
    private Type t;
    private Table formals;
    private Table locals;

    public MethodTable(Symbol s, Type t) {
        this.s = s;
        this.t = t;
        formals = new Table();
        locals = new Table();
    }

    public Symbol getId() {
        return s;
    }

    public boolean addVar(Symbol s, Type t) {
        if(locals.get(s) != null)
            return false;
        else {
            locals.put(s, new Binding(s, t));
            return true;
        }
    }

    public boolean addFormal(Symbol s, Type t) {
        if(formals.get(s) != null)
            return false;
        else {
            formals.put(s, new Binding(s, t));
            return true;
        }
    }

    public boolean inScope(Symbol s) {
        if(locals.get(s) == null) {
            if(formals.get(s) == null)
                return false;
        }
        return true;
    }

    public Binding getVar(Symbol s) {
        Binding b = (Binding)locals.get(s);
        if(b == null)
            return (Binding)formals.get(s);
        else
            return b;
    }
}
