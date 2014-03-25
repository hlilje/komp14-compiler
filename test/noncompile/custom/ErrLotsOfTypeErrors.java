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
    A a;
    C c;
    int i;
    boolean b;
    int[] ia;
/*
    a = new A();
    c = new C();
*/
    public int notanint() {
        return true;
    }

    public int notanint2() {
        return a.getc();
    }

    public boolean notabool() {
        return 1;
    }

    public boolean notabool2() {
        return a.getia();
    }

    public int[] notanarray() {
        return this;
    }

    public int[] notanarray2() {
        return a.geti();
    }

    public B notab() {
        return x;
    }

    public B notab2() {
        return a.geta();
    }

    public boolean wrongparams() {
        return c.formals(ia, c, b, i);
    }
}

class C {
    int i;
    boolean b;
    A a;

    public boolean formals(int i, boolean b, int[] ia, A a) {
        return false;
    }
}
