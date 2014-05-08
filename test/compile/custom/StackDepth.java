class StackDepth {
    public static void main(String[] a) {
        Tester t;
        int i;
        int j;
        int k;
        boolean b;
        boolean c;
        i = 5;
        j = 7;
        k = 3;
        b = true;
        c = false;

        System.out.println(i + j + k);
        System.out.println(b);
        i = k + 2;
        j = i - k + 5;
        System.out.println(2 + i);

        t = new Tester();
        i = t.getInt();
        System.out.println(i);
        i = t.getAnotherInt();
        System.out.println(i);
        i = t.getIntPrint();
        i = t.getAnotherIntPrint();

        System.out.println(t.getBiggerInt());
    }
}

class Tester {
    int i;
    boolean b;

    public int getInt() {
        i = 25;
        return i;
    }

    public int getAnotherInt() {
        int i;
        i = 35;
        return i;
    }

    public int getIntPrint() {
        i = 25;
        System.out.println(i);
        return i;
    }

    public int getAnotherIntPrint() {
        int i;
        i = 35;
        System.out.println(i);
        return i;
    }

    public int getBiggerInt() {
        return 1234;
    }

    public int add25(int x) {
        i = 25;
        return i + x;
    }

    public int addInts(int x, int y) {
        return x + y;
    }
}
