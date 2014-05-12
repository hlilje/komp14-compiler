class CompareObj {
    public static void main(String[] args) {
        Abc a;
        Abc b;

        a = new Abc();
        b = new Abc();

        if(a == a) {
            System.out.println(true);
        }
        if(a != b) {
            System.out.println(true);
        }

    }
}

class Abc {
    int i;
}
