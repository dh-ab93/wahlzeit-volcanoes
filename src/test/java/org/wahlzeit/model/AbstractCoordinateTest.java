package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * tests static helper methods of AbstractCoordinate
 */
public class AbstractCoordinateTest {
    final String d = "dummy";

    @Test
    public void testAssertArgIsFinite() {
        assertTrue(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertArgIsFinite(Double.NaN, d);
                    return null;
                }));
        assertTrue(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertArgIsFinite(Double.NEGATIVE_INFINITY, d);
                    return null;
                }));
        assertTrue(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertArgIsFinite(Double.POSITIVE_INFINITY, d);
                    return null;
                }));
        assertFalse(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertArgIsFinite(Double.MIN_VALUE, d);
                    return null;
                }));
        assertFalse(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertArgIsFinite(0.0, d);
                    return null;
                }));
    }

    @Test
    public void testAssertArgMaxMagnitude() {
        double limit = Math.pow(2.0, 510.0);
        assertTrue(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertArgMaxMagnitude(2.0, d, 1.0);
                    return null;
                }));
        assertTrue(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertArgMaxMagnitude(-2.0, d, 1.0);
                    return null;
                }));
        assertFalse(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertArgMaxMagnitude(0.0, d, 0.0);
                    return null;
                }));
        assertFalse(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertArgMaxMagnitude(-limit, d, limit);
                    return null;
                }));
        assertFalse(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertArgMaxMagnitude(1.0, d, 2.0);
                    return null;
                }));
    }

    @Test
    public void testAssertArgNotNull() {
        assertTrue(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertArgNotNull(null, d);
                    return null;
                }));
        assertFalse(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertArgNotNull(d, d);
                    return null;
                }));
    }

    @Test
    public void testAssertPrecondition() {
        assertTrue(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertPrecondition(false, d);
                    return null;
                }));
        assertFalse(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.assertPrecondition(true, d);
                    return null;
                }));
    }

    @Test
    public void testAssertInternalCondition() {
        assertTrue(CoordinateTest.throwsCoordinateError(
                () -> {
                    AbstractCoordinate.assertInternalCondition(false, d);
                    return null;
                }));
        assertFalse(CoordinateTest.throwsCoordinateError(
                () -> {
                    AbstractCoordinate.assertInternalCondition(true, d);
                    return null;
                }));
    }

    @Test
    public void testNormalizeAngle() throws CoordinateUseException {
        double delta = 1e-6;
        assertEquals(0.5, AbstractCoordinate.normalizeAngle(-1.5, 0.0, 1.0), delta);
        assertEquals(0.0, AbstractCoordinate.normalizeAngle(2.0, 0.0, 2.0), delta);
        assertEquals(-2.0, AbstractCoordinate.normalizeAngle(-2.0, -2.0, 0.0), delta);
        assertEquals(-2.0, AbstractCoordinate.normalizeAngle(2.0, -2.0, 0.0), delta);
        assertTrue(CoordinateTest.throwsCoordinateUseException(
                () -> {
                    AbstractCoordinate.normalizeAngle(1.0, 3.0, -4.0);
                    return null;
                }));
    }

    @Test
    public void testIsAlmostEqual() throws CoordinateUseException {
        double v = 1.0;
        double deltaBelowAbs = Coordinate.equalityAbsTolerance / 2.0;
        double deltaBelowRel = v * Coordinate.equalityRelTolerance / 2.0;
        double deltaAboveAbs = Coordinate.equalityAbsTolerance * 2.0;
        double deltaAboveRel = v * Coordinate.equalityRelTolerance * 2.0;
        assertTrue(AbstractCoordinate.isAlmostEqual(-2.0, -3.0, 1.1, 0.6));
        assertTrue(AbstractCoordinate.isAlmostEqual(-2.0, -3.0, 0.9, 0.6));
        assertTrue(AbstractCoordinate.isAlmostEqual(-2.0, -3.0, 1.1, 0.4));
        assertTrue(!AbstractCoordinate.isAlmostEqual(-2.0, -3.0, 0.9, 0.4));
        assertTrue(AbstractCoordinate.isAlmostEqual(v, v + deltaBelowAbs));
        assertTrue(AbstractCoordinate.isAlmostEqual(v + deltaBelowAbs, v));
        assertTrue(AbstractCoordinate.isAlmostEqual(v, v + deltaBelowRel));
        assertTrue(AbstractCoordinate.isAlmostEqual(v + deltaBelowRel, v));
        assertTrue(!AbstractCoordinate.isAlmostEqual(v, v + deltaAboveAbs));
        assertTrue(!AbstractCoordinate.isAlmostEqual(v + deltaAboveAbs, v));
        assertTrue(!AbstractCoordinate.isAlmostEqual(v, v + deltaAboveRel));
        assertTrue(!AbstractCoordinate.isAlmostEqual(v + deltaAboveRel, v));
        assertTrue(AbstractCoordinate.isAlmostEqual(0.0, -deltaBelowAbs));
        assertTrue(AbstractCoordinate.isAlmostEqual(-deltaBelowAbs, 0.0));
        assertTrue(!AbstractCoordinate.isAlmostEqual(0.0, -deltaAboveAbs));
        assertTrue(!AbstractCoordinate.isAlmostEqual(-deltaAboveAbs, 0.0));
        assertTrue(AbstractCoordinate.isAlmostEqual(-deltaBelowAbs / 2.0, deltaBelowAbs / 2.0));
    }
}
