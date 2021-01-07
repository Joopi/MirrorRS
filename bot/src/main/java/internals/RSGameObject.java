package internals;

import static utils.Reflection.getInt;

public class RSGameObject extends RSTileObject {

    public static final String CLASS_NAME = "Scenery";

    public RSGameObject(Object ref) {
        super(ref, CLASS_NAME);
    }

    @Override
    public int localX() {
        return getInt(CLASS_NAME, "centerX", ref);
    }

    @Override
    public int localY() {
        return getInt(CLASS_NAME, "centerY", ref);
    }
}
