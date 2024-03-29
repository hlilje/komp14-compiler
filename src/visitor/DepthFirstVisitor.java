/**
 * Depth first visitor which builds an AST.
 */

package visitor;

import syntaxtree.*;
import symbol.*;
import error.ErrorHandler;
import jvm.Frame;
import jvm.Record;
import frame.VMAccess;

public class DepthFirstVisitor implements Visitor {
    public static final boolean DEBUG = false;

    private ErrorHandler error;
    private SymbolTable symTable;
    private ClassTable currClass;
    private MethodTable currMethod;
    private BlockTable currBlock;

    private boolean staticClass; // If current class is static
    private int blockId; // To give a unique id for the outmost blocks

    // Added constructor to inject error message and symtable
    public DepthFirstVisitor(ErrorHandler error, SymbolTable symTable) {
        this.error = error;
        this.symTable = symTable;
        currClass = null;
        currMethod = null;
        currBlock = null;
        staticClass = false;
        blockId = -1; // To give block #1 id 0
    }

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        if(DEBUG) System.out.println(">>> VISIT PROGRAM");
        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            n.cl.elementAt(i).accept(this);
        }
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {
        // Hard coded method name, actual name is ignored
        Symbol s = Symbol.symbol(n.i1.toString()); Symbol s2 = Symbol.symbol("main");
        staticClass = true;

        if(DEBUG) System.out.println(">>> VISIT MAIN_CLASS: " + s);
        if(DEBUG) System.out.println("=== BEGIN MAIN CLASS SCOPE ====");
        ClassTable ct = new ClassTable(s, null, symTable);

        if(!(n.i3.s.equals("main"))) {
            error.complain("Main method not defined, was named: " + n.i3,
                    ErrorHandler.ErrorCode.MISSING_MAIN);
        }

        if(!symTable.addClass(s, ct)) {
            error.complain("Class " + s + " is already defined (main class)",
                    ErrorHandler.ErrorCode.ALREADY_DEFINED);
        } else {
            currClass = ct;
            MethodTable mt = new MethodTable(s2, null); // null type
            currClass.addMethod(s2, mt);
            currMethod = mt;
        }

        n.i1.accept(this);
        n.i2.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        staticClass = false;
        if(DEBUG) System.out.println("==== END MAIN CLASS SCOPE =====");
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
        Symbol s = Symbol.symbol(n.i.s);
        if(DEBUG) System.out.println(">>> VISIT CLASS_DECL_SIMP: " + s);
        if(DEBUG) System.out.println("====== BEGIN CLASS SCOPE ====== ");
        ClassTable ct = new ClassTable(s, null, symTable);

        if(!symTable.addClass(s, ct)) {
            error.complain("Class " + s + " is already defined",
                    ErrorHandler.ErrorCode.ALREADY_DEFINED);
        } else {
            currClass = ct;
            currMethod = null;
        }

        Record record = new Record(n.i.s);
        if(DEBUG) System.out.println(record.toString());

        n.i.accept(this);

        for ( int i = 0; i < n.vl.size(); i++ ) {
            // Add field accesses here instead of in Jasmin visitor, since a class
            // using them might be declared before the class they belong to
            VarDecl vd = n.vl.elementAt(i); String fieldName = vd.i.toString();
            VMAccess vma = record.allocField(fieldName, vd.t);
            currClass.addFieldAccess(Symbol.symbol(fieldName), vma);

            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }
        if(DEBUG) System.out.println("======= END CLASS SCOPE =======");
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {
        Symbol s = Symbol.symbol(n.i.s);
        Symbol spr = Symbol.symbol(n.j.s);
        if(DEBUG) System.out.println(">>> VISIT CLASS_DECLEXT: " + s);
        if(DEBUG) System.out.println("====== BEGIN CLASS SCOPE ======");
        ClassTable ct = new ClassTable(s, spr, symTable);

        // Check that class doesn't inherit from itself
        if(spr == s) {
            error.complain("Class " + n.i + " is inheriting from itself",
                            ErrorHandler.ErrorCode.SELF_INHERITANCE);
            ct.removeSuper();
        }

        // Check for circular inheritance
        if(ct.extendsClass(s)) {
            error.complain("Class " + n.i + " is in a circular inheritance",
                            ErrorHandler.ErrorCode.CIRCULAR_INHERITANCE);
            ct.removeSuper();
        }

        if(!symTable.addClass(s, ct)) {
            error.complain("Class " + s + " is already defined",
                    ErrorHandler.ErrorCode.ALREADY_DEFINED);
        } else {
            currClass = ct;
            currMethod = null;
        }

        Record record = new Record(n.i.s);
        if(DEBUG) System.out.println(record.toString());

        n.i.accept(this);
        n.j.accept(this);

        for ( int i = 0; i < n.vl.size(); i++ ) {
            // Add field accesses here instead of in Jasmin visitor, since a class
            // using them might be declared before the class they belong to
            VarDecl vd = n.vl.elementAt(i); String fieldName = vd.i.toString();
            VMAccess vma = record.allocField(fieldName, vd.t);
            currClass.addFieldAccess(Symbol.symbol(fieldName), vma);

            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }
        if(DEBUG) System.out.println("======= END CLASS SCOPE =======");
    }

    // Type t;
    // Identifier i;
    public void visit(VarDecl n) {
        Symbol s = Symbol.symbol(n.i.s);
        if(DEBUG) System.out.println(">>> VISIT VAR_DECL: " + s);

        // We only need to check class decls here since they are visited first
        if(currMethod == null) {
            if(!currClass.addVar(s, n.t)) {
                error.complain("VarDecl " + s + " is already defined in class " + currClass.getId(),
                        ErrorHandler.ErrorCode.ALREADY_DEFINED);
            }
        } else if(currBlock == null) { // A decl in a method
            // Here we assume it's ok to override class decls in methods
            if(!currMethod.addVar(s, n.t)) {
                error.complain("VarDecl " + s + " is already defined in method " + currMethod.getId() +
                        " in class " + currClass.getId(), ErrorHandler.ErrorCode.ALREADY_DEFINED);
            }
        } else { // A decl in a block
            if(DEBUG) System.out.println("  Looking for " + s + " in a block");
            // Here we assume it's ok to override class decls in blocks
            if(currMethod.inScope(s)) { // Not allowed to override method decls in blocks
                error.complain("VarDecl " + s + " is already defined in method " + currMethod.getId() +
                        " in class " + currClass.getId() + ", not allowed to be overridden in block",
                        ErrorHandler.ErrorCode.ALREADY_DEFINED);
            } else { // Finally try the block
                if(DEBUG) System.out.println("  Try to add " + s + " to the block");
                if(!currBlock.addVar(s, n.t)) {
                    error.complain("  VarDecl " + s + " is already defined in block in method " +
                            currMethod.getId() + " in class " + currClass.getId(),
                            ErrorHandler.ErrorCode.ALREADY_DEFINED);
                }
            }
        }

        n.t.accept(this);
        n.i.accept(this);
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n) {
        Symbol s = Symbol.symbol(n.i.s);
        if(DEBUG) System.out.println(">>> VISIT METHOD_DECL: " + s);
        if(DEBUG) System.out.println("====== BEGIN METHOD SCOPE =====");
        MethodTable mt = new MethodTable(s, n.t);
        // Reset block scopes
        currBlock = null;

        if(!currClass.addMethod(s, mt)) {
            error.complain("Method " + s + " is already defined in class " + currClass.getId(),
                    ErrorHandler.ErrorCode.ALREADY_DEFINED);
        } else
            currMethod = mt;

        // Create this frame here since it may not be declared yet due to
        // depth first visit in JasminVisitior
        if(DEBUG) System.out.println("Adding frame " + s + " to class " + currClass.getId());
        Frame frame = new Frame(n.i.s, n.fl, n.t);
        currClass.addFrame(s, frame);

        n.i.accept(this);

        for ( int i = 0; i < n.fl.size(); i++ ) {
            n.fl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        n.t.accept(this);
        n.e.accept(this);
        blockId = -1; // Reset the block counter for this method
        if(DEBUG) System.out.println("======= END METHOD SCOPE ======");
    }

    // Type t;
    // Identifier i;
    public void visit(Formal n) {
        Symbol s = Symbol.symbol(n.i.s);
        if(DEBUG) System.out.println(">>> VISIT FORMAL: " + s);

        // Here we assume it's ok to override class decls in formal decls
        if(!currMethod.addFormal(s, n.t)) {
            error.complain("Formal " + s + " is already defined in " + currMethod.getId(),
                    ErrorHandler.ErrorCode.ALREADY_DEFINED);
        }

        n.t.accept(this);
        n.i.accept(this);
    }

    public void visit(IntArrayType n) {
    }

    public void visit(LongArrayType n) {
    }

    public void visit(BooleanType n) {
    }

    public void visit(IntegerType n) {
    }

    public void visit(LongType n) {
    }

    // String s;
    public void visit(IdentifierType n) {
    }

    // StatementList sl;
    public void visit(Block n) {
        if(DEBUG) System.out.println(">>> VISIT BLOCK ");
        if(DEBUG) System.out.println("====== BEGIN BLOCK SCOPE ======");
        BlockTable bt;
        BlockTable prevBlock = null;
        boolean wasOutmostBlock = false;
        // Keep track of blocks in method, increase before potential nested blocks
        blockId++;

        if(currBlock == null) { // Non-nested block in method
            wasOutmostBlock = true;
            bt = new BlockTable(blockId, null); // Not a nested block
            if(DEBUG) {
                System.out.println("  Set new outer block with id " + blockId);
                System.out.println("    This is " + bt);
            }
        } else {
            bt = new BlockTable(blockId, currBlock);
            if(DEBUG) {
                System.out.println("  Set new nested block with id " + blockId);
                System.out.println("    Parent is " + currBlock);
                System.out.println("    This is " + bt);
            }
            prevBlock = currBlock; // Save the old outer block
        }

        currBlock = bt; // Make this the outer block
        currMethod.putBlock(Symbol.symbol(blockId + ""), bt);

        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.elementAt(i).accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        if(wasOutmostBlock) {
            currBlock = null;
        }
        else {
            currBlock = prevBlock;
        }
        if(DEBUG) System.out.println("======= END BLOCK SCOPE =======");
    }

    // Exp e;
    // Statement s1,s2;
    public void visit(If n) {
        if(DEBUG) System.out.println(">>> VISIT IF");
        n.e.accept(this);
        if(DEBUG) System.out.println("  Entering 'if' block");
        n.s1.accept(this);

        // Might be missing the 'else' block
        if(n.s2 != null) {
            if(DEBUG) System.out.println("  Entering 'else' block");
            n.s2.accept(this);
        } else {
            if(DEBUG) System.out.println("  No 'else' block");
        }
    }

    // Exp e;
    // Statement s;
    public void visit(While n) {
        if(DEBUG) System.out.println(">>> VISIT WHILE");
        n.e.accept(this);
        n.s.accept(this);
    }

    // Exp e;
    public void visit(Print n) {
        if(DEBUG) System.out.println(">>> VISIT PRINT");
        n.e.accept(this);
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {
        n.i.accept(this);
        n.e.accept(this);
    }

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n) {
        n.i.accept(this);
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(And n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Or n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(LessThan n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(LessThanEquals n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(GreaterThan n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(GreaterThanEquals n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Equals n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(EqualsNot n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Plus n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Minus n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Times n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(ArrayLookup n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e;
    public void visit(ArrayLength n) {
        n.e.accept(this);
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public void visit(Call n) {
        if(DEBUG) System.out.println(">>> VISIT CALL: " + n.i.s);
        // Cannot check if method exists here since class/method may not have
        // been visited
        n.e.accept(this);
        n.i.accept(this);
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.elementAt(i).accept(this);
        }
    }

    // int i;
    public void visit(IntegerLiteral n) {
    }

    // long i;
    public void visit(LongLiteral n) {
    }

    public void visit(True n) {
    }

    public void visit(False n) {
    }

    // String s;
    public void visit(IdentifierExp n) {
    }

    public void visit(This n) {
        if(staticClass) {
            error.complain("Invalid 'this' call in static class " + currClass.getId(),
                    ErrorHandler.ErrorCode.STATIC_THIS);
        }
    }

    // Exp e;
    public void visit(NewArray n) {
        n.e.accept(this);
    }

    // Exp e;
    public void visit(NewLongArray n) {
        n.e.accept(this);
    }

    // Identifier i;
    public void visit(NewObject n) {
        // Cannot check if class exists here since class may not have
        // been visited
    }

    // Exp e;
    public void visit(Not n) {
        n.e.accept(this);
    }

    // String s;
    public void visit(Identifier n) {
    }
}
