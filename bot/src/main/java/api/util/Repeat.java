package api.util;

import java.util.function.BooleanSupplier;

public class Repeat {
    public static double INTERVAL_DEVIATION = 0.25D;

    public static boolean attempts(BooleanSupplier supplier, int interval, int maxAttempts) {
        for (int i = 0; i < maxAttempts; i++) {
            if (supplier.getAsBoolean())
                return true;

            Sleep.duration(interval + (int) (interval * INTERVAL_DEVIATION * Math.random()));
        }
        return false;
    }

    public static boolean attempts(BooleanSupplier condition, BooleanSupplier operation, int interval, int maxAttempts) {
        for (int i = 0; i < maxAttempts; i++) {
            if (!condition.getAsBoolean())
                return false;

            if (operation.getAsBoolean())
                return true;

            Sleep.duration(interval + (int) (interval * INTERVAL_DEVIATION * Math.random()));
        }
        return false;
    }
}
