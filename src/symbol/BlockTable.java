package symbol;

import syntaxtree.*;
import frame.VMAccess;

public class BlockTable {
    public static final boolean DEBUG = false;

    private int id;
    private BlockTable bt;
    private Table locals;
    private Table localAccesses; // VMAccesses for locals

    public BlockTable(int id, BlockTable bt) {
        this.id = id;
        this.bt = bt;
        locals = new Table();
        localAccesses = new Table();
    }

    public int getId() {
        return id;
    }

    public boolean addVar(Symbol s, Type t) {
        if(DEBUG) System.out.println("  Add " + s + " to " + this);
        if(getVar(s) != null) {
            if(DEBUG) System.out.println("    Failed adding " + s + " to " + this);
            return false;
        } else {
            if(DEBUG) System.out.println("    Successfully added " + s + " to " + this);
            locals.put(s, new Binding(s, t));
            return true;
        }
    }

    // Returns null if not a local or no nested blocks
    public Binding getVar(Symbol s) {
        Binding b = (Binding)locals.get(s);
        if(DEBUG) {
            System.out.println("  Lookup " + s + " in " + this);
            if(b == null) System.out.println("    Result for " + s + " was null in " + this);
            if(bt == null) System.out.println("    Nested BT for " + s + " was null in " + this);
        }
        if(b == null && bt != null)
            return bt.getVar(s); // Up a scope level
        return b; // Not a nested block
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
        VMAccess vma = (VMAccess)localAccesses.get(s);
        if(vma == null && bt != null)
            return bt.getAccess(s); // Up a scope level
        return vma; // Not a nested block
    }
}
