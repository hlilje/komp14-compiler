/** 
 * This test is supposed to figure out why F3 passes the compiler check.
 * 4 errors
 */

class Main {
    public static void main(String[] args) {
        Object o;
        Other a;
        Another b; // Undeclared
        o = new Object();
        o = new Other(); // Wrong type?
        o = new What(); // Error
        System.out.println(0);
        System.out.println(new Tester().test());
        System.out.println(new Tester().nonExistent());
        System.out.println(new Tester().nonExistent().anotherCall());
    }
}

class Other {}

class Tester {
    public boolean test() {
        int a;
        a = this.otherMethod(); // Error
        return false;
    }
}
