// EXT:NBD

class ValidBlock {
    public static void main(String[] args) {
        int i;
        i = 1;
        while(i < 10) {
            int a; // TODO Not found
            i = i + 1;
            a = i * 2;
        }
    }
}

class A {
    int z;
    int x;

    public int method() {
        z = 3;
        x = 3;
        while(false) {
            z = z + i; // TODO? Gives type error, not undeclared error
            x = x + z;
        }
        return b;
    }
}

class B {
    int b;
    int c;

    public int method() {
        b = 123;
        while(false) {
            int u;
            b = 12;
            u = 3;
            while(false) {
                int v;
                b = 13;
                v = 0;
                while(false) {
                    int w;
                    w = a + b;
                }
            }
        }
        return 0;
    }
}
