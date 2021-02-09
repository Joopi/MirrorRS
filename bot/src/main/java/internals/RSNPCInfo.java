package internals;

import api.Varbit;

import java.util.Arrays;
import java.util.stream.Stream;

import static utils.Reflection.*;

public class RSNPCInfo extends RSInternal {

    public static final String CLASS_NAME = "NPCType";

    public RSNPCInfo(Object ref) {
        super(ref);
    }

    public static RSNPCInfo cachedInfo(long ID) {
        RSNode node = RSClient.NPCInfoCache().get(ID);
        return node != null ? new RSNPCInfo(node.ref) : null;
    }

    public RSNPCInfo transform() {
        int[] transforms = transforms();

        if (transforms.length > 0) {
            int transformVarbit = transformVarbit();

            int idx = (transformVarbit != -1) ? Varbit.get(transformVarbit) : RSVarps.varpsMain()[transformVar()];
            int ID = (idx >= 0 && idx < transforms.length) ? transforms[idx] : transforms[transforms.length - 1];
            return cachedInfo(ID);
        }
        return this;
    }

    public RSModel model() {
        RSNode node = RSClient.NPCModelCache().get((long) ID());
        return (node != null && node.ref != null) ? new RSModel(node.ref) : null;
    }

    public Stream<String> actions() {
        return Arrays.stream(actionRefArray())
                .map(action -> action != null ? (String) action : "");
    }

    public int modelTileSize() {
        return getInt(CLASS_NAME, "size", ref);
    }

    public int scaleWidth() {
        return getInt(CLASS_NAME, "resizeh", ref);
    }

    public int scaleHeight() {
        return getInt(CLASS_NAME, "resizev", ref);
    }

    protected int[] transforms() {
        return getIntArray(CLASS_NAME, "transforms", ref);
    }

    protected int transformVar() {
        return getInt(CLASS_NAME, "transformVarp", ref);
    }

    protected int transformVarbit() {
        return getInt(CLASS_NAME, "transformVarbit", ref);
    }

    public int ID() {
        return getInt(CLASS_NAME, "id", ref);
    }

    public int combatLevel() {
        return getInt(CLASS_NAME, "combatLevel", ref);
    }

    public String name() {
        return getString(CLASS_NAME, "name", ref);
    }

    public boolean isInteractable() {
        return getBoolean(CLASS_NAME, "isInteractable", ref);
    }

    public boolean isFollower() {
        return getBoolean(CLASS_NAME, "isFollower", ref);
    }

    protected Object[] actionRefArray() {
        return getRefArray(CLASS_NAME, "op", ref);
    }

    protected int[] headArray() {
        return getIntArray(CLASS_NAME, "head", ref);
    }

}
