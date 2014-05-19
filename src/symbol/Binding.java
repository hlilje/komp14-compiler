package symbol;

import syntaxtree.Type;

public class Binding {
    private Symbol s;
    private Type t;

    public Binding(Symbol s, Type t) {
        this.s = s;
        this.t = t;
    }

    public Symbol getSymbol() {
        return s;
    }

    public Type getType() {
        return t;
    }
}
