package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate {
    /**
     * @MethodType boolean-query
     */
    @Override
    public boolean equals(Object other) {
        if(other instanceof Coordinate) {
            return isEqual((Coordinate) other);
        } else {
            return false;
        }
    }

    /**
     * @MethodType boolean-query
     * @MethodProperties template
     * (contains the common code of coordinateImplementations.isEqual())
     */
    @Override
    public boolean isEqual(Coordinate other) {
        if(other == null) {
            return false;
        }
        if(other == this) {
            return true;
        }
        return doIsEqual(other);
    }

    /**
     * @MethodType boolean-query
     * @MethodProperties hook
     */
    abstract boolean doIsEqual(Coordinate other);

    /**
     * Tests if two double values are equal within a certain tolerance.
     * @return true if both values are equal within an absolute tolerance of 1E-10
     *         or (for non-zero values only) equal within a relative tolerance of 1E-10,
     *         else false
     * @MethodType helper
     * @MethodProperties default-value
     */
    // helper methods must be package visible in order to be accessible for test suites
    static boolean isAlmostEqual(double expected, double actual) {
        return isAlmostEqual(expected, actual, Coordinate.equalityAbsTolerance, Coordinate.equalityRelTolerance);
    }

    /**
     * Tests if two double values are equal within a certain absolute and relative tolerance.
     * @return true if both values are equal within the given absolute tolerance
     *         or (for non-zero values only) equal within the given relative tolerance,
     *         else false
     * @MethodType helper
     */
    static boolean isAlmostEqual(double expected, double actual, double absTolerance, double relTolerance) {
        if(isIllegalValue(expected) ||
                isIllegalValue(actual) ||
                isIllegalValue(absTolerance) ||
                isIllegalValue(relTolerance)) {
            throw new IllegalArgumentException();
        }
        return isWithinAbsoluteTolerance(expected, actual, absTolerance) ||
                (expected != 0.0 && actual != 0.0 && isWithinRelativeTolerance(expected, actual, relTolerance));
    }

    /**
     * @MethodType helper
     */
    static boolean isWithinAbsoluteTolerance(double expected, double actual, double absTolerance) {
        boolean result = Math.abs(expected - actual) <= Math.abs(absTolerance);
        return result;
    }

    /**
     * @MethodType helper
     */
    static boolean isWithinRelativeTolerance(double expected, double actual, double relTolerance) {
        boolean result = Math.abs(expected - actual) <= Math.abs(relTolerance * expected);
        return result;
    }

    /**
     * Normalizes an angle by adding or subtracting multiples of (angleMax - angleMin)
     * such that angle is in the range from angleMin (inclusive) to angleMax (exclusive).
     * @param angle the angle to be normalized
     * @param angleMin start of the range
     * @param angleMax end of the range; must be greater than angleMin
     * @return the normalized angle
     * @MethodType helper
     */
    static double normalizeAngle(double angle, double angleMin, double angleMax) {
        if(angleMax <= angleMin) {
            throw new IllegalArgumentException();
        }
        double intervalLength = Math.abs(angleMax - angleMin);
        if(angle < angleMin) {
            double factor = Math.floor(Math.abs((angleMax - angle) / intervalLength));
            angle += factor * intervalLength;
        } else if(angle >= angleMax) {
            double factor = Math.floor(Math.abs((angle - angleMin) / intervalLength));
            angle -= factor * intervalLength;
        }
        return angle;
    }

    /**
     * Checks for illegal double values.
     * @param v value to check
     * @return true if v is NaN or (pos./neg.) infinity, false otherwise
     * @MethodType helper
     */
    static boolean isIllegalValue(double v) {
        return ! Double.isFinite(v);
    }
}
