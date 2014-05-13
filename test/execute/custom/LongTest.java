/* An execution test for long types */
// EXT:LONG

class Main {
    public static void main(String[] args) {
        long a;
        long[] b;
        a = 3L;
        b = new long[3];
        b[123] = 2L;

        if(a < 3L) {
            System.out.println(false);
        }

        if(a < 3L) {
            System.out.println(false);
        }

        if(a < b[0]) {
            System.out.println(false);
        }
    }
}
