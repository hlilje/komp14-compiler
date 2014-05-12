/**
 * Visitor for generating Jasmin code.
 */

package visitor;

import syntaxtree.*;
import error.*;
import symbol.*;
import jvm.*;
import jasmin.*;
import frame.VMAccess;
import frame.VMFrame;

public class JasminVisitor implements TypeVisitor {
    public static final boolean DEBUG = false;

    private ErrorHandler error;
    private JasminFileWriter jfw;

    private SymbolTable symTable;
    private ClassTable currClass;
    private MethodTable currMethod;
    private BlockTable currBlock;

    private int blockId; // Unique id for blocks
    private int branchId; // Unique id for branching
    private int stackDepth; // Keep track of current stack depth
    private int stackDepthMax; // Keep track of max stack depth
    private int localVars; // Keep track of number of local vars through blocks

    public JasminVisitor(ErrorHandler error, SymbolTable symTable, String tfp) {
        this.error = error;
        this.symTable = symTable;
        currClass = null;
        currMethod = null;
        currBlock = null;
        blockId = -1; // To give block #1 id 0
        branchId = -1; // To give branch block #1 id 0
        stackDepth = 0;
        stackDepthMax = 0;
        jfw = new JasminFileWriter(error, tfp);
    }

    // Helper method for incrementing stack depth
    private void incrStack() {
        stackDepth++;
        if (stackDepth > stackDepthMax)
            stackDepthMax = stackDepth;
    }

    // Helper method for decrementing stack depth
    // Don't need to compare to max here since it must've been incremented before
    private void decrStack() {
        if (stackDepth > 0)
            stackDepth--;
    }

    // Helper method for setting stack depth, only use for reseting stack depth
    private void setStackDepth(int i) {
        stackDepth = i;
        stackDepthMax = i;
    }

    // Helper method to search the scopes for the given string
    private VMAccess getVMAccess(String str) {
        Symbol s = Symbol.symbol(str);
        VMAccess access;

        if(currMethod == null)
            access = currClass.getFieldAccess(s);
        else if(currBlock == null) {
            access = currMethod.getAccess(s);
            if(access == null) access = currClass.getFieldAccess(s);
        } else {
            access = currBlock.getAccess(s);
            if(DEBUG) {
                System.out.println("  Searching for VMA " + s + " in block");
                if(access == null) System.out.println("  " + s + " was not found in block");
            }
            if(access == null) access = currMethod.getAccess(s);
            if(access == null) access = currClass.getFieldAccess(s);
        }

        if(access == null) {
            error.complain(str + " missing VMAccess",
                    ErrorHandler.ErrorCode.INTERNAL_ERROR);
        }
        return access;
    }

    // Returns the class name of the given variable's type
    private String getClassFromVar(String varName) {
        Symbol s = Symbol.symbol(varName);
        Binding binding;

        if(currMethod == null)
            binding = currClass.getVar(s);
        else if(currBlock == null) {
            binding = currMethod.getVar(s);
            if(binding == null) binding = currClass.getVar(s);
        } else {
            binding = currBlock.getVar(s);
            if(binding == null) binding = currMethod.getVar(s);
            if(binding == null) binding = currClass.getVar(s);
        }

        if(binding == null) {
            error.complain(varName + " missing variable",
                    ErrorHandler.ErrorCode.INTERNAL_ERROR);
        }

        // Use the Id Type to extract the class name
        return ((IdentifierType)binding.getType()).toString();
    }

    // Recursive helper function to get the class name from a call
    private String getClassFromCall(Call n) {
        String className = null;

        if(n.e instanceof NewObject) {
            className = ((NewObject)n.e).i.s;
        } else if(n.e instanceof IdentifierExp) {
            className = getClassFromVar(((IdentifierExp)n.e).s);
        } else if(n.e instanceof Call) {
            if(DEBUG) System.out.println("  Will call getClassFromCall recursively");
            String tempName = getClassFromCall((Call)n.e);
            ClassTable ct = symTable.getClass(Symbol.symbol(tempName));
            MethodTable mt = ct.getMethod(Symbol.symbol(((Call)n.e).i.s));
            className = ((IdentifierType)mt.getType()).toString();
        } else if(n.e instanceof This) {
            className = currClass.getId().toString();
        } else {
            error.complain("Invalid expression type of Call in JasminVisitor",
                    ErrorHandler.ErrorCode.INTERNAL_ERROR);
        }

        if(className == null) {
            error.complain("null className for call in method " + currMethod.getId() +
                    " in class " + currClass.getId(), ErrorHandler.ErrorCode.INTERNAL_ERROR);
        }

        return className;
    }

