package internals;

import types.tile.LocalTile;

import static utils.Reflection.getInt;
import static utils.Reflection.getRef;

public class RSAnimableNode extends RSInternal {

    public static final String CLASS_NAME = "DynamicObject";

    public RSAnimableNode(Object ref) {
        super(ref);
    }

    public RSSequenceInfo sequenceInfo() {
        Object seqRef = getRef(CLASS_NAME, "seqType", ref);
        return seqRef != null ? new RSSequenceInfo(seqRef) : null;
    }

    public int ID() {
        return getInt(CLASS_NAME, "id", ref);
    }

    public int rotation() {
        return getInt(CLASS_NAME, "orientation", ref);
    }

    public int flags() {
        return getInt(CLASS_NAME, "type", ref);
    }

    public int frame() {
        return getInt(CLASS_NAME, "frame", ref);
    }

    public int cycleStart() {
        return getInt(CLASS_NAME, "cycleStart", ref);
    }

    public int localX() {
        return getInt(CLASS_NAME, "x", ref);
    }

    public int localY() {
        return getInt(CLASS_NAME, "y", ref);
    }

    public LocalTile localTile() {
        return new LocalTile(localX(), localY());
    }

}
