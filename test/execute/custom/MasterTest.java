/* TODO This is a valid test which contains all the extensions in our grammar */
// EXT:CLE
// EXT:CGT
// EXT:CGE
// EXT:CEQ
// EXT:CNE
// EXT:BDJ
// EXT:NBD

class Master {
    public static void main(String[] args) {
        boolean victory;
        victory = new Test().begin();
        System.out.println(victory);
    }
}

class Test {
    public boolean begin() {
        return true;
    }

    public int before() {
        return this.after(); // Valid call before declaration
    }

    public int after() {
        return 0;
    }
}
