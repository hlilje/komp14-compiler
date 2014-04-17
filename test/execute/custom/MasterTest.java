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
        boolean done;
        done = new Useful().start();

        System.out.println(1);
        System.out.println(2);
        System.out.println(3);
        System.out.println(done);
    }
}

class Useful {
    int[] sortMe;

    public boolean start() {
        sortMe = int[10];
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

        this.sortArray(sortMe);
    }

    public boolean sortArray(int[] sortMe) {
        int i;
        int prev;
        boolean cont;
        i = 0;
        cont = false;

        while(i < sortMe.length || cont) {
            int temp;
            temp = sortMe[i];

            if(sortMe[i] < temp) {

            } else {
            }
            
            i = i + i;

            prev = sortMe[i];
        }

        return true;
    }

    public boolean numberOfPrimes(int[] array) {
    }
}
