package internals;

import static utils.Reflection.getInt;
import static utils.Reflection.getRef;

public class RSVarbitInfo extends RSDualNode {

    public static final String CLASS_NAME = "VarBitType";

    public RSVarbitInfo(Object ref) {
        super(ref);
    }

    protected static RSCache varbitInfoCache() {
        Object cacheRef = getRef(RSClient.CLASS_NAME, "VarBitType_cached", null);
        return cacheRef != null ? new RSCache(cacheRef) : null;
    }

    public static RSVarbitInfo info(long ID) {
        RSCache cache = varbitInfoCache();
        RSNode node = null;
        if (cache != null)
            node = cache.get(ID);

        return node != null ? new RSVarbitInfo(node.ref) : null;
    }

    public int baseVar() {
        return getInt(CLASS_NAME, "baseVar", ref);
    }

    public int startBit() {
        return getInt(CLASS_NAME, "startBit", ref);
    }

    public int endBit() {
        return getInt(CLASS_NAME, "endBit", ref);
    }

}
