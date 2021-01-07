package internals;

import static utils.Reflection.getInt;

public class RSHealthbarInfo extends RSInternal {
    public static final String CLASS_NAME = "HeadbarUpdate";

    public RSHealthbarInfo(Object ref) {
        super(ref);
    }

    public int health() {
        return getInt(CLASS_NAME, "health2", ref);
    }

    public int healthRatio() {
        return getInt(CLASS_NAME, "health", ref);
    }
}
