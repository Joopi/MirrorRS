package internals;

import static utils.Reflection.getRef;

public class RSBoundaryObject extends RSTileObject {

    public static final String CLASS_NAME = "Wall";

    public RSBoundaryObject(Object ref) {
        super(ref, CLASS_NAME);
    }

    @Override
    public RSRenderable renderable() {
        Object entity = getRef(CLASS_NAME, "entity1", ref);
        return new RSRenderable(entity != null ? entity : getRef(CLASS_NAME, "entity2", ref));
    }
}
