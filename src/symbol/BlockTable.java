package symbol;

import syntaxtree.*;
import frame.VMAccess;

public class BlockTable {
    private BlockTable bt;
    private Table locals;
    private Table localAccesses; // VMAccesses for locals

    public BlockTable(BlockTable bt) {
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

    // Returns null if not a local or no nested blocks
    public Binding getVar(Symbol s) {
        Binding b = (Binding)locals.get(s);
        if(b == null && bt != null)
            return bt.getVar(s); // Up a scope level
        return null; // Not a nested block
    }

    // To support nested blocks
    public BlockTable getBlock() {
        return bt;
    }

    // Save a VMAccess for a local variable
    public void addLocalAccess(Symbol s, VMAccess vma) {
        localAccesses.put(s, vma);
    }

    public VMAccess getAccess(Symbol s) {
        return (VMAccess)localAccesses.get(s);
    }
}
