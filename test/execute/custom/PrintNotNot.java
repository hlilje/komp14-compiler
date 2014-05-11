class PrintNotNot {
	public static void main(String[] args) {
		System.out.println(!!!!true); // Should print "true", not "false"
		System.out.println(!(!(true))); // true
		System.out.println(!(!!(!(((!((true)))))))); // false
	}
}
