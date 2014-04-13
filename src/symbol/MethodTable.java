package symbol;

import syntaxtree.*;
import frame.VMAccess;

public class MethodTable extends AbstractTable {
    private Symbol s;
    private Type t;
    private Table formals; // Method params
    private Table locals; // Local vars
    private BlockTable block; // Nested blocks
    private java.util.ArrayList<Binding> orderedFormals; // Formals in decl order
    private Table formalAccesses; // VMAccesses for formals
    private Table localAccesses; // VMAccesses for locals

    public MethodTable(Symbol s, Type t) {
        this.s = s;
        this.t = t;
        formals = new Table();
        locals = new Table();
        block = null;
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

    public boolean inScope(Symbol s) {
        if(locals.get(s) == null) {
            if(formals.get(s) == null)
                return false;
        }
        return true;
    }

    // Will be called in the outmost block if several are nested, provided
    // the 'block before' was set to a method table
    public Binding getVar(Symbol s) {
        Binding b = (Binding)locals.get(s);
        if(b == null)
            return (Binding)formals.get(s);
        else
            return b;
    }

    // Should be set to a MethodTable for the outmost block
    public void newBlock(AbstractTable at) {
        block = new BlockTable(at);
    }

    // To set the current block initially in type visitor
    public AbstractTable getBlock() {
        return block;
    }

    // Added for type checking
    public java.util.ArrayList<Binding> getOrderedFormals() {
        return orderedFormals;
    }

    // TODO Are these VMAccess methods needed?
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

    public VMAccess getLocalAccesses(Symbol s) {
        return (VMAccess)localAccesses.get(s);
    }
}
