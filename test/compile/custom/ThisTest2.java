
class Test {
    public static void main(String[] args) {
        boolean dummy;
        dummy = new Test2().start();
        System.out.println(dummy);
    }
}

class Test2 {
    boolean b;
    int f;

    public boolean start() {
        b = this.run().finish();
        return true;
    }

    public boolean finish() {
        f = this.foo(this.run().run().run());
        if (this.run().foo(this) < this.foo(this)) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    public int foo(Test2 t) {
        System.out.println(this.foo(this) < this.bar());
        return this.bar();
    }

    public int bar() {
        if(this.bar() < this.bar())
            f = 6;
        else
            f = 6;
        return 6;
    }

    public Test2 run() {
        while(this.bar() < this.foo(this)) {
            f = this.bar();
        }
        return this;
    }
}
