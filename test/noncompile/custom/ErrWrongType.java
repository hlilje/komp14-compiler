class Main {
    public static void main(String[] args) {
        int a;
        boolean b;
        int c;
        int _d;
        int x;
        Objection obj;

        a = 1;
        x = 2;
        b = true;
        c = a + b;
        obj = new Objection().object();

        if(1 == true) {
            x = 0;
        } else {
            x = 24;
        }

        System.out.println(1);
    }
}

class Objection {
    boolean incorrect;

    public Objection object() {
        incorrect = true;
        return this;
    }
}
