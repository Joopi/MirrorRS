package internals;

import static utils.Reflection.*;

public class RSPlayerInfo extends RSInternal {

    public static final String CLASS_NAME = "PlayerAppearance";

    public RSPlayerInfo(Object ref) {
        super(ref);
    }

    public static RSCache modelCache() {
        Object cache = getRef(RSClient.CLASS_NAME, "PlayerAppearance_cachedModels", null);
        return cache != null ? new RSCache(cache) : null;
    }

    public RSModel model() {
        int transformId = npcTransformID();
        if (transformId > -1) {
            RSNPCInfo info = RSNPCInfo.cachedInfo((long) transformId);
            return info.model();
        }

        RSCache cache = modelCache();
        if (cache != null) {
            RSNode node = cache.get(modelId());
            if (node != null && node.ref != null)
                return new RSModel(node.ref);
        }
        return null;
    }

    public long modelId() {
        return getLong(CLASS_NAME, "modelId", ref);
    }

    public int[] equipment() {
        return getIntArray(CLASS_NAME, "equipment", ref);
    }

    public int[] bodyColors() {
        return getIntArray(CLASS_NAME, "bodyColors", ref);
    }

    public int npcTransformID() {
        return getInt(CLASS_NAME, "npcTransformId", ref);
    }

    public boolean isFemale() {
        return getBoolean(CLASS_NAME, "isFemale", ref);
    }
}
