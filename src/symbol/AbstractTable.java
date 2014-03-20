package symbol;

import syntaxtree.*;

public abstract class AbstractTable {
    public abstract Binding getVar(Symbol s);
}
