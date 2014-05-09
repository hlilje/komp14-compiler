package jvm;

import syntaxtree.Type;
import jasmin.JasminReservedWords;

/**
  An implementation of the VMRecord interface for the Java
  virtual machine (JVM).
  @see frame.VMRecord
  */
public class Record implements frame.VMRecord {
    private int offset = 0;
    private String name;

    public Record(String className) {
        if(JasminReservedWords.reservedWord(className))
            className += "_";
        name = className;
    }

    public String toString() {
        return "jvm.Record(" + name + ", " + offset + ")";
    }

    public frame.VMAccess allocField(String id, Type type) {
        offset++;
        return new OnHeap(name, id, Hardware.signature(type));
    }

    public int numberOfFields() {
        return offset;
    }
}
