package internals;

import java.util.Arrays;
import java.util.stream.Stream;

import static utils.Reflection.getInt;
import static utils.Reflection.getRef;

public class RSTile extends RSInternal {

    public static final String CLASS_NAME = "Tile";

    public RSTile(Object ref) {
        super(ref);
    }

    public Stream<RSGameObject> gameObjects() {
        return Arrays.stream(gameObjectRefArray())
                .map(obj -> obj != null ? new RSGameObject(obj) : null);
    }

    public Object[] gameObjectRefArray() {
        return (Object[]) getRef(CLASS_NAME, "scenery", ref);
    }

    public int gameObjectsCount() {
        return getInt(CLASS_NAME, "sceneryCount", ref);
    }

    public RSGameObject gameObject() {
        Object[] gameObjects = gameObjectRefArray();
        return new RSGameObject(gameObjects.length > 0 ? gameObjects[0] : null);
    }

    public RSBoundaryObject boundaryObject() {
        return new RSBoundaryObject(getRef(CLASS_NAME, "wall", ref));
    }

    public RSWallDecoration wallDecoration() {
        return new RSWallDecoration(getRef(CLASS_NAME, "wallDecoration", ref));
    }

    public RSFloorDecoration floorDecoration() {
        return new RSFloorDecoration(getRef(CLASS_NAME, "floorDecoration", ref));
    }
}
