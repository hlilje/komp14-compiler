// EXT:NBD

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
