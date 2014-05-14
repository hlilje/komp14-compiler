/* An execution test for inheritance and long */
// EXT:LONG
// EXT:ISC
// EXT:ICG

class Main {
    public static void main(String[] args) {
        //System.out.println(new Long().getLong());
        System.out.println(new Long().getInt());
    }
}

class Long extends ExtendMe {
    public long getLong() {
        return f1;
    }
}

class ExtendMe {
    long f1;

    public long getLong() {
        f1 = 3L;
        return 1L;
    }

    public int getInt() {
        return 1;
    }
}
