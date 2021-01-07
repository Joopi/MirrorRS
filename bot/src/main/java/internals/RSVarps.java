package internals;

import static utils.Reflection.getInt;
import static utils.Reflection.getIntArray;

public class RSVarps extends RSInternal {

    public RSVarps(Object ref) {
        super(ref);
    }

    public static int[] varpsMain() {
        return getIntArray(RSClient.CLASS_NAME, "Varps_main", null);
    }

    public static int[] varpsMasks() {
        return getIntArray(RSClient.CLASS_NAME, "Varps_masks", null);
    }

    public static int[] varpsTemp() {
        return getIntArray(RSClient.CLASS_NAME, "Varps_temp", null);
    }

    public int transformVarp() {
        return getInt(RSClient.CLASS_NAME, "transformVarp", ref);
    }

    public int transformVarbit() {
        return getInt(RSClient.CLASS_NAME, "transformVarbit", ref);
    }

}
