/**
 * Visitor for generating Jasmin code.
 */

package visitor;

import syntaxtree.*;
import error.*;
import symbol.*;
import jvm.*;
import frame.VMAccess;
import frame.VMFrame;

public class JasminVisitor implements Visitor {
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
            // TODO Might recursively become current class which is wrong
            //className = getClassFromCall((Call)n.e);
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

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            n.cl.elementAt(i).accept(this);
        }
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {
        if(DEBUG) System.out.println(">>>> MainClass");
        String className = n.i1.toString();
        currClass = symTable.getClass(Symbol.symbol(className));
        currMethod = currClass.getMethod(Symbol.symbol("main"));

        Record record = new Record(className);
        if(DEBUG) System.out.println(record.toString());
        Frame frame = new Frame("main", null, null);
        if(DEBUG) System.out.println(frame.toString());

        // No inheritance
        jfw.declareClass(className, className, "java/lang/Object");
        jfw.declareMainMethod();
        // TODO: Seems to be needed
        setStackDepth(1);

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
            // TODO This is only for debugging
            //jfw.declareLocal(vma);
            vd.accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        // TODO Calculate needed stack depth
        jfw.setReturn(null);
        jfw.limitMethod(Math.max(n.vl.size(), 1), stackDepthMax); // Need at least one local for args
        jfw.declareMethodEnd();
        jfw.createSourceFile(className);
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
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
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {
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
    }

    // void t;
    // Identifier i;
    public void visit(VarDecl n) {
        n.t.accept(this);
        n.i.accept(this);
    }

    // void t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n) {
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

        // TODO: this stack depth should be correct
        setStackDepth(0);

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
            // TODO This is only for debugging
            //jfw.declareLocal(vma);
            // TODO Don't think this is correct, should only stack size 1 for all vars
            // Need to fix jasmin code for programs with several classes first to test
            //incrStack();
            vd.accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        n.e.accept(this);
        jfw.setReturn(currMethod.getType());
        jfw.limitMethod(n.vl.size() + n.fl.size() + 1, stackDepthMax);
        jfw.declareMethodEnd();
        blockId = -1; // Reset the block counter for this method
        branchId = -1; // Reset branch id, will be inc before first assign
    }

    // void t;
    // Identifier i;
    public void visit(Formal n) {
        n.t.accept(this);
        n.i.accept(this);
    }

    public void visit(IntArrayType n) {
    }

    public void visit(BooleanType n) {
    }

    public void visit(IntegerType n) {
    }

    // String s;
    public void visit(IdentifierType n) {
    }

    // StatementList sl;
    public void visit(Block n) {
        if(DEBUG) System.out.println(">>>> Block");
        BlockTable prevBlock = currBlock;

        blockId++; // Keep track of blocks in method
        currBlock = currMethod.getBlock(Symbol.symbol(blockId + ""));

        // TODO Handle VarDecl in Blocks
        Frame frame = new Frame(currMethod.getId().toString(), null, null);
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
            // TODO This is only for debugging
            //jfw.declareLocal(vma); // TODO This might need to be handled differently
            vd.accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        currBlock = prevBlock; // End scope
    }

    // Exp e;
    // Statement s1,s2;
    public void visit(If n) {
        branchId++;
        int thisBranchId = branchId; // Avoid id change by nested blocks

        n.e.accept(this);
        jfw.ifCheck(thisBranchId); // Check branch condition

        n.s1.accept(this);
        jfw.skip(thisBranchId); // To avoid always executing 'else'

        jfw.setElse(thisBranchId); // 'Else' block
        n.s2.accept(this);
        jfw.setSkip(thisBranchId); // Skip here if not 'else'
    }

    // Exp e;
    // Statement s;
    public void visit(While n) {
        branchId++;
        int thisBranchId = branchId; // Avoid id change by nested blocks

        jfw.setSkip(thisBranchId);
        n.e.accept(this);
        jfw.ifCheck(thisBranchId); // Use 'if' branches for simplicity

        n.s.accept(this);
        jfw.skip(thisBranchId); // Use 'skip' for looping for simplicity
        jfw.setElse(thisBranchId); // The 'else' will skip the while block
    }

    // Exp e;
    public void visit(Print n) {
        // 1 extra value (ref to print method) is added to stack, and 2 removed
        jfw.print();
        incrStack();
        n.e.accept(this);
        jfw.printAfter();
        decrStack();
        decrStack();
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {
        n.i.accept(this);
        n.e.accept(this);
        VMAccess vma = getVMAccess(n.i.s);
        // If the variable to be assigned is a field, one extra value is put on the stack
        if( vma instanceof OnHeap )
            incrStack();
        jfw.storeAccess(vma);
        decrStack();
        // The extra stack value for fields is used
        if( vma instanceof OnHeap)
            decrStack();
    }

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n) {
        n.i.accept(this);
        VMAccess vma = getVMAccess(n.i.s);
        jfw.loadAccess(vma);
        n.e1.accept(this);
        n.e2.accept(this);
        jfw.storeArray();
        // TODO: nothing is done to stack here, which is probably wrong
    }

    // Exp e1,e2;
    public void visit(And n) {
        n.e1.accept(this);
        n.e2.accept(this);

        jfw.and();
        decrStack(); // The result is pushed onto the op stack
    }

    // Exp e1,e2;
    public void visit(LessThan n) {
        n.e1.accept(this);
        n.e2.accept(this);

        branchId++;
        jfw.lessThan(branchId);
        decrStack(); // Also loads a constant onto the stack
    }

    // Exp e1,e2;
    public void visit(Plus n) {
        n.e1.accept(this);
        n.e2.accept(this);
        jfw.add();
        decrStack(); // Pop values and push result
    }

    // Exp e1,e2;
    public void visit(Minus n) {
        n.e1.accept(this);
        n.e2.accept(this);
        jfw.minus();
        decrStack(); // Pop values and push result
    }

    // Exp e1,e2;
    public void visit(Times n) {
        n.e1.accept(this);
        n.e2.accept(this);
        jfw.mul();
        decrStack(); // Pop values and push result
    }

    // Exp e1,e2;
    public void visit(ArrayLookup n) {
        n.e1.accept(this);
        n.e2.accept(this);
        jfw.loadArray();
    }

    // Exp e;
    public void visit(ArrayLength n) {
        n.e.accept(this);
        jfw.arrayLength(); // Pop and push onto stack
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public void visit(Call n) {
        if(DEBUG) System.out.println(">>>> Call: " + n.i.s);
        String className = getClassFromCall(n);

        if(DEBUG) {
            System.out.println("  Try to get class " + className);
            ClassTable ct = symTable.getClass(Symbol.symbol(className));
            if(ct == null) {
                System.out.println("    class was not found");
            } else if(ct.getFrame(Symbol.symbol(n.i.s)) == null) {
                System.out.println("    got no frame for " + n.i.s);
            }
        }

        Frame frame = (Frame)symTable.getClass(Symbol.symbol(className))
            .getFrame(Symbol.symbol(n.i.s));

        n.e.accept(this);
        n.i.accept(this);
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.elementAt(i).accept(this);
        }

        if(DEBUG && frame == null) {
            System.out.println("    Will call methodCall with null frame");
        }
        jfw.methodCall(className, frame);
    }

    // int i;
    public void visit(IntegerLiteral n) {
        jfw.pushInt(n);
        incrStack();
    }

    public void visit(True n) {
        jfw.pushTrue();
        incrStack();
    }

    public void visit(False n) {
        jfw.pushFalse();
        incrStack();
    }

    // String s;
    public void visit(IdentifierExp n) {
        if(DEBUG) System.out.println(">>>> IdentifierExp: " + n.s);
        jfw.loadAccess(getVMAccess(n.s));
        incrStack(); // TODO
    }

    public void visit(This n) {
        // TODO This might be done by calling a method somewhere
        jfw.loadThis();
        incrStack();
    }

    // Exp e;
    public void visit(NewArray n) {
        n.e.accept(this);
        jfw.newArray();
        incrStack(); // TODO
    }

    // Identifier i;
    public void visit(NewObject n) {
        jfw.newObject(n.i.s);
        incrStack(); // TODO
    }

    // Exp e;
    public void visit(Not n) {
        branchId++;
        int thisBranchId = branchId;
        n.e.accept(this);
        jfw.not(thisBranchId);
    }

    // String s;
    public void visit(Identifier n) {
    }

    // Exp e1,e2;
    public void visit(LessThanEquals n) {
        n.e1.accept(this);
        n.e2.accept(this);

        branchId++;
        jfw.lessThanEquals(branchId);
        decrStack(); // Also loads a constant onto the stack
    }

    // Exp e1,e2;
    public void visit(GreaterThan n) {
        n.e1.accept(this);
        n.e2.accept(this);

        branchId++;
        jfw.greaterThan(branchId);
        decrStack(); // Also loads a constant onto the stack
    }

    // Exp e1,e2;
    public void visit(GreaterThanEquals n) {
        n.e1.accept(this);
        n.e2.accept(this);

        branchId++;
        jfw.greaterThanEquals(branchId);
        decrStack(); // Also loads a constant onto the stack
    }

    // Exp e1,e2;
    public void visit(Equals n) {
        n.e1.accept(this);
        n.e2.accept(this);

        branchId++;
        jfw.equals(branchId);
        decrStack(); // Also loads a constant onto the stack
    }

    // Exp e1,e2;
    public void visit(EqualsNot n) {
        n.e1.accept(this);
        n.e2.accept(this);

        branchId++;
        jfw.equalsNot(branchId);
        decrStack(); // Also loads a constant onto the stack
    }

    // Exp e1,e2;
    public void visit(Or n) {
        n.e1.accept(this);
        n.e2.accept(this);

        jfw.or();
        decrStack(); // The result is pushed onto the op stack
    }
}
