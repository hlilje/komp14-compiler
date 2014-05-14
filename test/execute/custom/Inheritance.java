class Inheritance {
    public static void main(String[] args) {
        Asd a;
        Asdf b;
        Asd c;
        Asd d;

        a = new Asd();
        b = new Asdf();
        c = new Asdf();
        d = new Asdfg();

        System.out.println(a.getInt());
        System.out.println(b.getSubInt());
        System.out.println(b.getInt());
        //System.out.println(c.getSuperInt()); // doesn't work as it shouldn't
        System.out.println(b.getSuperInt());
        System.out.println(b.getSumInts());
    }
}

class Asd {
    int i;

    public int getInt() {
        i = 42;
        return i;
    }
}

class Asdf extends Asd {
    int j;

    public int getSubInt() {
        j = 7;
        return j;
    }

    public int getSuperInt() {
        i = 105;
        return i;
    }

    public int getSumInts() {
        return i + j;
    }
}

class Asdfg extends Asdf {
    
}
