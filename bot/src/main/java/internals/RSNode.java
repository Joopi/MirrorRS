package internals;

import static utils.Reflection.getLong;
import static utils.Reflection.getRef;

public class RSNode extends RSInternal {

    public static final String CLASS_NAME = "Node";

    public RSNode(Object ref) {
        super(ref);
    }

    public RSNode next() {
        Object res = getRef(CLASS_NAME, "next", ref);
        return res != null ? new RSNode(res) : null;
    }

    public RSNode previous() {
        Object res = getRef(CLASS_NAME, "previous", ref);
        return res != null ? new RSNode(res) : null;
    }

    public long key() {
        return getLong(CLASS_NAME, "key", ref);
    }
}