    // Helper method to extract type of given var symbol, returns null
    // if not found
    // Copied from TypeVisitor
    public Type getVarType(Symbol s) {
        Type t; Binding b;
        if(currMethod == null)
            b = currClass.getVar(s);
        else if(currBlock == null) {
            b = (Binding)currMethod.getVar(s);
            if(b == null) b = (Binding)currClass.getVar(s);
        } else { // Check block
            b = currBlock.getVar(s);
            if(b == null) {
                b = currMethod.getVar(s);
                if(b == null) b = currClass.getVar(s);
            } else b = currBlock.getVar(s);
        }

        return b != null ? b.getType() : null;
    }

    // MainClass m;
    // ClassDeclList cl;
    public Type visit(Program n) {
        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            n.cl.elementAt(i).accept(this);
        }

        return null;
    }

    // Identifier i1,i2;
    // Statement s;
    public Type visit(MainClass n) {
        if(DEBUG) System.out.println(">>>> MainClass");
        String className = n.i1.toString();
        currClass = symTable.getClass(Symbol.symbol(className));
        currMethod = currClass.getMethod(Symbol.symbol("main"));

        Record record = new Record(className);
        if(DEBUG) System.out.println(record.toString());
        Frame frame = new Frame("main", null, null);
        if(DEBUG) System.out.println(frame.toString());

        // Add frame to class table so blocks can get it
        currClass.addFrame(Symbol.symbol("main"), frame);

        // No inheritance
        jfw.declareClass(className, className, "java/lang/Object");
        jfw.declareMainMethod();

        setStackDepth(0); // Reset

        n.i1.accept(this);
        n.i2.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            VarDecl vd = n.vl.elementAt(i); String varName = vd.i.toString();
            VMAccess vma = frame.allocLocal(varName, vd.t);
            currMethod.addLocalAccess(Symbol.symbol(varName), vma);

            if(DEBUG) {
                if(vma instanceof IntegerInFrame)
                    System.out.println(((IntegerInFrame)vma).toString());
                else // instanceof ObjectInFrame
                    System.out.println(((ObjectInFrame)vma).toString());
            }
            vd.accept(this);
        }
        // Set number of local vars before visiting blocks
        localVars = n.vl.size();
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }
        jfw.setReturn(null);

        // Need at least one local for args
        // TODO: should be +1 like in regular methods?
        jfw.limitMethod(Math.max(localVars, 1), stackDepthMax);
        jfw.declareMethodEnd();
        jfw.createSourceFile(className);

        return null;
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclSimple n) {
        String className = n.i.s;
        if(DEBUG) System.out.println(">>>> ClassDeclSimple");
        currClass = symTable.getClass(Symbol.symbol(className));
        currMethod = null;

        Record record = new Record(className);
        if(DEBUG) System.out.println(record.toString());

        // No inheritance
        jfw.declareClass(className, className, "java/lang/Object");

        n.i.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            VarDecl vd = n.vl.elementAt(i); String fieldName = vd.i.toString();
            VMAccess vma = record.allocField(fieldName, vd.t);
            currClass.addFieldAccess(Symbol.symbol(fieldName), vma);

            if(DEBUG) System.out.println(((OnHeap)vma).toString());
            jfw.declareField(vma);
            vd.accept(this);
        }

        jfw.declareConstructor();

        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }

        jfw.createSourceFile(className);

        return new IdentifierType(n.i.s);
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclExtends n) {
        if(DEBUG) System.out.println(">>>> ClassDeclExtends");
        String className = n.i.s;
        currClass = symTable.getClass(Symbol.symbol(className));
        currMethod = null;

        Record record = new Record(className);
        if(DEBUG) System.out.println(record.toString());

        // No inheritance
        jfw.declareClass(className, className, "java/lang/Object");

        n.i.accept(this);
        n.j.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            VarDecl vd = n.vl.elementAt(i); String fieldName = vd.i.toString();
            VMAccess vma = record.allocField(fieldName, vd.t);
            currClass.addFieldAccess(Symbol.symbol(fieldName), vma);

            if(DEBUG) System.out.println(((OnHeap)vma).toString());
            jfw.declareField(vma);
            vd.accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }

        jfw.createSourceFile(className);

        return new IdentifierType(n.i.s);
    }

    // void t;
    // Identifier i;
    public Type visit(VarDecl n) {
        n.t.accept(this);
        n.i.accept(this);
        return n.t;
    }

    // void t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public Type visit(MethodDecl n) {
        if(DEBUG) System.out.println(">>>> MethodDecl");
        Symbol s = Symbol.symbol(n.i.s);
        currMethod = currClass.getMethod(s);
        currBlock = null; // Reset block scope

        Frame frame = (Frame)currClass.getFrame(s); // Created in DepthFirstVisitor
        if(DEBUG) System.out.println(frame.toString());
        if(frame == null) {
            error.complain("null frame for method " + s + " in class " + currClass.getId(),
                    ErrorHandler.ErrorCode.INTERNAL_ERROR);
        }
        jfw.declareMethod("public", frame);

        setStackDepth(0); // Reset

        n.t.accept(this);
        n.i.accept(this);

        // Alloc dummy formal just to increment frame variable offset by one
        // since the first local variable in a method call is the object the
        // method is being called on. TODO: maybe do this in a better way.
        frame.allocFormal("this", new IdentifierType(null));

        for ( int i = 0; i < n.fl.size(); i++ ) {
            Formal f = n.fl.elementAt(i); String formName = f.i.toString();
            VMAccess vma = frame.allocFormal(formName, f.t);
            currMethod.addFormalAccess(Symbol.symbol(formName), vma);
            f.accept(this);
        }
        for ( int i = 0; i < n.vl.size(); i++ ) {
            VarDecl vd = n.vl.elementAt(i); String varName = vd.i.toString();
            VMAccess vma = frame.allocLocal(varName, vd.t);
            currMethod.addLocalAccess(Symbol.symbol(varName), vma);

            if(DEBUG) {
                if(vma instanceof IntegerInFrame)
                    System.out.println(((IntegerInFrame)vma).toString());
                else // instanceof ObjectInFrame
                    System.out.println(((ObjectInFrame)vma).toString());
            }
            // TODO Don't think this is correct, should only stack size 1 for all vars
            // Need to fix jasmin code for programs with several classes first to test
            //incrStack();
            vd.accept(this);
        }
        // Set number of local vars before visiting blocks
        localVars = n.vl.size();
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        n.e.accept(this);
        jfw.setReturn(currMethod.getType());
        jfw.limitMethod(localVars + n.fl.size() + 1, stackDepthMax);
        jfw.declareMethodEnd();
        blockId = -1; // Reset the block counter for this method
        branchId = -1; // Reset branch id, will be inc before first assign

        return n.t;
    }

    // void t;
    // Identifier i;
    public Type visit(Formal n) {
        n.t.accept(this);
        n.i.accept(this);
        return n.t;
    }

    public Type visit(IntArrayType n) {
        return n;
    }

    public Type visit(BooleanType n) {
        return n;
    }

    public Type visit(IntegerType n) {
        return n;
    }

    // String s;
    public Type visit(IdentifierType n) {
        return n;
    }

    // StatementList sl;
    public Type visit(Block n) {
        if(DEBUG) System.out.println(">>>> Block");
        BlockTable prevBlock = currBlock;

        blockId++; // Keep track of blocks in method
        currBlock = currMethod.getBlock(Symbol.symbol(blockId + ""));

        // Get method frame, in order to use to the same local var list
        Frame frame = (Frame)currClass.getFrame(currMethod.getId());
        if(DEBUG) System.out.println(frame.toString());

        for ( int i = 0; i < n.vl.size(); i++ ) {
            VarDecl vd = n.vl.elementAt(i); String varName = vd.i.toString();
            VMAccess vma = frame.allocLocal(varName, vd.t);
            currBlock.addLocalAccess(Symbol.symbol(varName), vma);

            if(DEBUG) {
                if(vma instanceof IntegerInFrame)
                    System.out.println(((IntegerInFrame)vma).toString());
                else // instanceof ObjectInFrame
                    System.out.println(((ObjectInFrame)vma).toString());
                if(vma == null) {
                    System.out.println("  VMA was null for block id: " + blockId);
                }
            }
            vd.accept(this);
        }
        // Add block var decl to method local vars
        localVars += n.vl.size();
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        currBlock = prevBlock; // End scope
        return null;
    }

    // Exp e;
    // Statement s1,s2;
    public Type visit(If n) {
        branchId++;
        int thisBranchId = branchId; // Avoid id change by nested blocks

        n.e.accept(this);
        jfw.ifCheck(thisBranchId); // Check branch condition
        decrStack();

        n.s1.accept(this);
        jfw.skip(thisBranchId); // To avoid always executing 'else'

        jfw.setElse(thisBranchId); // 'Else' block
        n.s2.accept(this);
        jfw.setSkip(thisBranchId); // Skip here if not 'else'

        return null;
    }

    // Exp e;
    // Statement s;
    public Type visit(While n) {
        branchId++;
        int thisBranchId = branchId; // Avoid id change by nested blocks

        jfw.setSkip(thisBranchId);
        n.e.accept(this);
        jfw.ifCheck(thisBranchId); // Use 'if' branches for simplicity
        decrStack();

        n.s.accept(this);
        jfw.skip(thisBranchId); // Use 'skip' for looping for simplicity
        jfw.setElse(thisBranchId); // The 'else' will skip the while block

        return null;
    }

    // Exp e;
    public Type visit(Print n) {
        // 1 extra value (ref to print method) is added to stack, and 2 removed
        jfw.printInvoke();
        incrStack();
        Type t = n.e.accept(this);

        if(t instanceof IntegerType)
            jfw.printInt();
        else if(t instanceof BooleanType)
            jfw.printBoolean();
        else {
            error.complain("Print of unknown type in method " + currMethod.getId() +
                    " in class " + currClass.getId(), ErrorHandler.ErrorCode.INTERNAL_ERROR);
        }
        decrStack();
        decrStack();

        return null;
    }

    // Identifier i;
    // Exp e;
    public Type visit(Assign n) {
        n.i.accept(this);
        n.e.accept(this);
        VMAccess vma = getVMAccess(n.i.s);
        // If the variable to be assigned is a field, one extra value is put on the stack
        if(vma instanceof OnHeap)
            incrStack();
        jfw.storeAccess(vma);
        decrStack();
        // The extra stack value for fields is used
        if(vma instanceof OnHeap)
            decrStack();

        return null;
    }

    // Identifier i;
    // Exp e1,e2;
    public Type visit(ArrayAssign n) {
        n.i.accept(this);
        VMAccess vma = getVMAccess(n.i.s);
        jfw.loadAccess(vma);
        incrStack();

        n.e1.accept(this);
        n.e2.accept(this);
        jfw.storeArray();

        decrStack(); // For iastore
        decrStack();
        decrStack();

        return new IntArrayType();
    }

    // Exp e1,e2;
    public Type visit(And n) {
        branchId++;
        int thisBranchId = branchId;
        incrStack(); // Increase for dup
        n.e1.accept(this);
        jfw.dup(); // Duplicate since this is also needed for the 'and' check
        // Short-circuit
        jfw.ifCheck(thisBranchId); // Use 'if' check for simplicity
        n.e2.accept(this);

        jfw.and();
        jfw.setElse(thisBranchId); // Skip to 'else' if first exp was false
        decrStack(); // Result is pushed onto stack
        decrStack(); // For dup

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(LessThan n) {
        n.e1.accept(this);
        n.e2.accept(this);

        branchId++;
        jfw.lessThan(branchId);
        decrStack(); // Also loads a constant onto the stack

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(Plus n) {
        n.e1.accept(this);
        n.e2.accept(this);
        jfw.add();
        decrStack(); // Pop values and push result

        return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(Minus n) {
        n.e1.accept(this);
        n.e2.accept(this);
        jfw.minus();
        decrStack(); // Pop values and push result

        return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(Times n) {
        n.e1.accept(this);
        n.e2.accept(this);
        jfw.mul();
        decrStack(); // Pop values and push result

        return new IntegerType();
    }

    // Exp e1,e2;
    public Type visit(ArrayLookup n) {
        n.e1.accept(this);
        n.e2.accept(this);
        jfw.loadArray();
        decrStack();

        return new IntegerType();
    }

    // Exp e;
    public Type visit(ArrayLength n) {
        n.e.accept(this);
        jfw.arrayLength(); // Pop and push onto stack

        return new IntegerType();
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public Type visit(Call n) {
        if(DEBUG) System.out.println(">>>> Call: " + n.i.s);
        String className = getClassFromCall(n);
        Symbol s1 = Symbol.symbol(n.i.s); // Method name
        Exp e = n.e; Type t = null; Type t2; Symbol s2;
        ClassTable ct = null; MethodTable mt = null; // For null checks

        if(DEBUG) {
            System.out.println("  Try to get class " + className);
            ct = symTable.getClass(Symbol.symbol(className));
            if(ct == null) {
                System.out.println("    class was not found");
            } else if(ct.getFrame(Symbol.symbol(n.i.s)) == null) {
                System.out.println("    got no frame for " + n.i.s);
            }
        }

        Frame frame = (Frame)symTable.getClass(Symbol.symbol(className))
            .getFrame(Symbol.symbol(n.i.s));

        t2 = n.e.accept(this); // Save for Call on Call

        if(e instanceof NewObject) {
            s2 = Symbol.symbol(((NewObject)e).i.toString());
            ct = symTable.getClass(s2); mt = ct.getMethod(s1);
            t = mt.getType();
        } else if(e instanceof IdentifierExp) {
            IdentifierExp ie = (IdentifierExp)e;
            s2 = Symbol.symbol(getClassFromCall(n));
            ct = symTable.getClass(s2); mt = ct.getMethod(s1);
            t = mt.getType();
        } else if(e instanceof Call) {
            s2 = Symbol.symbol(((IdentifierType)t2).s);
            ct = symTable.getClass(s2);
            mt = ct.getMethod(s1); t = mt.getType();
        } else if(e instanceof This) {
            mt = currClass.getMethod(s1);
            t = mt.getType();
        } else {
            error.complain("Call on invalid object with method call in JasminVisitor",
                    ErrorHandler.ErrorCode.INTERNAL_ERROR);
        }

        n.i.accept(this);
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.elementAt(i).accept(this);
        }

        if(DEBUG && frame == null) {
            System.out.println("    Will call methodCall with null frame");
        }
        jfw.methodCall(className, frame);

        // Must be done outside of argument loop
        for(int i = 0; i < n.el.size(); i++) {
            decrStack(); // Once for each argument
        }

        return t;
    }

    // int i;
    public Type visit(IntegerLiteral n) {
        jfw.pushInt(n);
        incrStack();

        return new IntegerType();
    }

    public Type visit(True n) {
        jfw.pushTrue();
        incrStack();

        return new BooleanType();
    }

    public Type visit(False n) {
        jfw.pushFalse();
        incrStack();

        return new BooleanType();
    }

    // String s;
    public Type visit(IdentifierExp n) {
        Symbol s = Symbol.symbol(n.s);
        if(DEBUG) System.out.println(">>>> IdentifierExp: " + n.s);
        jfw.loadAccess(getVMAccess(n.s));
        incrStack(); // Increase for both heap and stack

        return getVarType(s);
    }

    public Type visit(This n) {
        jfw.loadThis();
        incrStack();

        return new IdentifierType(currClass.getId().toString());
    }

    // Exp e;
    public Type visit(NewArray n) {
        n.e.accept(this);
        jfw.newArray();
        // Nothing needs to be done here with stack

        return new IntArrayType();
    }

    // Identifier i;
    public Type visit(NewObject n) {
        jfw.newObject(n.i.s);
        incrStack();
        incrStack(); // For dup
        decrStack(); // Constructor returns void

        return new IdentifierType(n.i.s);
    }

    // Exp e;
    public Type visit(Not n) {
        if(DEBUG) System.out.println(">>>> Not");
        branchId++;
        int thisBranchId = branchId;
        n.e.accept(this);
        jfw.not(thisBranchId); // Push and pop

        return new BooleanType();
    }

    // String s;
    public Type visit(Identifier n) {
        Symbol s = Symbol.symbol(n.s);
        return getVarType(s);
    }

    // Exp e1,e2;
    public Type visit(LessThanEquals n) {
        n.e1.accept(this);
        n.e2.accept(this);

        branchId++;
        jfw.lessThanEquals(branchId);
        decrStack(); // Also loads a constant onto the stack

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(GreaterThan n) {
        n.e1.accept(this);
        n.e2.accept(this);

        branchId++;
        jfw.greaterThan(branchId);
        decrStack(); // Also loads a constant onto the stack

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(GreaterThanEquals n) {
        n.e1.accept(this);
        n.e2.accept(this);

        branchId++;
        jfw.greaterThanEquals(branchId);
        decrStack(); // Also loads a constant onto the stack

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(Equals n) {
        n.e1.accept(this);
        n.e2.accept(this);

        branchId++;
        jfw.equals(branchId);
        decrStack(); // Also loads a constant onto the stack

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(EqualsNot n) {
        n.e1.accept(this);
        n.e2.accept(this);

        branchId++;
        jfw.equalsNot(branchId);
        decrStack(); // Also loads a constant onto the stack

        return new BooleanType();
    }

    // Exp e1,e2;
    public Type visit(Or n) {
        branchId++;
        int thisBranchId = branchId;
        incrStack(); // Increase for dup
        n.e1.accept(this);
        jfw.dup(); // Duplicate since this is also needed for the 'or' check
        // Short circuit
        jfw.ifInvCheck(thisBranchId); // Skip if true (> 0)
        n.e2.accept(this);

        jfw.or();
        jfw.setElse(thisBranchId); // Skip here
        decrStack(); // Result is pushed onto stack
        decrStack(); // For dup

        return new BooleanType();
    }
}
