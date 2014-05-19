/* An execution test that goes out of bounds */
// EXT:ABC

class Main {
    public static void main(String[] args) {
        int[] array;
        array = new int[3];
        array[2] = 1;
        System.out.println(array[3]); // Woops
    }
}
