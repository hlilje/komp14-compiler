/**
 * Main class decl
 * //Line comment here should be ok.
 */
class Main {
	public static void main(String[] args) {
		TreeSet t;

		// new TreeSet(0, null)
		t = new TreeSet().init(0, new TreeSet());

		t = t.insert(0 - 1);
		t = t.insert(1);

		if (t.contains(1)) {
			System.out.println(1);
		} else {
			System.out.println(0);
		}
	}
}

/*
	TreeSet
*/
class TreeSet {
	int value;

	TreeSet nil;
	TreeSet left;
	TreeSet right;

	// This is basically a constructor.
	public TreeSet init(int v, TreeSet nul) {
		value = v;

		nil = nul;
		left = nul;
		right = nul;

		return this;
	}

	public TreeSet insert(int v) {
		TreeSet _;

		if (v < value) {
			_ = left.insert(v);
		} else if (value < v) {
			_ = right.insert(v);
		} else {}

		return this;
	}

	public boolean contains(int v) {
		boolean val;

		if (v < value) {

			// if (left == nil) {
				val = false;
			// } else {
				val = left.contains(v);
			// }

		} else if (value < v) {

			// if (right == nil) {
				val = false;
			// } else {
				val = right.contains(v);
			// }

		} else {
			val = true;
		}

		return val;
	}
}
