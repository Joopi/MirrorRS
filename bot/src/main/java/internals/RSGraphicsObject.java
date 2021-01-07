package internals;

import static utils.Reflection.getBoolean;
import static utils.Reflection.getInt;

public class RSGraphicsObject extends RSInternal {
    public static final String CLASS_NAME = "GraphicsObject";

    public RSGraphicsObject(Object ref) {
        super(ref);
    }

    public int cycleStart() {
        return getInt(CLASS_NAME, "cycleStart", ref);
    }

    public int frame() {
        return getInt(CLASS_NAME, "frame", ref);
    }

    public int frameCycle() {
        return getInt(CLASS_NAME, "frameCycle", ref);
    }

    public int id() {
        return getInt(CLASS_NAME, "id", ref);
    }

    public boolean isFinished() {
        return getBoolean(CLASS_NAME, "isFinished", ref);
    }
}
