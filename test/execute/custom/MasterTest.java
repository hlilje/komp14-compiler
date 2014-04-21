/*
 * This is a valid test which contains all the extensions in
 * our grammar and does something useful
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
        int index;
        int res;
        same = new Useful().startSort();
        index = new Useful().startSearch();
        res = new Classy().method();

        if(same != true) { // CNE
            System.out.println(1);
        } else {
            System.out.println(0);
        }

        System.out.println(index);
        System.out.println(classy);
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
                i = i + 1;
            } else {}
        }
        return numIndex;
    }
}

class Classy {
    int f1;
    boolean f2;

    public int method() {
        return this.iter(900);
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
        }
        return temp;
    }
}
