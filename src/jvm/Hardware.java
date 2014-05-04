package jvm;

import syntaxtree.BooleanType;
import syntaxtree.FormalList;
import syntaxtree.IdentifierType;
import syntaxtree.IntArrayType;
import syntaxtree.IntegerType;
import syntaxtree.Type;

import symbol.Binding;

public class Hardware
{
    static String signature(Type t) {
        if(t instanceof BooleanType) {
            return "B";
        }
        else if(t instanceof IntegerType) {
            return "I";
        }
        else if(t instanceof IntArrayType) {
            return "[I";
        }
        else if(t instanceof IdentifierType) {
            return "L" + t.toString() + ";";
        }
        else {
            throw new InternalError("Unknown type " + t);
        }
    }

    static String methodSignature(FormalList formals, Type returnType) {
        String sig = "(";
        if(formals != null) {
            for(int i = 0; i < formals.size(); i++) {
                sig = sig + Hardware.signature(formals.elementAt(i).t);
            }
        }
        sig = sig + ")";
        if(returnType != null) {
            sig = sig + Hardware.signature(returnType);
        }
        else {
            sig = sig + "V";
        }
        return sig;
    }

    // This method has been added to generate the signature from a list of
    // bindings, instead of the actual FormalList
    static String methodSignature(java.util.ArrayList<Binding> formals, Type returnType) {
        String sig = "(";
        if(formals != null) {
            for(int i = 0; i < formals.size(); i++) {
                sig = sig + Hardware.signature(formals.get(i).getType());
            }
        }
        sig = sig + ")";
        if(returnType != null) {
            sig = sig + Hardware.signature(returnType);
        }
        else {
            sig = sig + "V";
        }
        return sig;
    }
}
