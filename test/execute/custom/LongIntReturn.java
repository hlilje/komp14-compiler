/* An execution test for long methods returning ints */
// EXT:LONG

class Main {
    public static void main(String[] args) {
        Long l;
        l = new Long();

        System.out.println(l.retLong());
        System.out.println(l.retInt());
        System.out.println(l.retRealInt());
    } 
}

class Long {
    long l;
    int i;

    public long retLong() {
        l = 0L;
        return l;
    }

    public long retInt() {
        i = 1;
        return i;
    }

    public int retRealInt() {
        return 1;
    }
}
