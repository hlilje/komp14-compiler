package symbol;

import syntaxtree.*;
import frame.VMAccess;

public class BlockTable extends AbstractTable {
    private AbstractTable bt;
    private Table locals;
    private Table localAccesses; // VMAccesses for locals

    public BlockTable(AbstractTable bt) {
        this.bt = bt;
        locals = new Table();
        localAccesses = new Table();
    }

    public boolean addVar(Symbol s, Type t) {
        if(getVar(s) != null)
            return false;
        else {
            locals.put(s, new Binding(s, t));
            return true;
        }
    }

    // Will continue checking back until bt is current method
    public Binding getVar(Symbol s) {
        Binding b = (Binding)locals.get(s);
        if(b == null)
            return bt.getVar(s);
        return b;
    }

    // To be able to traverse the nested blocks
    public AbstractTable getBlock() {
        return bt;
    }

    // Save a VMAccess for a local variable
    public void addLocalAccess(Symbol s, VMAccess vma) {
        localAccesses.put(s, vma);
    }

    public VMAccess getLocalAccess(Symbol s) {
        return (VMAccess)localAccesses.get(s);
    }
}
