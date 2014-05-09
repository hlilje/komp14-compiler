class PrintBool {
	public static void main(String[] args) {
		boolean t;
        boolean f;
        t = true;
        f = false;
		System.out.println(t); // Should print "true", not "1"
		System.out.println(f); // Should print "false", not "0"
		System.out.println(true); // Should print "true", not "1"
		System.out.println(false); // Should print "false", not "0"
	}
}
