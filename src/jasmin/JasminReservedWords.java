/**
 * A hash set which contains (almost) all the reserved words in Jasmin.
 */

package jasmin;

public class JasminReservedWords {
    static java.util.HashSet reservedWords;

    public static boolean reservedWord(String word) {
        return reservedWords.contains(word);
    }

    static {
        reservedWords = new java.util.HashSet();

        // Add the jvm instructions in Jasmin that take no parameters
        reservedWords.add("aaload");
        reservedWords.add("aastore");
        reservedWords.add("aconst_null");
        reservedWords.add("aload_0");
        reservedWords.add("aload_1");
        reservedWords.add("aload_2");
        reservedWords.add("aload_3");
        reservedWords.add("areturn");
        reservedWords.add("arraylength");
        reservedWords.add("astore_0");
        reservedWords.add("astore_1");
        reservedWords.add("astore_2");
        reservedWords.add("astore_3");
        reservedWords.add("athrow");
        reservedWords.add("baload");
        reservedWords.add("bastore");
        reservedWords.add("breakpoint");
        reservedWords.add("caload");
        reservedWords.add("castore");
        reservedWords.add("d2f");
        reservedWords.add("d2i");
        reservedWords.add("d2l");
        reservedWords.add("dadd");
        reservedWords.add("daload");
        reservedWords.add("dastore");
        reservedWords.add("dcmpg");
        reservedWords.add("dcmpl");
        reservedWords.add("dconst_0");
        reservedWords.add("dconst_1");
        reservedWords.add("ddiv");
        reservedWords.add("dload_0");
        reservedWords.add("dload_1");
        reservedWords.add("dload_2");
        reservedWords.add("dload_3");
        reservedWords.add("dmul");
        reservedWords.add("dneg");
        reservedWords.add("drem");
        reservedWords.add("dreturn");
        reservedWords.add("dstore_0");
        reservedWords.add("dstore_1");
        reservedWords.add("dstore_2");
        reservedWords.add("dstore_3");
        reservedWords.add("dsub");
        reservedWords.add("dup");
        reservedWords.add("dup2");
        reservedWords.add("dup2_x1");
        reservedWords.add("dup2_x2");
        reservedWords.add("dup_x1");
        reservedWords.add("dup_x2");
        reservedWords.add("f2d");
        reservedWords.add("f2i");
        reservedWords.add("f2l");
        reservedWords.add("fadd");
        reservedWords.add("faload");
        reservedWords.add("fastore");
        reservedWords.add("fcmpg");
        reservedWords.add("fcmpl");
        reservedWords.add("fconst_0");
        reservedWords.add("fconst_1");
        reservedWords.add("fconst_2");
        reservedWords.add("fdiv");
        reservedWords.add("fload_0");
        reservedWords.add("fload_1");
        reservedWords.add("fload_2");
        reservedWords.add("fload_3");
        reservedWords.add("fmul");
        reservedWords.add("fneg");
        reservedWords.add("frem");
        reservedWords.add("freturn");
        reservedWords.add("fstore_0");
        reservedWords.add("fstore_1");
        reservedWords.add("fstore_2");
        reservedWords.add("fstore_3");
        reservedWords.add("fsub");
        reservedWords.add("i2d");
        reservedWords.add("i2f");
        reservedWords.add("i2l");
        reservedWords.add("iadd");
        reservedWords.add("iaload");
        reservedWords.add("iand");
        reservedWords.add("iastore");
        reservedWords.add("iconst_0");
        reservedWords.add("iconst_1");
        reservedWords.add("iconst_2");
        reservedWords.add("iconst_3");
        reservedWords.add("iconst_4");
        reservedWords.add("iconst_5");
        reservedWords.add("iconst_m1");
        reservedWords.add("idiv");
        reservedWords.add("iload_0");
        reservedWords.add("iload_1");
        reservedWords.add("iload_2");
        reservedWords.add("iload_3");
        reservedWords.add("imul");
        reservedWords.add("ineg");
        reservedWords.add("int2byte");
        reservedWords.add("int2char");
        reservedWords.add("int2short");
        reservedWords.add("ior");
        reservedWords.add("irem");
        reservedWords.add("ireturn");
        reservedWords.add("ishl");
        reservedWords.add("ishr");
        reservedWords.add("istore_0");
        reservedWords.add("istore_1");
        reservedWords.add("istore_2");
        reservedWords.add("istore_3");
        reservedWords.add("isub");
        reservedWords.add("iushr");
        reservedWords.add("ixor");
        reservedWords.add("l2d");
        reservedWords.add("l2f");
        reservedWords.add("l2i");
        reservedWords.add("ladd");
        reservedWords.add("laload");
        reservedWords.add("land");
        reservedWords.add("lastore");
        reservedWords.add("lcmp");
        reservedWords.add("lconst_0");
        reservedWords.add("lconst_1");
        reservedWords.add("ldiv");
        reservedWords.add("lload_0");
        reservedWords.add("lload_1");
        reservedWords.add("lload_2");
        reservedWords.add("lload_3");
        reservedWords.add("lmul");
        reservedWords.add("lneg");
        reservedWords.add("lor");
        reservedWords.add("lrem");
        reservedWords.add("lreturn");
        reservedWords.add("lshl");
        reservedWords.add("lshr");
        reservedWords.add("lstore_0");
        reservedWords.add("lstore_1");
        reservedWords.add("lstore_2");
        reservedWords.add("lstore_3");
        reservedWords.add("lsub");
        reservedWords.add("lushr");
        reservedWords.add("lxor");
        reservedWords.add("monitorenter");
        reservedWords.add("monitorexit");
        reservedWords.add("nop");
        reservedWords.add("pop");
        reservedWords.add("pop2");
        reservedWords.add("return");
        reservedWords.add("saload");
        reservedWords.add("sastore");
        reservedWords.add("swap");

        // Add the reserved words in Jasmin that take parameters
        reservedWords.add("aload");
        reservedWords.add("anewarray");
        reservedWords.add("astore");
        reservedWords.add("bipush");
        reservedWords.add("checkcast");
        reservedWords.add("default");
        reservedWords.add("dload");
        reservedWords.add("dstore");
        reservedWords.add("getfield");
        reservedWords.add("getstatic");
        reservedWords.add("fload");
        reservedWords.add("fstore");
        reservedWords.add("goto");
        reservedWords.add("goto_w");
        reservedWords.add("if_acmpeq");
        reservedWords.add("if_acmpne");
        reservedWords.add("if_icmpeq");
        reservedWords.add("if_icmpge");
        reservedWords.add("if_icmpgt");
        reservedWords.add("if_icmple");
        reservedWords.add("if_icmplt");
        reservedWords.add("if_icmpne");
        reservedWords.add("ifeq");
        reservedWords.add("ifge");
        reservedWords.add("ifgt");
        reservedWords.add("ifle");
        reservedWords.add("iflt");
        reservedWords.add("ifne");
        reservedWords.add("ifnonnull");
        reservedWords.add("ifnull");
        reservedWords.add("iinc");
        reservedWords.add("iload");
        reservedWords.add("instanceof");
        reservedWords.add("invokenonvirtual");
        reservedWords.add("invokestatic");
        reservedWords.add("invokevirtual");
        reservedWords.add("istore");
        reservedWords.add("jsr");
        reservedWords.add("jsr_w");
        reservedWords.add("ldc");
        reservedWords.add("ldc_w");
        reservedWords.add("lload");
        reservedWords.add("lookupswitch");
        reservedWords.add("lstore");
        reservedWords.add("multianewarray");
        reservedWords.add("new");
        reservedWords.add("newarray");
        reservedWords.add("putfield");
        reservedWords.add("putstatic");
        reservedWords.add("ret");
        reservedWords.add("sipush");
        reservedWords.add("tableswitch");

        // Reserved words for Jasmin directives
        reservedWords.add("class");
        reservedWords.add("field");
        reservedWords.add("from");
        reservedWords.add("inner");
        reservedWords.add("invisible");
        reservedWords.add("invisibleparam");
        reservedWords.add("is");
        reservedWords.add("locals");
        reservedWords.add("method");
        reservedWords.add("offset");
        reservedWords.add("outer");
        reservedWords.add("signature");
        reservedWords.add("stack");
        reservedWords.add("to");
        reservedWords.add("use");
        reservedWords.add("using");
        reservedWords.add("visible");
        reservedWords.add("visibleparam");
    }
}
