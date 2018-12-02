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
     * @throws IllegalArgumentException if any argument is not a finite double value
     * @return true if both values are equal within the given absolute tolerance
     *         or (for non-zero values only) equal within the given relative tolerance,
     *         else false
     * @MethodType helper
     */
    static boolean isAlmostEqual(double expected, double actual, double absTolerance, double relTolerance) {
        assertArgIsFinite(expected);
        assertArgIsFinite(actual);
        assertArgIsFinite(absTolerance);
        assertArgIsFinite(relTolerance);
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
     * @throws IllegalArgumentException if not angleMin < angleMax
     * @throws IllegalArgumentException if any argument is not a finite double value
     * @return the normalized angle
     * @MethodType helper
     */
    static double normalizeAngle(double angle, double angleMin, double angleMax) {
        // those 4 asserts could be dropped - the only user is the SphericCoordinate constructor,
        // which already does these checks
        assertTrue(angleMin < angleMax, IllegalArgumentException.class);
        assertArgIsFinite(angle);
        assertArgIsFinite(angleMin);
        assertArgIsFinite(angleMax);
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
     * Checks for illegal double values which were given as parameters.
     * @param v value to check
     * @throws IllegalArgumentException if v is NaN or (pos./neg.) infinity
     * @MethodType assertion
     */
    static void assertArgIsFinite(double v) {
        if(! Double.isFinite(v)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Generic assertion helper method for boolean expressions that throws a specified exception.
     * @param condition the boolean expression to check
     * @param exceptionClass the exception type to throw if condition is false
     * @throws E if condition evaluates to false
     * @throws RuntimeException if E cannot be instantiated
     * @MethodType assertion
     */
    // not possible to instantiate E with a message
    static <E extends Exception> void assertTrue(boolean condition, Class<E> exceptionClass) throws E {
        if(! condition) {
            E exception;
            try {
                exception = exceptionClass.newInstance();
            } catch(Exception error) {
                throw new RuntimeException("exception while trying to instantiate an exception of type " +
                        exceptionClass.getName(), error);
            }
            throw exception;
        }
    }
}
