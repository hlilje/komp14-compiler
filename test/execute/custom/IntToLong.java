/* An execution test for converting ints to longs */
// EXT:LONG

class Main {
    public static void main(String[] args) {
        //System.out.println(1 + 2L);
        System.out.println(2L + 1);
    }
}

class Long {
    public long getLong() {
        return 1L;
    }

    public int getInt() {
        return 1;
    }
}
