package internals;

import static utils.Reflection.getRef;

public class RSCombatInfoCache extends RSInternal {

    public static final String CLASS_NAME = "IterableNodeDeque";

    public RSCombatInfoCache(Object ref) {
        super(ref);
    }

    public RSNode current() {
        return new RSNode(getRef(CLASS_NAME, "current", ref));
    }

    public RSNode head() {
        return new RSNode(getRef(CLASS_NAME, "sentinel", ref));
    }
}
