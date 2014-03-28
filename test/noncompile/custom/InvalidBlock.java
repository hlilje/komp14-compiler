// EXT:NBD

// TODO Check that this works
class InvalidBlock {
    public static void main(String[] args) {
        int i;
        i = 1;
        u = i - 123;
        while(i < 10) {
            int i; // Already declared outside
            i = i + 1;
            a = i * 2;
        }
    }
}

class Help {
    int u;

    public int helper() {
        u = 3;
        while(false) {
            int u; // Already declared outside
            u = u;
        }
        return u;
    }
}

class Hello {
    int b;
    int c;

    public int method() {
        b = 123;
        while(false) {
            int a;
            b = 12;
            a = 3;
            while(false) {
                int a; // Not allowed
                int z;
                b = 13;
                z = b + 2;
                while(false) {
                    int a; // Not allowed
                    int b; // Not allowed
                    int u;
                    u = a + b;
                }
            }
        }
        return 0;
    }
}
