/**
 * TODO This is a valid test which executes with the standard grammar
 * and extensions.
 */

class Main {
    public static void main(String[] args) {
        System.out.println(new Standard().start());
    }
}

class Standard {
    public boolean start() {
        return true;
    }

    public boolean findPrimes() {
        return false;
    }
}
