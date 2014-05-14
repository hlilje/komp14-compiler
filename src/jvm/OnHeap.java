package jvm;

import jasmin.JasminReservedWords;

public class OnHeap implements frame.VMAccess {
    public OnHeap(String className, String fieldName, String signature) {
        // Added checks to handle reserved Jasmin words
        if(JasminReservedWords.reservedWord(className))
            className += "_";
        if(JasminReservedWords.reservedWord(fieldName))
            fieldName += "_";
        c = className;
        f = fieldName;
        s = signature;
    }

    public String toString() {
        return "jvm.OnHeap(" + c + ", " + f + ", " + s + ")";
    }

    public String declare() {
        return ".field public " + f + " " + s;
    }

    public String load() {
        return
            "    aload_0 ; this\n" +
            "    getfield " + c + "/" + f + " " + s;
    }

    // We get one extra instruction here, since the arguments
    // end up in wrong order on the stack.
    public String store() {
        String str = "";

        if(s.equals("J")) { // J = Long
            str =
                "    aload_0 ; this\n" +
                "    dup_x2\n" +
                "    pop\n" +
                "    putfield " + c + "/" + f + " " + s;
        } else {
            str = 
                "    aload_0 ; this\n" +
                "    swap\n" +
                "    putfield " + c + "/" + f + " " + s;
        }

        return str;
    }

    private String c;
    private String f;
    private String s;
}
