/**
 * Generates Jasmin assembly code
 */

package jvm;

import syntaxtree.*;
import error.*;
import jvm.*;
import frame.VMAccess;
import frame.VMFrame;

public class JasminFileWriter {
    private ErrorHandler error;

    private String filePath; // Where to generate Jasmin files
    private java.lang.StringBuilder sb; // The Jasmin string to write to file

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
        // Declare Jasmin source file
        sb.append(".source "); sb.append(src); sb.append(".j");
        sb.append(System.getProperty("line.separator"));
        sb.append(".class "); sb.append(clss);
        sb.append(System.getProperty("line.separator"));
        sb.append(".super "); sb.append(spr);
        sb.append(System.getProperty("line.separator"));
    }

    // Wrapper method to declare a field in a Jasmin source file
    public void declareField(VMAccess vma) {
        sb.append(vma.declare());
        sb.append(System.getProperty("line.separator"));
    }

    // Wrapper method to declare a local Jasmin variable in a method
    public void declareLocal(VMAccess vma) {
        sb.append(vma.declare());
        sb.append(System.getProperty("line.separator"));
    }

    // Helper method to declare a method for a Jasmin source file
    // Doesn't close the method tag since it must happen after visit
    public void declareMethod(String acs, VMFrame vmf) {
        sb.append(".method "); sb.append(acs); sb.append(" ");
        sb.append(vmf.procEntry()); // Name decl according to Jasmin spec
        sb.append(System.getProperty("line.separator"));
    }

    // Special method to handle the main method with Jasmin
    public void declareMainMethod() {
        sb.append(".method public static main([Ljava/lang/String;)V");
        sb.append(System.getProperty("line.separator"));
    }

    // Helper method to end a Jasmin method declaration
    public void declareMethodEnd() {
        sb.append(".end method");
        sb.append(System.getProperty("line.separator"));
    }

    // Helper method to set which directives to use for the Jasmin method decl
    public void limitMethod(int locals, int stackDepth) {
        sb.append("    .limit stack "); sb.append(stackDepth);
        sb.append(System.getProperty("line.separator"));
        sb.append("    .limit locals "); sb.append(locals);
        sb.append(System.getProperty("line.separator"));
    }

    /* JASMIN RETURNS */

    // Sets the return op based on given type
    public void setReturn(Type t) {
        if(t == null)
            sb.append("    return");
        else
            sb.append(returnWithType(t));
        sb.append(System.getProperty("line.separator"));
    }

    // Helper method to format the return type for Jasmin
    private String returnWithType(Type t) {
        if(t instanceof IntegerType)
            return "    ireturn";
        else if(t instanceof BooleanType)
            return "    ireturn"; // TODO Float?
        else if(t instanceof IdentifierType)
            return "    areturn"; // TODO Object?
        else if(t instanceof IntArrayType)
            return "    areturn"; // TODO Object?
        else {
            error.complain("Invalid return type in JasminFileWriter",
                    ErrorHandler.ErrorCode.INTERNAL_ERROR);
            return "";
        }
    }

    /* JASMIN CONSTANTS */

    // Wrapper method to push an interger literal to the stack
    public void pushInt(IntegerLiteral n) {
        sb.append("    ldc "); sb.append(n.i);
        sb.append(System.getProperty("line.separator"));
    }

    /* JASMIN OPERATORS */

    // Jasmin add op
    public void add() {
        sb.append("    iadd");
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin minus op
    public void minus() {
        sb.append("    ineg");
        sb.append(System.getProperty("line.separator"));
        add(); // Add negated number
    }

    // Jasmin multiplication op
    public void mul() {
        sb.append("    imul");
        sb.append(System.getProperty("line.separator"));
    }

    /* JASMIN OBJECT CREATION */

    // Declare new Jasmin class
    // No inheritance
    public void newObject(String className) {
        //sb.append("    new java/lang/Object/"); sb.append(className);
        sb.append("    new "); sb.append(className);
        sb.append(System.getProperty("line.separator"));
    }

    // Declare new Jasmin int array
    public void newArray() {
        sb.append("    newarray int");
        sb.append(System.getProperty("line.separator"));
    }

    /* JASMIN PRINT */

    // Call Java's print method with Jasmin
    public void print() {
        sb.append("    getstatic java/lang/System/out Ljava/io/PrintStream;");
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method to finish the print call
    public void printAfter() {
        sb.append("    invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        sb.append(System.getProperty("line.separator"));
    }

    /* JASMIN LOADING / STORING */

    // Jasmin method to load the given VMAccess
    public void loadAccess(VMAccess vma) {
        sb.append(vma.load());
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method to store at the given VMAccess
    public void storeAccess(VMAccess vma) {
        sb.append(vma.store());
        sb.append(System.getProperty("line.separator"));
    }

    /* JASMIN COMPARISON */

    // Jasmin method for < ('And' branch)
    public void lessThanAnd(int id) {
        sb.append("    if_icmpge else"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method for < ('Or' branch)
    public void lessThanOr(int id) {
        sb.append("    if_icmpge if"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method for > ('And' branch)
    public void greaterThanAnd(int id) {
        sb.append("    if_icmple else"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method for > ('Or' branch)
    public void greaterThanOr(int id) {
        sb.append("    if_icmple if"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method for <= ('And' branch)
    public void greaterThanEqualsAnd(int id) {
        sb.append("    if_icmplt else"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method for <= ('Or' branch)
    public void greaterThanEqualsOr(int id) {
        sb.append("    if_icmplt if"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method for >= ('And' branch)
    public void lessThanEqualsAnd(int id) {
        sb.append("    if_icmpgt else"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method for >= ('Or' branch)
    public void lessThanEqualsOr(int id) {
        sb.append("    if_icmpgt if"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method for == ('And' branch)
    public void equalsAnd(int id) {
        sb.append("    if_icmpeq else"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method for == ('Or' branch)
    public void equalsOr(int id) {
        sb.append("    if_icmpeq if"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method for == ('And' branch)
    public void equalsNotAnd(int id) {
        sb.append("    if_icmpne else"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method for == ('Or' branch)
    public void equalsNotOr(int id) {
        sb.append("    if_icmpne if"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    /* JASMIN BRANCH LABELS */

    // Jasmin method to set the jump label for 'else'
    public void setElse(int id) {
        sb.append("else"); sb.append(id); sb.append(":");
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method to set the jump label when skipping 'else'
    public void setSkip(int id) {
        sb.append("skip"); sb.append(id); sb.append(":");
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method to used to skip the 'else' block
    public void setGotoSkip(int id) {
        sb.append("goto skip"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }

    // Jasmin method to used to set a label for the 'if' block
    public void setIf(int id) {
        sb.append("if"); sb.append(id); sb.append(":");
        sb.append(System.getProperty("line.separator"));
    }

    // By using this method for 'while' it's possible to reuse the
    // methods for 'if' 'else' branching
    public void setGotoIf(int id) {
        sb.append("goto if"); sb.append(id);
        sb.append(System.getProperty("line.separator"));
    }
}
