package internals;

import static utils.Reflection.*;

public class RSComponentParent extends RSNode {

    public static final String CLASS_NAME = "InterfaceParent";

    public RSComponentParent(Object ref) {
        super(ref);
    }

    public int groupId() {
        return getInt(CLASS_NAME, "itf", ref);
    }

    public int type() {
        return getInt(CLASS_NAME, "type", ref);
    }
}
