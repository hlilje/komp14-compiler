/*
 * This is a small test.
 */

class Main {
    public static void main(String[] args) {
        ExpandableArray expArray;
        expArray = new ExpandableArray();
        System.out.println(expArray.insertAt(5, 10));
        System.out.println(expArray.insertAt(8, 20));
        System.out.println(expArray.get(5));
        System.out.println(expArray.get(8));
    }
}

/* An array that will expand if necessary */
class ExpandableArray {
    int[] array;
    boolean init; // Should be default false in Java

    public boolean init(int n) {
        array = new int[n];
        init = true;
        System.out.println(n); // Print the array length
        System.out.println(999999); // Delimiter
        return true || false; // BDJ
    }

    // Insert x at the given index i
    public int insertAt(int i, int x) {
        boolean dummy;
        int wasInit;
        // Make sure it has been created first
        if(!init) {
            dummy = this.init(i + 1);
            wasInit = 1;
        } else {
            wasInit = 0;
        }

        if(i > array.length - 1) { // CGT
            dummy = this.expandArray(i);
        } else {}
        array[i] = x; // Store the new value

        System.out.println(this.printArray(array)); // Print the array in its given state
        return wasInit;
    }

    // At is the index we would want to insert at
    public boolean expandArray(int at) {
        int[] tempArray;
        int i;
        // Calculate how big the new array needs to be
        tempArray = new int[array.length + (at - array.length) + 1];
        i = 0;
        while(i < array.length) {
            // Copy over old values
            tempArray[i] = array[i];
            i = i + 1;
        }
        array = tempArray; // Use the new instead

        System.out.println(this.printArray(array)); // Print the array in its given state
        return true;
    }

    // Get the value at the given index, returns 0 if out-of-bounds
    public int get(int i) {
        int ret;
        ret = 0;

        if(i < array.length) {
            ret = array[i];
        } else {}

        return ret;
    }

    // Helper method to print the contents of an array
    public int printArray(int[] array) {
        int i;
        i = 0;
        while(i < array.length) {
            System.out.println(array[i]);
            i = i + 1;
        }
        System.out.println(999999); // Delimiter
        return 0;
    }
}
