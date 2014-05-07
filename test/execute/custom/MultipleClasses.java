class Main {
    public static void main(String[] args) {
        System.out.println(1);
        System.out.println(new Class1().print());
    }
}

class Class1 {
    public int print() {
        System.out.println(true);
        return 2;
    }
}

class Third {
    public boolean booleanMethod() {
        return false;
    }

    public int intMethod() {
        return 0;
    }
}
