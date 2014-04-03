/* A test with tricky uses of 'this' that should succeed */
class ThisTest {
    public static void main(String[] args) {
        A a;
        B b;
        a = new A();
        b = new B();
    }
}

class A {
    C cvar;

    public A a(int asd) {
        return this; // TODO Should this be allowed?
    }

    public C c(int p1, A p2) {
        return cvar;
    }

    public C d(A asf) {
        return this.c();
    }

    public A rec(A recs) {
        // TODO Nullpointer exception, seems to be for every 'undeclared' param (this should however work)
        return this.recs(this);
    }

    public int inter() {
        cvar = this.c(123, this);
        cvar = this.d(this);
        return 0;
    }
}

class B {
    boolean i;
    boolean l;
    int j;
    int k;

    public boolean a() {
        i = false;
        k = this.b(this.c(this.d(), 0), 123);
        return i;
    }

    public int b(int f1, int f2) {
        return 1;
    }

    public int c(int first, int second) {
        return 123;
    }

    public int d() {
        return 1 - 1;
    }

    public boolean e() {
       l = 3 < this.b(1, 2) + 0 * this.d();

        return this.e() && false || true == this.v(this.a());
    }

    public boolean v(boolean param) {
        return param && j < k;
    }
}
