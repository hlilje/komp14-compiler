package Symbol;

public class Bucket extends Binding {
    public String key;
    public Object binding;
    public Bucket next;

    public Bucket(String k, Object b, Bucket n) {
        key=k; binding=b; next=n;
    }
}
