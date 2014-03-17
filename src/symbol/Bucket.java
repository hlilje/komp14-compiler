package symbol;

public class Bucket {
    public String key;
    public Object binding;
    public Bucket next;

    public Bucket(String k, Object b, Bucket n) {
        key = k;
        binding = b;
        next = n;
    }
}
