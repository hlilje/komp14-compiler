package jvm;

import syntaxtree.FormalList;
import syntaxtree.Type;
import syntaxtree.LongType;
import jasmin.JasminReservedWords;

/**
  An implementation of the VMFrame interface for the Java
  virtual machine (JVM).
  @see frame.VMFrame
  */
public class Frame implements frame.VMFrame {
    private int formals = 0;
    private int locals = 0;
    private String name;
    private String sig;

    public Frame(String methodName, FormalList formals, Type returnType) {
        // Added checks to handle reserved Jasmin words
        if(JasminReservedWords.reservedWord(methodName))
            methodName += "_";
        name = methodName;
        sig = Hardware.methodSignature(formals, returnType);
    }

    public String toString() {
        return "jvm.Frame(" + name + ", " + sig + ")";
    }

    public frame.VMAccess allocFormal(String id, Type type) {
        formals++;
        if(type instanceof LongType) formals++; // Long has double the size
        return alloc(id, Hardware.signature(type));
    }

    public frame.VMAccess allocLocal(String id, Type type) {
        locals++;
        if(type instanceof LongType) locals++; // Long has double the size
        return alloc(id, Hardware.signature(type));
    }

    public int numberOfFormals() {
        return formals;
    }

    public int numberOfLocals() {
        return locals;
    }

    public String procEntry() {
        // All functions in the JVM are called by name concatenated
        // with type signature.
        return name + sig;
    }

    private frame.VMAccess alloc(String id, String signature) {
        // The signature is actually only used for debug purposes
        // in IntegerInFrame and ObjectInFrame, but it comes in
        // handy here, too, since we need to find out what kind
        // of VMAccess object to return.
        if(signature.equals("B") || signature.equals("I"))
            return new IntegerInFrame(id, formals + locals - 1, signature);
        else if(signature.equals("J"))
            // Index is -2 due to double size
            return new LongInFrame(id, formals + locals - 2, signature);
        else
            return new ObjectInFrame(id, formals + locals - 1, signature);
    }

}
