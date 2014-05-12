package symbol;

import syntaxtree.*;
import frame.VMAccess;
import frame.VMFrame;

public class ClassTable {
    private Symbol s;
    private Table locals;
    private Table methods;
    private Table fieldAccesses; // VMAccesses for fields
    private Table frames; // JVM Frames
    private SymbolTable symTable; // For inheritance
    private Symbol spr; // For inheritance

    public ClassTable(Symbol s, Symbol spr, SymbolTable st) {
        this.s = s;
        this.spr = spr;
        this.symTable = st;
        locals = new Table();
        methods = new Table();
        fieldAccesses = new Table();
        frames = new Table();
    }

    public Symbol getId() {
        return s;
    }

    public Symbol getSuperId() {
        return spr;
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
        MethodTable mt = (MethodTable)methods.get(s);
        if(mt == null && spr != null)
            mt = symTable.getClass(spr).getMethod(s); // inherited method
        return mt;
    }

    public Binding getVar(Symbol s) {
        return (Binding)locals.get(s);
    }

    public boolean hasVar(Symbol s) {
        return locals.get(s) != null;
    }

    // Save a VMAccess for a field
    public void addFieldAccess(Symbol s, VMAccess vma) {
        fieldAccesses.put(s, vma);
    }

    public VMAccess getFieldAccess(Symbol s) {
        return (VMAccess)fieldAccesses.get(s);
    }

    // Save a JVM frame
    public void addFrame(Symbol s, VMFrame f) {
        frames.put(s, f);
    }

    public VMFrame getFrame(Symbol s) {
        VMFrame frame = (VMFrame)frames.get(s);
        if(frame == null && spr != null)
            frame = symTable.getClass(spr).getFrame(s); // inherited method
        return frame;
    }
}
