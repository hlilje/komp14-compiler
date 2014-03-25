class Main
{
    public static void main(String[] a){
        System.out.println(5);
    }
}

class A {
    int i;
    int[] ia;
    B b;
    C c;
    Boolean bool;

    public int geti() {
        return i;
    }

    public int[] getia() {
        return ia;
    }

    public A geta() {
        return this;
    }

    public B getb() {
        return b;
    }

    public C getc() {
        return c;
    }

    public boolean getbool() {
        return bool;
    }
}

class B {
    int x;
    boolean s;

    public int notanint() {
        return true;
    }

    public boolean notabool() {
        return 1;
    }

    public int[] notanarray() {
        return this;
    }

    public B notab() {
        return x;
    }
}

class C {
    int y;
    boolean t;

    public Other v() {
        return false;
    }
}
