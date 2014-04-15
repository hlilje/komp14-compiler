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
    public static final boolean DEBUG = true;

    private ErrorHandler error;
    private String filePath; // Where to generate Jasmin files
    private java.lang.StringBuilder sb; // The Jasmin string to write to file
    private int stackDepth; // Keep track of needed stack depth

    private SymbolTable symTable;
    private ClassTable currClass;
    private MethodTable currMethod;
    private AbstractTable currBlock;

    public JasminVisitor(ErrorHandler error, SymbolTable symTable, String tfp) {
        this.error = error;
        this.symTable = symTable;
        currClass = null;
        currMethod = null;
        currBlock = null;
        filePath = tfp;
        sb = new java.lang.StringBuilder();
        stackDepth = 0;
    }

    // Helper method to write a class declaration in Jasmin syntax
    private void jDeclareClass(String src, String clss, String spr) {
        // Declare Jasmin source file
        sb.append(".source "); sb.append(src); sb.append(".j");
        sb.append(System.getProperty("line.separator"));
        sb.append(".class "); sb.append(clss);
        sb.append(System.getProperty("line.separator"));
        sb.append(".super "); sb.append(spr);
        sb.append(System.getProperty("line.separator"));
    }

    // Wrapper method to declare a field in a Jasmin source file
    private void jDeclareField(VMAccess vma) {
        sb.append(vma.declare());
        sb.append(System.getProperty("line.separator"));
    }

    // Helper method to declare a method for a Jasmin source file
    // Doesn't close the method tag since it must happen after visit
    private void jDeclareMethod(String acs, VMFrame vmf) {
        sb.append(".method "); sb.append(acs); sb.append(" ");
        sb.append(vmf.procEntry()); // Name decl according to Jasmin spec
        sb.append(System.getProperty("line.separator"));
    }

    // Special method to handle the main method with Jasmin
    private void jDeclareMainMethod() {
        sb.append(".method public static main([Ljava/lang/String;)V");
        sb.append(System.getProperty("line.separator"));
    }

    // Helper method to end a Jasmin method declaration
    private void jDeclareMethodEnd() {
        sb.append("    return"); // TODO Return different values
        sb.append(System.getProperty("line.separator"));
        sb.append(".end method");
        sb.append(System.getProperty("line.separator"));
    }

    // Helper method to set which directives to use for the Jasmin method decl
    private void jLimitMethod(int stack, int locals) {
        sb.append(".limit stack "); sb.append(stack);
        sb.append(System.getProperty("line.separator"));
        sb.append(".limit locals "); sb.append(locals);
        sb.append(System.getProperty("line.separator"));
    }

    // Wrapper method to declare a local Jasmin variable in a method
    private void jDeclareLocal(VMAccess vma) {
        sb.append(vma.declare());
        sb.append(System.getProperty("line.separator"));
    }

    // Wrapper method to push an interger literal to the stack
    private void jPushInt(IntegerLiteral n) {
        sb.append("    bipush "); sb.append(n.i);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin add op
    private void jAdd() {
        sb.append("    iadd");
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin minus op
    private void jMinus() {
        sb.append("    ineg");
        sb.append(System.getProperty("line.separator"));
        jAdd(); // Add negated number
    }

    // Jasmin multiplication op
    private void jMul() {
        sb.append("    imul");
        sb.append(System.getProperty("line.separator"));
    }

    // Declare new Jasmin class
    // No inheritance
    private void jNewObject(String className) {
        sb.append("    new java/lang/Object/"); sb.append(className);
        sb.append(System.getProperty("line.separator"));
    }

    // Declare new Jasmin int array
    private void jNewArray() {
        sb.append("    newarray int");
        sb.append(System.getProperty("line.separator"));
    }

    // Call Java's print method with Jasmin
    private void jPrint() {
        sb.append("    getstatic java/lang/System/out Ljava/io/PrintStream;");
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method to finish the print call
    private void jPrintAfter() {
        sb.append("    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        sb.append(System.getProperty("line.separator"));
    }

    // Helper method which creates a Jasmin source file
    private void jCreateSourceFile(String className) {
        try {
            String fileName = filePath + java.io.File.separator + className + ".j";
            java.io.File f = new java.io.File(fileName);
            f.createNewFile(); // Create file in the same dir

            // Write the accumulated string to the file
            java.io.FileWriter fw = new java.io.FileWriter(f.getAbsoluteFile());
            java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();

            sb.setLength(0); // Reset the accumulated string
        } catch(java.io.IOException e) {
            error.complain("Error while creating Jasmin file for class " +
                    className + ":\n" + e, ErrorHandler.ErrorCode.INTERNAL_ERROR);
        }
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
        currBlock = null;

        Record record = new Record(className);
        if(DEBUG) System.out.println(record.toString());
        Frame frame = new Frame("main", null, null); // Hard coded main method
        if(DEBUG) System.out.println(frame.toString());

        // No inheritance
        jDeclareClass(className, className, "java/lang/Object");
        jDeclareMainMethod();
        jLimitMethod(stackDepth, n.vl.size()); // TODO Calculate local stack size

        n.i1.accept(this);
        n.i2.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            VarDecl vd = n.vl.elementAt(i);
            String varName = vd.i.toString();
            VMAccess vma = frame.allocLocal(varName, vd.t);

            if(DEBUG) {
                if(vma instanceof IntegerInFrame)
                    System.out.println(((IntegerInFrame)vma).toString());
                else // instanceof ObjectInFrame
                    System.out.println(((ObjectInFrame)vma).toString());
            }
            jDeclareLocal(vma);
            vd.accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        jDeclareMethodEnd();
        jCreateSourceFile(className);
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
        String className = n.i.toString();
        if(DEBUG) System.out.println(">>>> ClassDeclSimple");
        currClass = symTable.getClass(Symbol.symbol(className));
        currMethod = null;
        currBlock = null;

        Record record = new Record(className);
        if(DEBUG) System.out.println(record.toString());

        // No inheritance
        jDeclareClass(className, className, "java/lang/Object");

        n.i.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            VarDecl vd = n.vl.elementAt(i);
            String fieldName = vd.i.toString();
            VMAccess vma = record.allocField(vd.i.toString(), vd.t);

            if(DEBUG) System.out.println(((OnHeap)vma).toString());
            jDeclareField(vma);
            vd.accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }

        jCreateSourceFile(className);
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {
        if(DEBUG) System.out.println(">>>> ClassDeclExtends");
        String className = n.i.toString();
        currClass = symTable.getClass(Symbol.symbol(className));
        currMethod = null;
        currBlock = null;

        Record record = new Record(className);
        if(DEBUG) System.out.println(record.toString());

        // No inheritance
        jDeclareClass(className, className, "java/lang/Object");

        n.i.accept(this);
        n.j.accept(this);
        for ( int i = 0; i < n.vl.size(); i++ ) {
            VarDecl vd = n.vl.elementAt(i);
            String fieldName = vd.i.toString();
            VMAccess vma = record.allocField(vd.i.toString(), vd.t);

            if(DEBUG) System.out.println(((OnHeap)vma).toString());
            jDeclareField(vma);
            vd.accept(this);
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.elementAt(i).accept(this);
        }

        jCreateSourceFile(className);
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
        currMethod = currClass.getMethod(Symbol.symbol(n.i.toString()));
        currBlock = null;

        Frame frame = new Frame("main", n.fl, currMethod.getType());
        if(DEBUG) System.out.println(frame.toString());
        jDeclareMethod("public", frame);
        jLimitMethod(0, n.vl.size()); // TODO Calculate local stack size

        n.t.accept(this);
        n.i.accept(this);
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
            jDeclareLocal(vma);
            vd.accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }

        n.e.accept(this);
        jDeclareMethodEnd(); // Close the declaration
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
        if(currBlock == null) {
            currBlock = currMethod.getBlock();
        } else {
            currBlock = currBlock.getBlock();
        }

        // TODO Handle VarDecl in Blocks
        Frame frame = new Frame(currMethod.getId().toString(), null, null);
        if(DEBUG) System.out.println(frame.toString());

        for ( int i = 0; i < n.vl.size(); i++ ) {
            VarDecl vd = n.vl.elementAt(i);
            VMAccess vma = frame.allocLocal(vd.i.toString(), vd.t);
            if(DEBUG) {
                if(vma instanceof IntegerInFrame)
                    System.out.println(((IntegerInFrame)vma).toString());
                else // instanceof ObjectInFrame
                    System.out.println(((ObjectInFrame)vma).toString());
            }
            jDeclareLocal(vma); // TODO This might need to be handled differently
            vd.accept(this);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.elementAt(i).accept(this);
        }
        currBlock = null;
    }

    // Exp e;
    // Statement s1,s2;
    public void visit(If n) {
        n.e.accept(this);
        n.s1.accept(this);
        n.s2.accept(this);
    }

    // Exp e;
    // Statement s;
    public void visit(While n) {
        n.e.accept(this);
        n.s.accept(this);
    }

    // Exp e;
    public void visit(Print n) {
        jPrint();
        n.e.accept(this);
        jPrintAfter();
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
    public void visit(LessThan n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Plus n) {
        jAdd();
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Minus n) {
        jMinus();
        n.e1.accept(this);
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Times n) {
        jMul();
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
        n.e.accept(this);
        n.i.accept(this);

        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.elementAt(i).accept(this);
        }
    }

    // int i;
    public void visit(IntegerLiteral n) {
        jPushInt(n);
        stackDepth++;
    }

    public void visit(True n) {
    }

    public void visit(False n) {
    }

    // String s;
    public void visit(IdentifierExp n) {
    }

    public void visit(This n) {
    }

    // Exp e;
    public void visit(NewArray n) {
        jNewArray();
        n.e.accept(this);
    }

    // Identifier i;
    public void visit(NewObject n) {
        jNewObject(n.i.s);
    }

    // Exp e;
    public void visit(Not n) {
        n.e.accept(this);
    }

    // String s;
    public void visit(Identifier n) {
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
    public void visit(Or n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }
}
