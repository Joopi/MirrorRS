package internals;

import static utils.Reflection.getRef;

public class RSCombatInfo extends RSInternal {

    public static final String CLASS_NAME = "Headbar";

    public RSCombatInfo(Object ref) {
        super(ref);
    }

    public RSCombatInfoCache updates() {
        Object headbar = getRef(CLASS_NAME, "updates", ref);
        return headbar != null ? new RSCombatInfoCache(headbar) : null;
    }
}
