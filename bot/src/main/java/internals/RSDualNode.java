package internals;

import static utils.Reflection.getLong;
import static utils.Reflection.getRef;

public class RSDualNode extends RSNode {

    public static final String CLASS_NAME = "DualNode";

    public RSDualNode(Object ref) {
        super(ref);
    }

    @Override
    public RSNode next() {
        Object res = getRef(CLASS_NAME, "nextDual", ref);
        return res != null ? new RSNode(res) : null;
    }

    @Override
    public RSNode previous() {
        Object res = getRef(CLASS_NAME, "previousDual", ref);
        return res != null ? new RSNode(res) : null;
    }

    @Override
    public long key() {
        return getLong(CLASS_NAME, "keyDual", ref);
    }
}
