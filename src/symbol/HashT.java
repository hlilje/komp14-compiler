package symbol;

public class HashT {
    public static final boolean DEBUG = false;

    final int SIZE = 128;
    private Bucket table[] = new Bucket[SIZE];

    private int hash(String s) {
        int h = 0;
        for(int i=0; i<s.length(); i++) {
            h = h * 65599 + s.charAt(i);
        }
        return h < 0 ? -h : h; // Avoid negative values from int overflow
    }

    // Changed from Binding b, possible error in Appel book
    public void insert(String s, Object b) {
        int index = hash(s) % SIZE;
        if(DEBUG) System.out.println("      INDEX FROM HASH: " + index);
        table[index] = new Bucket(s,b,table[index]);
    }

    public Object lookup(String s) {
        int index = hash(s) % SIZE;
        // Changed from Binding b, possible error in Appel book
        for(Bucket b=table[index]; b!=null; b=b.next)
            if(s.equals(b.key)) return b.binding;
        return null;
    }

    public void pop(String s) {
        int index=hash(s) % SIZE;
        table[index] = table[index].next;
    }
}
