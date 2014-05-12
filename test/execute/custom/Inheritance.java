class Inheritance {
    public static void main(String[] args) {
        Asd a;
        Asdf b;

        a = new Asd();
        b = new Asdf();

        System.out.println(a.getSuperInt());
        System.out.println(b.getInt());
        System.out.println(b.getSuperInt());

    }
}

class Asd {
    int i;

    public int getSuperInt() {
        i = 42;
        return i;
    }
}

class Asdf extends Asd {
    int j;

    public int getInt() {
        j = 7;
        return j;
    }
}
