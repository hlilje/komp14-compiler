class AndEval {
	public static void main(String[] args) {
        Foo bar;
        bar = new Foo();
        if( false && bar.print() ) { // bar.print() shouldn't be evaluated
            System.out.println(666); // should not be printed
        } else {
    		System.out.println(42); // should be printed
        }
	}
}

class Foo {
    public boolean print() {
        System.out.println(666); // should not be printed
        return true;
    }
}
