/* An execution test for long fields */
// EXT:LONG

class Main {
    public static void main(String[] args) {
        System.out.println(new Long().getLong());
    }
}

class Long {
    long f1;

    public long getLong() {
        f1 = 2L;
        //return f1;
        return 1L;
    }

    public int getInt() {
        return 1;
    }
}
