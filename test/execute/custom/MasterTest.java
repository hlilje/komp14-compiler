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
        same = new Useful().startSort();

        if(same != true) { // CNE
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }
}

class Useful {
    int[] sortMe;

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

    // Bubble sort
    public boolean sortArray(int[] sortMe) {
        int i;
        int j;
        boolean same;
        i = 0;
        j = 1;

        while(i <= sortMe.length - 1) { // CLE
            while(sortMe.length - 2 >= j) { // CGE
                if(sortMe[j - 1] > sortMe[j]) { // CGT
                    int temp; // NBD
                    temp = sortMe[j - 1];
                    sortMe[j - 1] = sortMe[j];
                    sortMe[j] = temp; // TODO This gives type error since temp type is null
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
}
