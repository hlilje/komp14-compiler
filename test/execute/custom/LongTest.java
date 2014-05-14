/* An execution test for long types */
// EXT:LONG
// EXT:CLE
// EXT:CGT
// EXT:CGE
// EXT:NBD
// EXT:IWE

class Main {
    public static void main(String[] args) {
        Long longClass;
        long l;
        int i;
        int[] ia;
        long[] la;
        i = 1;
        l = 2L;
        ia = new int[3];
        la = new long[4];
        ia[2] = 1;
        la[3] = 2L;
        System.out.println(i);
        System.out.println(l);

        if(i < 3) {
            System.out.println(true);
        }

        if(l < 3L) {
            System.out.println(false);
        }

        if(l <= la[3]) {
            System.out.println(1);
        }

        if(2L <= 2L) {
            System.out.println(2);
        }

        if(i >= ia[2]) {
            System.out.println(3);
        }

        longClass = new Long();
        System.out.println(longClass.getLong());
    }
}

class Long {
    long l1;
    int i1;
    long l2;
    int i2;

    public long getLong() {
        long l1;
        int i1;
        long l2;
        int i2;
        l1 = 2L;
        i1 = 4;
        l2 = 5L;
        i2 = 4000;
        return l2;
    }

    public int getInt() {
        i1 = 10;
        return i1;
    }
}
