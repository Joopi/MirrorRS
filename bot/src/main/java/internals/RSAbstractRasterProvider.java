package internals;

import static utils.Reflection.getInt;
import static utils.Reflection.getIntArray;

public class RSAbstractRasterProvider extends RSInternal {
    public static final String CLASS_NAME = "AbstractRasterProvider";

    public RSAbstractRasterProvider(Object ref) {
        super(ref);
    }

    public int[] pixels() {
        return getIntArray(CLASS_NAME, "pixels", ref);
    }

    public int width() {
        return getInt(CLASS_NAME, "width", ref);
    }

    public int height() {
        return getInt(CLASS_NAME, "height", ref);
    }
}
