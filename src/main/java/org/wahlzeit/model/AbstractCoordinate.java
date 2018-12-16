package org.wahlzeit.model;

import java.io.StringWriter;
import java.util.logging.Logger;

public abstract class AbstractCoordinate implements Coordinate {
    /**
     * @MethodType boolean-query
     */
    @Override
    public boolean equals(Object other) {
        if(other instanceof Coordinate) {
            boolean result = false; // default answer in case an exception occurs
            try {
                result = isEqual((Coordinate) other);
            } catch (CoordinateError e) {
                // contract violation, but do not escalate (method signature doesn't allow it)
                // but at least log the conversion error in full detail
	            Logger log = Logger.getLogger(AbstractCoordinate.class.getName());
	            java.io.StringWriter sw = new StringWriter();
	            e.printStackTrace(new java.io.PrintWriter(sw));
	            String stacktrace = sw.toString();
	            log.warning(String.format(
	            		"AbstractCoordinate.equals() call failed (" +
					            "this coordinate: %s; " +
					            "other coordinate: %s), " +
					            "stack trace:",
			            this.toString(), other.toString()
	            ) + System.lineSeparator() + stacktrace);
            }
            return result;
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
    public boolean isEqual(Coordinate other) throws CoordinateError {
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
    abstract boolean doIsEqual(Coordinate other) throws CoordinateError;

    /**
     * Tests if two double values are equal within a certain tolerance.
     * @return true if both values are equal within an absolute tolerance of 1E-10
     *         or (for non-zero values only) equal within a relative tolerance of 1E-10,
     *         else false
     * @MethodType helper
     * @MethodProperties default-value
     */
    // helper methods must be package visible in order to be accessible for test suites
    static boolean isAlmostEqual(double expected, double actual) throws CoordinateUseException {
        return isAlmostEqual(expected, actual, Coordinate.equalityAbsTolerance, Coordinate.equalityRelTolerance);
    }

    /**
     * Tests if two double values are equal within a certain absolute and relative tolerance.
     * @throws CoordinateUseException if any argument is not a finite double value
     * @return true if both values are equal within the given absolute tolerance
     *         or (for non-zero values only) equal within the given relative tolerance,
     *         else false
     * @MethodType helper
     */
    static boolean isAlmostEqual(double expected, double actual, double absTolerance, double relTolerance) throws CoordinateUseException {
        assertArgIsFinite(expected, "expected");
        assertArgIsFinite(actual, "actual");
        assertArgIsFinite(absTolerance, "absTolerance");
        assertArgIsFinite(relTolerance, "relTolerance");
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
    static double normalizeAngle(double angle, double angleMin, double angleMax) throws CoordinateUseException {
        // those 4 asserts could be dropped - the only user is the SphericCoordinate constructor,
        // which already does these checks
        assertPrecondition(angleMin < angleMax, "angleMin must be less than angleMax");
        assertArgIsFinite(angle, "angle");
        assertArgIsFinite(angleMin, "angleMin");
        assertArgIsFinite(angleMax, "angleMax");
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
     * @param arg value to check
     * @param argName name of that parameter (used in error message of exception)
     * @throws CoordinateUseException if arg is NaN or (pos./neg.) infinity
     * @MethodType assertion
     */
    static void assertArgIsFinite(double arg, String argName) throws CoordinateUseException {
        if(! Double.isFinite(arg)) {
            throw new CoordinateUseException(argName + " must have a finite double value");
        }
    }

    /**
     * Checks for illegal double values which were given as parameters.
     * @param arg value to check
     * @param argName name of that parameter (used in error message of exception)
     * @param limit
     * @throws CoordinateUseException if abs(arg) > abs(limit)
     * @MethodType assertion
     */
    static void assertArgMaxMagnitude(double arg, String argName, double limit) throws CoordinateUseException {
        if(Math.abs(arg) > Math.abs(limit)) {
            throw new CoordinateUseException(String.format(
                    "%s must have an absolute value that is less than or equal to the" +
                            "allowable maximum value %g (input: %g)",
                    argName, limit, arg
            ));
        }
    }

    /**
     * Checks for illegal values which were given as parameters.
     * @param arg value to check
     * @param argName name of that parameter (used in error message of exception)
     * @throws CoordinateUseException if arg is null
     * @MethodType assertion
     */
    static void assertArgNotNull(Object arg, String argName) throws CoordinateUseException {
        if(arg == null) {
            throw new CoordinateUseException(
                    argName + " must not be null"
            );
        }
    }

    /**
     * Checks if a given boolean expression evaluates to true.
     * Should be used only for checks of preconditions.
     * @param expression the boolean expression to check
     * @param reason an error message for the exception
     * @throws CoordinateUseException if expression is false
     * @MethodType assertion
     */
    static void assertPrecondition(boolean expression, String reason) throws CoordinateUseException {
        if(! expression) {
            throw new CoordinateUseException(reason);
        }
    }

    /**
     * Checks if a given boolean expression evaluates to true.
     * Should be used only for checks of postconditions or class invariants.
     * @param expression the boolean expression to check
     * @param reason an error message for the exception
     * @throws CoordinateError if expression is false
     * @MethodType assertion
     */
    static void assertInternalCondition(boolean expression, String reason) throws CoordinateError {
        if(! expression) {
            throw new CoordinateError(reason);
        }
    }
}
