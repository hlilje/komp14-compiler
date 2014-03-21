class Main
{
    public static void main(String[] a){
        System.out.println(new Test().start());
    }
}

class Test {
    int a;
    boolean b;
    Other c;

    public int start() {
        return 1;
    }

    public boolean starter() {
        return false;
    }
}

class Test2 {
    int x;
    public int v() {
        return 1;
    }
    public int v() {
        return 1;
    }
}

class Other {
    int a;
    boolean a;

    public Other v() {
        //return new Other;
        return false;
    }
}
