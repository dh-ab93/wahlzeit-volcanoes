package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * tests static helper methods of AbstractCoordinate
 */
public class AbstractCoordinateTest {
    @Test
    public void testAssertArgIsFinal() {
        assertTrue(CoordinateTest.throwsIllegalArgumentException(
                () -> AbstractCoordinate.assertArgIsFinite(Double.NaN)));
        assertTrue(CoordinateTest.throwsIllegalArgumentException(
                () -> AbstractCoordinate.assertArgIsFinite(Double.NEGATIVE_INFINITY)));
        assertTrue(CoordinateTest.throwsIllegalArgumentException(
                () -> AbstractCoordinate.assertArgIsFinite(Double.POSITIVE_INFINITY)));
        assertFalse(CoordinateTest.throwsIllegalArgumentException(
                () -> AbstractCoordinate.assertArgIsFinite(Double.MIN_VALUE)));
        assertFalse(CoordinateTest.throwsIllegalArgumentException(
                () -> AbstractCoordinate.assertArgIsFinite(0.0)));
    }

    @Test
    public void testAssertTrue() {
        assertTrue(CoordinateTest.throwsIllegalArgumentException(
                () -> AbstractCoordinate.assertTrue(false, IllegalArgumentException.class)));
        assertFalse(CoordinateTest.throwsIllegalArgumentException(
                () -> AbstractCoordinate.assertTrue(true, IllegalArgumentException.class)));
        assertTrue(CoordinateTest.throwsIllegalStateException(
                () -> AbstractCoordinate.assertTrue(false, IllegalStateException.class)));
        assertFalse(CoordinateTest.throwsIllegalStateException(
                () -> AbstractCoordinate.assertTrue(true, IllegalStateException.class)));
    }

    @Test
    public void testNormalizeAngle() {
        double delta = 1e-6;
        assertEquals(0.5, AbstractCoordinate.normalizeAngle(-1.5, 0.0, 1.0), delta);
        assertEquals(0.0, AbstractCoordinate.normalizeAngle(2.0, 0.0, 2.0), delta);
        assertEquals(-2.0, AbstractCoordinate.normalizeAngle(-2.0, -2.0, 0.0), delta);
        assertEquals(-2.0, AbstractCoordinate.normalizeAngle(2.0, -2.0, 0.0), delta);
        assertTrue(CoordinateTest.throwsIllegalArgumentException(
                () -> {AbstractCoordinate.normalizeAngle(1.0, 3.0, -4.0);}
        ));
    }

    @Test
    public void testIsAlmostEqual() {
        double v = 1.0;
        double deltaBelowAbs = Coordinate.equalityAbsTolerance / 2.0;
        double deltaBelowRel = v * Coordinate.equalityRelTolerance / 2.0;
        double deltaAboveAbs = Coordinate.equalityAbsTolerance * 2.0;
        double deltaAboveRel = v * Coordinate.equalityRelTolerance * 2.0;
        assertTrue(AbstractCoordinate.isAlmostEqual(-2.0, -3.0, 1.1, 0.6));
        assertTrue(AbstractCoordinate.isAlmostEqual(-2.0, -3.0, 0.9, 0.6));
        assertTrue(AbstractCoordinate.isAlmostEqual(-2.0, -3.0, 1.1, 0.4));
        assertTrue(! AbstractCoordinate.isAlmostEqual(-2.0, -3.0, 0.9, 0.4));
        assertTrue(AbstractCoordinate.isAlmostEqual(v, v + deltaBelowAbs));
        assertTrue(AbstractCoordinate.isAlmostEqual(v + deltaBelowAbs, v));
        assertTrue(AbstractCoordinate.isAlmostEqual(v, v + deltaBelowRel));
        assertTrue(AbstractCoordinate.isAlmostEqual(v + deltaBelowRel, v));
        assertTrue(! AbstractCoordinate.isAlmostEqual(v, v + deltaAboveAbs));
        assertTrue(! AbstractCoordinate.isAlmostEqual(v + deltaAboveAbs, v));
        assertTrue(! AbstractCoordinate.isAlmostEqual(v, v + deltaAboveRel));
        assertTrue(! AbstractCoordinate.isAlmostEqual(v + deltaAboveRel, v));
        assertTrue(AbstractCoordinate.isAlmostEqual(0.0, -deltaBelowAbs));
        assertTrue(AbstractCoordinate.isAlmostEqual(-deltaBelowAbs, 0.0));
        assertTrue(! AbstractCoordinate.isAlmostEqual(0.0, -deltaAboveAbs));
        assertTrue(! AbstractCoordinate.isAlmostEqual(-deltaAboveAbs, 0.0));
        assertTrue(AbstractCoordinate.isAlmostEqual(-deltaBelowAbs / 2.0, deltaBelowAbs / 2.0));
    }
}
