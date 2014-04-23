/* This test is supposed to figure out why F3 passes the compiler check */

class Main {
    public static void main(String[] args) {
        Object o;
        Other a;
        Another b; // Undeclared
        o = new Object();
        o = new Other(); // Wrong type?
        o = new What(); // TODO Give error
        System.out.println(0);
        System.out.println(new Tester().test());
        System.out.println(new Tester().nonExistent()); // TODO Give error
    }
}

class Other {}

class Tester {
    public boolean test() {
        int a;
        a = this.otherMethod(); // TODO Give error
        return false;
    }
}
