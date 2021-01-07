package internals;

import static utils.Reflection.*;

public class RSProjectile extends RSDualNode {

    public static final String CLASS_NAME = "Projectile";

    public RSProjectile(Object ref) {
        super(ref);
    }

    public int id() {
        return getInt(CLASS_NAME, "id", ref);
    }

    public int targetIndex() {
        return getInt(CLASS_NAME, "targetIndex", ref);
    }

    public boolean isMoving() {
        return getBoolean(CLASS_NAME, "isMoving", ref);
    }

    public int cycleStart() {
        return getInt(CLASS_NAME, "cycleStart", ref);
    }

    public int cycleEnd() {
        return getInt(CLASS_NAME, "cycleEnd", ref);
    }

    public double x() {
        return getInt(CLASS_NAME, "x", ref);
    }

    public double y() {
        return getInt(CLASS_NAME, "y", ref);
    }

    public double z() {
        return getInt(CLASS_NAME, "z", ref);
    }

    public int plane() {
        return getInt(CLASS_NAME, "plane", ref);
    }

    public int pitch() {
        return getInt(CLASS_NAME, "pitch", ref);
    }

    public int yaw() {
        return getInt(CLASS_NAME, "yaw", ref);
    }

    public int sourceX() {
        return getInt(CLASS_NAME, "sourceX", ref);
    }

    public int sourceY() {
        return getInt(CLASS_NAME, "sourceY", ref);
    }

    public int sourceZ() {
        return getInt(CLASS_NAME, "sourceZ", ref);
    }

    public double speedX() {
        return getInt(CLASS_NAME, "speedX", ref);
    }

    public double speedY() {
        return getInt(CLASS_NAME, "speedY", ref);
    }

    public double speedZ() {
        return getInt(CLASS_NAME, "speedZ", ref);
    }

    public double accelerationZ() {
        return getInt(CLASS_NAME, "accelerationZ", ref);
    }

    public RSSequenceInfo sequenceInfo() {
        return new RSSequenceInfo(getRef(CLASS_NAME, "seqType", ref));
    }
}
