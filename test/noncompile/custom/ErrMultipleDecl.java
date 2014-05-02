/* Multiple declarations of variables and classes */

class Main
{
    public static void main(String[] a){
        System.out.println(new Test().start());
    }
}

class Test {
    int a;
    int a;
    int a;
    Other b;
    Other a;

    public int start() {
        a = 17;

        if(false) {
            a = 2;
        } else {
            a = 12;
        }

        return a;
    }
}

class Test {
    int x;
    public int v() {
        return 1;
    }
    public int v() {
        return 1;
    }
}
