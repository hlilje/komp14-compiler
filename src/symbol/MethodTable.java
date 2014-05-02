package symbol;

import syntaxtree.*;
import frame.VMAccess;

public class MethodTable {
    private Symbol s;
    private Type t;
    private Table formals; // Method params
    private Table locals; // Local vars
    private Table blocks; // All the outmost blocks in the method
    private java.util.ArrayList<Binding> orderedFormals; // Formals in decl order
    private Table formalAccesses; // VMAccesses for formals
    private Table localAccesses; // VMAccesses for locals

    public MethodTable(Symbol s, Type t) {
        this.s = s;
        this.t = t;
        formals = new Table();
        locals = new Table();
        blocks = new Table();
        orderedFormals = new java.util.ArrayList<Binding>();
        formalAccesses = new Table();
        localAccesses = new Table();
    }

    public Symbol getId() {
        return s;
    }

    public Type getType() {
        return t;
    }

    public boolean addVar(Symbol s, Type t) {
        if(locals.get(s) != null)
            return false;
        else {
            if(formals.get(s) != null) // Not allowed to override formals
                return false;
            else {
                locals.put(s, new Binding(s, t));
                return true;
            }
        }
    }

    // Should always be called before var decls
    public boolean addFormal(Symbol s, Type t) {
        if(formals.get(s) != null)
            return false;
        else {
            formals.put(s, new Binding(s, t));
            orderedFormals.add(new Binding(s, t)); // To keep track of formal order
            return true;
        }
    }

    // Helper method to check if the given variable is in scope
    public boolean inScope(Symbol s) {
        if(locals.get(s) == null) {
            if(formals.get(s) == null)
                return false;
        }
        return true;
    }

    // Get a variable from either the locals or formals
    public Binding getVar(Symbol s) {
        Binding b = (Binding)locals.get(s);
        if(b == null)
            return (Binding)formals.get(s);
        else
            return b;
    }

    // Insert a new outmost block
    public void putBlock(Symbol s, BlockTable bt) {
        blocks.put(s, bt);
    }

    // To get the outmost block from a given id
    public BlockTable getBlock(Symbol s) {
        return (BlockTable)blocks.get(s);
    }

    // Added for type checking
    public java.util.ArrayList<Binding> getOrderedFormals() {
        return orderedFormals;
    }

    // Save a VMAccess for a formal parameter
    public void addFormalAccess(Symbol s, VMAccess vma) {
        formalAccesses.put(s, vma);
    }

    // Save a VMAccess for a variable
    public void addLocalAccess(Symbol s, VMAccess vma) {
        localAccesses.put(s, vma);
    }

    public VMAccess getFormalAccess(Symbol s) {
        return (VMAccess)formalAccesses.get(s);
    }

    public VMAccess getLocalAccess(Symbol s) {
        return (VMAccess)localAccesses.get(s);
    }

    // Helper method to avoid having to check both locals and formals
    public VMAccess getAccess(Symbol s) {
        VMAccess vma = (VMAccess)localAccesses.get(s);
        if(vma == null)
            return (VMAccess)formalAccesses.get(s);
        return vma;
    }
}
