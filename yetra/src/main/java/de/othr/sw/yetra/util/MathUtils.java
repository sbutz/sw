package de.othr.sw.yetra.util;

import java.util.Random;

public class MathUtils {

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        double coeff = Math.pow(10, places);
        return ((int) (value * coeff)) / coeff;
    }

    public static double random(double lower, double upper) {
        if (lower > upper)
            throw new IllegalArgumentException();

        Random random = new Random();
        double r = random.nextDouble();
        return upper - r * (upper - lower);
    }
}
