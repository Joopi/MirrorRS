package internals;

import static utils.Reflection.getIntArray;

public class RSItemContainer extends RSInternal {

    public static final String CLASS_NAME = "Inventory";

    public RSItemContainer(Object ref) {
        super(ref);
    }

    public int[] IDs() {
        return getIntArray(CLASS_NAME, "ids", ref);
    }

    public int[] quantities() {
        return getIntArray(CLASS_NAME, "quantities", ref);
    }
}
