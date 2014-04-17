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
    public boolean start() {
        return true;
    }
}
