package internals;

import static utils.Reflection.getRef;

public class RSWallDecoration extends RSTileObject {

    public static final String CLASS_NAME = "WallDecoration";

    public RSWallDecoration(Object ref) {
        super(ref, CLASS_NAME);
    }

    @Override
    public RSRenderable renderable() {
        Object entityRef = getRef(CLASS_NAME, "entity1", ref);
        return new RSRenderable(entityRef != null ? entityRef : getRef(CLASS_NAME, "entity2", ref));
    }
}
