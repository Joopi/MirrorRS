package internals;

import api.Varbit;

import static utils.Reflection.*;

public class RSObjectInfo extends RSInternal {

    public static final String CLASS_NAME = "LocType";

    public RSObjectInfo(Object ref) {
        super(ref);
    }

    public static RSObjectInfo info(long ID) {
        Object infoRef = RSClient.objInfoCache().get(ID);
        return infoRef != null ? new RSObjectInfo(infoRef) : null;
    }

    public RSModel model(long ID) {
        RSNode node = RSClient.objModelCache().get(ID);
        return node != null ? new RSModel(node.ref) : null;
    }

    public int ID() {
        return getInt(CLASS_NAME, "id", ref);
    }

    public String name() {
        return getString(CLASS_NAME, "name", ref);
    }

    public int defaultAnimationID() {
        return getInt(CLASS_NAME, "animationId", ref);
    }

    public int[] modelIDs() {
        return getIntArray(CLASS_NAME, "models", ref);
    }

    public int[] models() {
        return getIntArray(CLASS_NAME, "modelTypes", ref);
    }

    public RSObjectInfo transform() {
        int[] transformations = transformations();
        if (transformations.length > 0) {
            int transformVarbit = transformVarbit();

            int idx = (transformVarbit != -1) ? Varbit.getVarbit(transformVarbit) : RSVarps.varpsMain()[transformVar()];
            int ID = (idx >= 0 && idx < transformations.length) ? transformations[idx] : transformations[transformations.length - 1];
            return info(ID);
        }
        return this;
    }

    public int[] transformations() {
        return getIntArray(CLASS_NAME, "multi", ref);
    }

    public int transformVar() {
        return getInt(CLASS_NAME, "multiVar", ref);
    }

    public int transformVarbit() {
        return getInt(CLASS_NAME, "multiVarbit", ref);
    }
}
