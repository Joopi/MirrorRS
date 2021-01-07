package internals;

public class RSInternal {
    public Object ref;

    public RSInternal(Object ref) throws NullPointerException {
        if (ref == null)
            throw new NullPointerException("Ref is null");

        this.ref = ref;
    }
}
