package symbol;

public class HashT {
    final int SIZE = 256;
    private Bucket table[] = new Bucket[SIZE];

    private int hash(String s) {
        int h = 0;
        for(int i=0; i<s.length(); i++) {
            h = h * 65599 + s.charAt(i);
        }
        System.out.println("    HASH: " + h); // DEBUG
        return h < 0 ? -h : h; // Avoid negative values form int overflow
    }

    // Changed from Binding b, possible error in Appel book
    public void insert(String s, Object b) {
        int index = hash(s) % SIZE;
        //System.out.println("    INDEX FROM HASH: " + index); // DEBUG
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
