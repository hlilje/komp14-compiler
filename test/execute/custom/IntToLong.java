/* An execution test for converting ints to longs */
// EXT:LONG

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
    }
}
