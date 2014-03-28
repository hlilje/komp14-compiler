/* TODO This is a valid test which executes with the standard grammar and extensions */

class Test {
    public static void main(String[] args) {
        boolean dummy;
        dummy = new Test2().start();
        System.out.println(dummy);
    }
}

class Test2 {
    public boolean start() {
        return true;
    }
}
