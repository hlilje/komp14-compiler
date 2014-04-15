package symbol;

import syntaxtree.*;
import frame.VMAccess;

public abstract class AbstractTable {
    public abstract Binding getVar(Symbol s);
    public abstract AbstractTable getBlock();
    public abstract VMAccess getAccess(Symbol s);
    // Added here to avoid having to cast later
    public abstract void addLocalAccess(Symbol s, VMAccess vma);
}
