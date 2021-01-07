package internals;

import types.tile.LocalTile;

import static utils.Reflection.getInt;
import static utils.Reflection.getIntArray;

public class RSSequenceInfo extends RSNode {
    public static final String CLASS_NAME = "SeqType";

    public RSSequenceInfo(Object ref) {
        super(ref);
    }

    public static RSCache sequenceInfoCache() {
        return RSClient.sequenceInfoCache();
    }

    public int frameCount() {
        return getInt(CLASS_NAME, "frameCount", ref);
    }

    public int[] frameIDs() {
        return getIntArray(CLASS_NAME, "frameIds", ref);
    }

    public int[] frameIDs2() {
        return getIntArray(CLASS_NAME, "frameIds2", ref);
    }

    public int[] frameLengths() {
        return getIntArray(CLASS_NAME, "frameLengths", ref);
    }

    public int leftHand() {
        return getInt(CLASS_NAME, "lefthand", ref);
    }

    public int rightHand() {
        return getInt(CLASS_NAME, "righthand", ref);
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
