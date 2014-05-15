/**
 * Generates Jasmin assembly code.
 */

package jasmin;

import syntaxtree.*;
import error.*;
import frame.VMAccess;
import frame.VMFrame;
import symbol.Binding;

public class JasminFileWriter {
    public static final boolean DEBUG = false;

    private ErrorHandler error;
    private java.lang.StringBuilder sb; // The Jasmin string to write to file

    private String filePath; // Where to generate Jasmin files

    public JasminFileWriter(ErrorHandler error, String filePath) {
        this.error = error;
        this.filePath = filePath;
        sb = new java.lang.StringBuilder();
    }

    // Helper method which creates a Jasmin source file
    public void createSourceFile(String className) {
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

    /* JASMIN DECLARATIONS */

    // Helper method to write a class declaration in Jasmin syntax
    public void declareClass(String src, String clss, String spr) {
        if(JasminReservedWords.reservedWord(clss))
            clss += "_";
        if(JasminReservedWords.reservedWord(spr))
            spr += "_";

        // Declare Jasmin source file
        sb.append(".source "); sb.append(src); sb.append(".j");
        sb.append(System.lineSeparator());
        sb.append(".class "); sb.append(clss);
        sb.append(System.lineSeparator());
        sb.append(".super "); sb.append(spr);
        sb.append(System.lineSeparator());
    }

    // Helper method to write a default constructor declaration in Jasmin syntax
    public void declareConstructor(String spr) {
        sb.append(".method public <init>()V");
        sb.append(System.lineSeparator());
        sb.append("    aload_0");
        sb.append(System.lineSeparator());
        sb.append("    invokespecial "); sb.append(spr); sb.append("/<init>()V");
        sb.append(System.lineSeparator());
        sb.append("    return");
        sb.append(System.lineSeparator());
        sb.append(".end method");
        sb.append(System.lineSeparator());
    }

    // Wrapper method to declare a field in a Jasmin source file
    public void declareField(VMAccess vma) {
        sb.append(vma.declare());
        sb.append(System.lineSeparator());
    }

    // Wrapper method to declare a local Jasmin variable in a method
    public void declareLocal(VMAccess vma) {
        if(DEBUG) {
            if(vma == null) System.out.println("  Try to declare a NULL vma");
        }
        sb.append(vma.declare());
        sb.append(System.lineSeparator());
    }

    // Helper method to declare a method for a Jasmin source file
    // Doesn't close the method tag since it must happen after visit
    public void declareMethod(String acs, VMFrame vmf) {
        if(JasminReservedWords.reservedWord(acs))
            acs += "_";
        sb.append(".method "); sb.append(acs); sb.append(" ");
        sb.append(vmf.procEntry()); // Name decl according to Jasmin spec
        sb.append(System.lineSeparator());
    }

    // Special method to handle the main method with Jasmin
    public void declareMainMethod() {
        sb.append(".method public static main([Ljava/lang/String;)V");
        sb.append(System.lineSeparator());
    }

    // Helper method to end a Jasmin method declaration
    public void declareMethodEnd() {
        sb.append(".end method");
        sb.append(System.lineSeparator());
    }

    // Helper method to set which directives to use for the Jasmin method decl
    public void limitMethod(int locals, int stackDepth) {
        sb.append("    .limit stack "); sb.append(stackDepth);
        sb.append(System.lineSeparator());
        sb.append("    .limit locals "); sb.append(locals);
        sb.append(System.lineSeparator());
    }

    /* JASMIN RETURNS */

    // Sets the return op based on given type
    public void setReturn(Type t) {
        if(t == null)
            sb.append("    return");
        else
            sb.append(returnWithType(t));
        sb.append(System.lineSeparator());
    }

    // Helper method to format the return type for Jasmin
    private String returnWithType(Type t) {
        if(t instanceof IntegerType)
            return "    ireturn";
        else if(t instanceof LongType)
            return "    lreturn";
        else if(t instanceof BooleanType)
            return "    ireturn"; // Boolean is int
        else if(t instanceof IdentifierType)
            return "    areturn";
        else if(t instanceof IntArrayType)
            return "    areturn"; // Array is object
        else if(t instanceof LongArrayType)
            return "    areturn"; // Array is object
        else {
            error.complain("Invalid return type in JasminFileWriter",
                    ErrorHandler.ErrorCode.INTERNAL_ERROR);
            return "";
        }
    }

    /* JASMIN CONSTANTS */

    // Jasmin method to push an interger literal onto the stack
    public void pushInt(IntegerLiteral n) {
        sb.append("    ldc "); sb.append(n.i);
        sb.append(System.lineSeparator());
    }

    // Jasmin method to push a long literal onto the stack
    public void pushLong(LongLiteral n) {
        sb.append("    ldc2_w "); sb.append(n.i);
        sb.append(System.lineSeparator());
    }

    // Jasmin method to push a 'true' literal onto the stack
    public void pushTrue() {
        sb.append("    ldc 1");
        sb.append(System.lineSeparator());
    }

    // Jasmin method to push a 'false' literal onto the stack
    public void pushFalse() {
        sb.append("    ldc 0");
        sb.append(System.lineSeparator());
    }

    /* JASMIN OPERATORS */

    // Jasmin add op
    public void add() {
        sb.append("    iadd");
        sb.append(System.lineSeparator());
    }

    // Jasmin add op (long)
    public void addLong() {
        sb.append("    ladd");
        sb.append(System.lineSeparator());
    }

    // Jasmin sub op
    public void sub() {
        sb.append("    ineg");
        sb.append(System.lineSeparator());
        add(); // Add negated number
    }

    // Jasmin sub op (long)
    public void subLong() {
        sb.append("    lneg");
        sb.append(System.lineSeparator());
        addLong(); // Add negated number
    }

    // Jasmin mult op
    public void mul() {
        sb.append("    imul");
        sb.append(System.lineSeparator());
    }

    // Jasmin mult op (long)
    public void mulLong() {
        sb.append("    lmul");
        sb.append(System.lineSeparator());
    }

    /* JASMIN OBJECT CREATION */

    // Declare new Jasmin class
    // No inheritance
    public void newObject(String className) {
        if(JasminReservedWords.reservedWord(className))
            className += "_";

        sb.append("    new "); sb.append(className);
        sb.append(System.lineSeparator());
        sb.append("    dup");
        sb.append(System.lineSeparator());
        sb.append("    invokespecial "); sb.append(className);
        sb.append("/<init>()V");
        sb.append(System.lineSeparator());
    }

    // Declare new Jasmin int array
    public void newArray() {
        sb.append("    newarray int");
        sb.append(System.lineSeparator());
    }

    // Declare new Jasmin long array
    public void newLongArray() {
        sb.append("    newarray long");
        sb.append(System.lineSeparator());
    }

    /* JASMIN PRINT */

    // Call Java's print method with Jasmin
    public void printInvoke() {
        sb.append("    getstatic java/lang/System/out Ljava/io/PrintStream;");
        sb.append(System.lineSeparator());
    }

    // Jasmin method to finish the print call for ints
    public void printInt() {
        sb.append("    invokevirtual java/io/PrintStream/println(I)V");
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method to finish the print call for longs
    public void printLong() {
        sb.append("    invokevirtual java/io/PrintStream/println(J)V");
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method to finish the print call for booleans
    public void printBoolean() {
        sb.append("    invokevirtual java/io/PrintStream/println(Z)V");
        sb.append(System.getProperty("line.separator"));
    }

    /* JASMIN LOADING / STORING */

    // Jasmin method to load the given VMAccess
    public void loadAccess(VMAccess vma) {
        if(DEBUG) {
            if(vma == null) System.out.println("  Try to load a NULL vma");
        }
        sb.append(vma.load());
        sb.append(System.lineSeparator());
    }

    // Jasmin method to store at the given VMAccess
    public void storeAccess(VMAccess vma) {
        if(DEBUG) {
            if(vma == null) System.out.println("  Try to store a NULL vma");
        }
        sb.append(vma.store());
        sb.append(System.lineSeparator());
    }

    // Jasmin method to load from an Integer Array
    public void loadArray() {
        sb.append("    iaload");
        sb.append(System.lineSeparator());
    }

    // Jasmin method to load from a Long Array
    public void loadLongArray() {
        sb.append("    laload");
        sb.append(System.lineSeparator());
    }

    // Jasmin method to store in an Integer Array
    public void storeArray() {
        sb.append("    iastore");
        sb.append(System.lineSeparator());
    }

    // Jasmin method to store in a Long Array
    public void storeLongArray() {
        sb.append("    lastore");
        sb.append(System.lineSeparator());
    }

    /* JASMIN COMPARISON */

    // Jasmin method for <
    public void lessThan(int id) {
        sb.append("    if_icmplt const"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("const"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for < (long)
    public void lessThanLong(int id) {
        // Puts 1 on stack if a > b, 0 if a == b, -1 if a < b
        sb.append("    lcmp");
        sb.append(System.lineSeparator());
        sb.append("    iflt "); sb.append("lt"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("lt"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for >
    public void greaterThan(int id) {
        sb.append("    if_icmpgt const"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("const"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for > (long)
    public void greaterThanLong(int id) {
        // Puts 1 on stack if a > b, 0 if a == b, -1 if a < b
        sb.append("    lcmp");
        sb.append(System.lineSeparator());
        sb.append("    ifgt "); sb.append("gt"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("gt"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for <=
    public void greaterThanEquals(int id) {
        sb.append("    if_icmpge const"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("const"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for >= (long)
    public void greaterThanEqualsLong(int id) {
        // Puts 1 on stack if a > b, 0 if a == b, -1 if a < b
        sb.append("    lcmp");
        sb.append(System.lineSeparator());
        sb.append("    iflt "); sb.append("lt"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("lt"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for >=
    public void lessThanEquals(int id) {
        sb.append("    if_icmple const"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("const"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for <= (long)
    public void lessThanEqualsLong(int id) {
        // Puts 1 on stack if a > b, 0 if a == b, -1 if a < b
        sb.append("    lcmp");
        sb.append(System.lineSeparator());
        sb.append("    ifgt "); sb.append("gt"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("gt"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for ==
    public void equals(int id) {
        sb.append("    if_icmpeq const"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("const"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for == (long)
    public void equalsLong(int id) {
        // Puts 1 on stack if a > b, 0 if a == b, -1 if a < b
        sb.append("    lcmp");
        sb.append(System.lineSeparator());
        sb.append("    ifeq "); sb.append("eq"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("e1"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for == (object type)
    public void equalsObj(int id) {
        sb.append("    if_acmpeq const"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("const"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for !=
    public void equalsNot(int id) {
        sb.append("    if_icmpne const"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("const"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for != (long)
    public void equalsNotLong(int id) {
        // Puts 1 on stack if a > b, 0 if a == b, -1 if a < b
        sb.append("    lcmp");
        sb.append(System.lineSeparator());
        sb.append("    ifeq "); sb.append("eq"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("eq"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for != (object type)
    public void equalsNotObj(int id) {
        sb.append("    if_acmpne const"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // False
        sb.append(System.lineSeparator());
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("const"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // True
        sb.append(System.lineSeparator());
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    /* JASMIN LOGICAL CONNECTIVES */

    // Jasmin method for 'And'
    public void and() {
        sb.append("    iand");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for 'Or'
    public void or() {
        sb.append("    ior");
        sb.append(System.lineSeparator());
    }

    // Jasmin method for 'Not'
    public void not(int id) {
        // This might be done in an easier way
        sb.append("    ifeq "); sb.append("true"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("    ldc 0"); // Switch to false
        sb.append(System.lineSeparator());
        sb.append("    goto false"); sb.append(id);
        sb.append(System.lineSeparator());
        sb.append("true"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
        sb.append("    ldc 1"); // Switch to true
        sb.append(System.lineSeparator());
        sb.append("false"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    /* JASMIN BRANCH LABELS */

    // Jasmin method for if/while branch check
    // Also used by other types of branching
    public void ifCheck(int id) {
        sb.append("    ifeq "); sb.append("else"); sb.append(id);
        sb.append(System.lineSeparator());
    }

    // Jasmin method to handle IWE
    public void ifCheckWithoutElse(int id) {
        sb.append("    ifeq "); sb.append("skip"); sb.append(id);
        sb.append(System.lineSeparator());
    }

    // Jasmin method to branch if > 0
    public void ifInvCheck(int id) {
        sb.append("    ifgt "); sb.append("else"); sb.append(id);
        sb.append(System.lineSeparator());
    }

    // Jasmin method to set the jump label for 'else' (if/while)
    public void setElse(int id) {
        sb.append("else"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method to set the jump label when skipping 'else'
    public void setSkip(int id) {
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.lineSeparator());
    }

    // Jasmin method to used to skip the 'else' block
    public void skip(int id) {
        sb.append("    goto skip"); sb.append(id);
        sb.append(System.lineSeparator());
    }

    /* JASMIN ARRAY LENGTH */

    // Jasmin method to load the length of an array
    public void arrayLength() {
        sb.append("    arraylength");
        sb.append(System.lineSeparator());
    }

    /* JASMIN METHOD CALLS */

    // Jasmin virtual method call
    public void methodCall(String className, VMFrame vmf) {
        if(DEBUG) {
            if(vmf == null) System.out.println("  Got a NULL vmf for class name " + className);
        }
        if(JasminReservedWords.reservedWord(className))
            className += "_";
        sb.append("    invokevirtual "); sb.append(className);
        sb.append("/"); sb.append(vmf.procEntry());
        sb.append(System.lineSeparator());
    }

    // Jasmin method for 'this' call
    public void loadThis() {
        sb.append("    aload_0");
        sb.append(System.lineSeparator());
    }

    /* JASMIN HELPERS */

    // Jasmin method to duplicate the topmost stack item
    public void dup() {
        sb.append("    dup");
        sb.append(System.lineSeparator());
    }

    // Jasmin method to convert an integer to a long
    public void int2long() {
        sb.append("    i2l");
        sb.append(System.lineSeparator());
    }
}
