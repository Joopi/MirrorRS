package internals;

import static utils.Reflection.getString;

public class RSUsernameProvider extends RSInternal {

    public static final String CLASS_NAME = "Username";

    public RSUsernameProvider(Object ref) {
        super(ref);
    }

    public String name() {
        return getString(CLASS_NAME, "cleanName", ref);
    }
}
