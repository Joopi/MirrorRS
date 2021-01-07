package internals;

import static utils.Reflection.getRef;

public class RSNodeDeque extends RSInternal {

    public static final String CLASS_NAME = "NodeDeque";

    public RSNodeDeque(Object ref) {
        super(ref);
    }

    public RSNode head() {
        Object head = getRef(CLASS_NAME, "sentinel", ref);
        return head != null ? new RSNode(head) : null;
    }

    public RSNode current() {
        return new RSNode(getRef(CLASS_NAME, "current", ref));
    }
}
