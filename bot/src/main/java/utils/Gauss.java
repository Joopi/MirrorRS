package utils;

public class Gauss {

    public static final int MAX_TRIES = 1000;

    //generates a value following Gaussian distribution.
    //it does not guarantee that the return value is within any range or even close to "mean". Instead, anyone using this function should enforce that.
    public static double random(double mean, double dev) {
        double len = dev * StrictMath.sqrt(-2 * StrictMath.log(StrictMath.random()));
        return (mean + len * StrictMath.cos(2 * StrictMath.PI * StrictMath.random()));
    }

    public static double constrain(double low, double high, double mean, double dev, int attempts) {
        for (int i = 1; i < attempts; i++) {
            double value = random(mean, dev);
            if (value >= low && value < high)
                return value;
        }

        System.out.println("Falling back on uniform distribution after " + attempts + " attempts.");
        return low + (high - low) * Math.random();
    }

    public static double constrain(double low, double high, double mean, double dev) {
        return constrain(low, high, mean, dev, MAX_TRIES);
    }

}
