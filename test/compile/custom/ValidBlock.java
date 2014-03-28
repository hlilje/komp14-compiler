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
    int b;
    int c;

    public int method() {
        b = 3;
        c = 3;
        while(false) {
            b = b + i; // TODO? Gives type error, not undeclared error
            b = b + c;
        }
        return b;
    }
}
