package api.util;

import utils.Gauss;

import java.util.function.BooleanSupplier;


public class Sleep {

    public static double INTERVAL_DEVIATION = 0.25D;

    public static void duration(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //uniform random distribution
    //bottom inclusive, top exclusive
    public static void range(int from, int to) {
        duration(from + (int) ((to - from) * Math.random()));
    }

    //gaussian distribution
    //inclusive, exclusive
    public static void range(int from, int to, double mean, double dev) {
        duration((int) Gauss.constrain((double) from, (double) to, mean, dev));
    }

    public static boolean until(BooleanSupplier supplier, int interval, long max) {
        long maxMS = System.currentTimeMillis() + max;
        do {
            if (supplier.getAsBoolean())
                return true;

            Sleep.duration(interval + (int) (interval * INTERVAL_DEVIATION * Math.random()));
        } while (System.currentTimeMillis() < maxMS);

        return false;
    }

}
