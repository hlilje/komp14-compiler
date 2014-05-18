/* An execution test for converting ints to longs */
// EXT:LONG
// EXT:IWE
// EXT:CEQ
// EXT:CNE

class Main {
    public static void main(String[] args) {
        System.out.println(2L + 1);
        System.out.println(1 + 2L);
        System.out.println(1 + 1);
        System.out.println(2L + 2L);
        System.out.println(1 + 2L + 3 + 4L + 5 + 6 + 7L + 8L + 9L + 10 + 11l);
        System.out.println(999999999L);
        System.out.println(8 - 4);
        System.out.println(4 - 8);
        System.out.println(999999999L);
        System.out.println(3 * 2);
        System.out.println(2 * 3);

        if(2 < 3L) {
            System.out.println(1);
        }
        if(3 < 2L) {
            System.out.println(0);
        }
        if(2L < 3) {
            System.out.println(2);
        }
        if(3L < 2) {
            System.out.println(0);
        }
        System.out.println(999999999L);
        if(2 == 2L) {
            System.out.println(3);
        }
        if(1 == 2L) {
            System.out.println(0);
        }
        if(1L == 1) {
            System.out.println(4);
        }
        if(1L == 2) {
            System.out.println(0);
        }
        System.out.println(999999999L);
        if(1 != 2L) {
            System.out.println(5);
        }
        if(2 != 2L) {
            System.out.println(0);
        }
        if(1L != 2) {
            System.out.println(6);
        }
        if(1L != 1) {
            System.out.println(0);
        }
    }
}
