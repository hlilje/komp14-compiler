package symbol;

import syntaxtree.*;

public class ClassTable {
    private Symbol s;
    private Table locals;
    private Table methods;

    public ClassTable(Symbol s) {
        this.s = s;
        locals = new Table();
        methods = new Table();
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

    public boolean addMethod(Symbol s, MethodTable mt) {
        if(methods.get(s) != null)
            return false;
        else {
            methods.put(s, mt);
            return true;
        }
    }

    public MethodTable getMethod(Symbol s) {
        return (MethodTable)methods.get(s);
    }

    public Binding getVar(Symbol s) {
        return (Binding)locals.get(s);
    }

    public boolean hasVar(Symbol s) {
        return locals.get(s) != null;
    }
}
