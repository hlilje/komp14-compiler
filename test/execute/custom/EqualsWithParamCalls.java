/* A simple execution test to see if comparison on method calls works. */
// EXT:CEQ
// EXT:CNE
// EXT:IWE

class Main {
	public static void main(String[] args) {
        Test t;
        int i;
        t = new Test();
        i = 0;

        //if(t.retTrue() != t.retFalse()) {
            //System.out.println(1);
        //}

        if(t.retTrueParam(i) == t.retFalseParam(0)) {
            System.out.println(0);
        }
	}
}

class Test {
    public boolean retTrue() {
        return true;
    }

    public boolean retTrueParam(int p) {
        return true;
    }

    public boolean retFalse() {
        return false;
    }

    public boolean retFalseParam(int p) {
        return false;
    }
}
