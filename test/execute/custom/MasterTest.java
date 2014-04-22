/*
 * This is a valid test which contains all the extensions in
 * our grammar and does something useful.
 *
 * This test is 1059 tokens large.
 */
// EXT:CLE
// EXT:CGT
// EXT:CGE
// EXT:CEQ
// EXT:CNE
// EXT:BDJ
// EXT:NBD

class Master {
    public static void main(String[] args) {
        boolean same;
        Or or;
        int[] toBeSorted;

        same = new Useful().startSort();
        or = new Or().init();

        // Init
        toBeSorted = new int[5];
        toBeSorted[0] = 123;
        toBeSorted[1] = 2;
        toBeSorted[2] = 20934;
        toBeSorted[3] = 293;
        toBeSorted[4] = 0;

        if(same != true) { // CNE
            System.out.println(1);
        } else {
            System.out.println(0);
        }

        System.out.println(new Useful().startSearch());
        System.out.println(new Classy().method());
        System.out.println(new Fibonacci().start(10));
        System.out.println(or.Or(20));
    }
}

class Useful {
    int[] sortMe;
    int[] searchMe;

    public boolean startSort() {
        sortMe = new int[10];
        sortMe[0] = 12345;
        sortMe[1] = 0;
        sortMe[2] = (123*234) - 444;
        sortMe[3] = 44;
        sortMe[4] = 23948;
        sortMe[5] = 9245;
        sortMe[6] = 1147;
        sortMe[7] = 9348;
        sortMe[8] = 123998;
        sortMe[9] = 8;

        return this.sortArray(sortMe);
    }

    public int startSearch() {
        searchMe = new int[10];
        searchMe[0] = 1123 - (123*2);
        searchMe[1] = 2;
        searchMe[2] = 124;
        searchMe[3] = 9;
        searchMe[4] = 1111;
        searchMe[5] = 42;
        searchMe[6] = 0;
        searchMe[7] = 1;
        searchMe[8] = 5006996;
        searchMe[9] = 120943;

        return this.searchArray(searchMe, 42);
    }

    // Bubble sort
    public boolean sortArray(int[] sortMe) {
        int i;
        int j;
        boolean same;
        i = 0;
        j = 1;

        while(i <= sortMe.length - 1) { // CLE
            while(sortMe.length - 2 >= j) { // CGE
                int test;
                if(sortMe[j - 1] > sortMe[j]) { // CGT
                    int temp; // NBD
                    temp = sortMe[j - 1];
                    sortMe[j - 1] = sortMe[j];
                    sortMe[j] = temp;
                } else {}

                j = j + 1;
            }

            i = i + 1;
        }

        if(sortMe[sortMe.length - 1] == sortMe[0]) { // CEQ
            same = false;
        } else {
            same = true;
        }
        return same;
    }

    // Search for the index of the given number
    public int searchArray(int[] searchMe, int num) {
        int i;
        int numIndex;
        i = 1;
        numIndex = 2147483647;

        while(i < searchMe.length) {
            if(searchMe[i] == num) {
                numIndex = i;
            } else {}

            i = i + 1;
        }
        return numIndex;
    }

    // Insertion sort of given array for descending order
    public int[] insertionSort(int[] a) {
        int i;
        int j; // Number of items sorted so far
        int key; // Item to be sorted
        j = 1; // Start with 1

        while(j < a.length) {
            key = a[j];

            i = j - 1;
            while((i >= 0) && (a[i] < key)) {
                a[i + 1] = a[i]; // Move up smaller values

                i = i - 1;
            }
            a[i + 1] = key; // Put the key where it belongs

            j = j + 1;
        }

        return a;
    }
}

/* Less usesful class */
class Classy {
    int f1;
    boolean f2;

    public int method() {
        return this.iter(100);
    }

    public int getInt() {
        return f1;
    }

    public boolean setBool(boolean p) {
        f2 = p;
        return f2;
    }

    public int iter(int a) {
        int temp;
        int i;
        boolean cont;
        cont = true;
        temp = a;
        i = 0;

        while(cont) {
            if(i > a) {
                cont = false;
            } else {
                cont = true;
            }
            
            i = i + 1;
        }
        return temp;
    }
}

// Class which has a method that prints the Fibonacci series
// based on given n
class Fibonacci {
    public int start(int n) {
        int ret;
        int i;
        int grandparent;
        int parent;
        boolean skip;

        grandparent = 1;
        parent = 3;
        i = 2;
        skip = false;

        if(n == 0) {
            ret = 1;
            skip = true;
        } else if(n == 1) {
            ret = 3;
            skip = true;
        } else {}

        if(!skip) {
            while(i <= n) {
                ret = 3 * (parent - grandparent);
                grandparent = parent;
                parent = ret;

                i = i + 1;
            }
        } else {}

        System.out.println(ret);
        return ret;
    }
}

// This is not such a useful // class /// ******/
class Or {
    boolean or;
    boolean not;

    public boolean Or(int n) {
        int counter;
        or = false;
        not = false;
        counter = 1;

        while(or || !not) { // BDJ
            int temp;
            temp = 1;

            if(true && false) {
                // Nothing
            } else {
                while(counter < n) {
                    counter = counter + counter;
                    System.out.println(counter);
                    if(counter > 0) { // Will always happen!
                        not = true; // Break the outer loop
                        temp = counter;
                    } else { // Won't happen
                        not = false;
                        counter = temp;
                    }
                }
            }
        }
        return true || false;
    }

    public Or init() {
        return this;
    }
}
