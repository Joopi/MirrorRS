package internals;

import static utils.Reflection.getInt;
import static utils.Reflection.instanceOf;

public class RSRenderable extends RSInternal {

    public static final String CLASS_NAME = "Entity";

    public RSRenderable(Object ref) {
        super(ref);
    }

    public int height() {
        return getInt(CLASS_NAME, "height", ref);
    }

    public boolean isAnimableNode() {
        return (instanceOf(ref, RSAnimableNode.CLASS_NAME));
    }

    public boolean isGraphicsObject() {
        return (instanceOf(ref, RSGraphicsObject.CLASS_NAME));
    }

    public boolean isActor() {
        return (instanceOf(ref, RSActor.CLASS_NAME));
    }

    public boolean isModel() {
        return (instanceOf(ref, RSModel.CLASS_NAME));
    }

    //TODO
    public boolean isObj() {
        return (instanceOf(ref, "Obj"));
    }

    public boolean isUnlitModel() {
        return (instanceOf(ref, "UnlitModel"));
    }

    public boolean isProjectile() {
        return (instanceOf(ref, "Projectile"));
    }
}
