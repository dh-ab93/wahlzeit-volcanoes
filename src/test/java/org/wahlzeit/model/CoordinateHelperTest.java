package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CoordinateHelperTest {
	@Test
	public void testIsIllegalValue() {
		assertTrue(CoordinateHelper.isIllegalValue(Double.NaN));
		assertTrue(CoordinateHelper.isIllegalValue(Double.NEGATIVE_INFINITY));
		assertTrue(CoordinateHelper.isIllegalValue(Double.POSITIVE_INFINITY));
		assertTrue(! CoordinateHelper.isIllegalValue(Double.MIN_VALUE));
		assertTrue(! CoordinateHelper.isIllegalValue(0.0));
	}
	
	@Test
	public void testNormalizeAngle() {
		double delta = 1e-6;
		assertEquals(0.5, CoordinateHelper.normalizeAngle(-1.5, 0.0, 1.0), delta);
		assertEquals(0.0, CoordinateHelper.normalizeAngle(2.0, 0.0, 2.0), delta);
		assertEquals(-2.0, CoordinateHelper.normalizeAngle(-2.0, -2.0, 0.0), delta);
		assertEquals(-2.0, CoordinateHelper.normalizeAngle(2.0, -2.0, 0.0), delta);
		assertTrue(CoordinateTest.throwsIllegalArgumentException(
				() -> {CoordinateHelper.normalizeAngle(1.0, 3.0, -4.0);}
				));
	}
	
	@Test
	public void testIsAlmostEqual() {
		double v = 1.0;
		double deltaBelowAbs = CoordinateHelper.defaultAbsTolerance / 2.0;
		double deltaBelowRel = v * CoordinateHelper.defaultRelTolerance / 2.0;
		double deltaAboveAbs = CoordinateHelper.defaultAbsTolerance * 2.0;
		double deltaAboveRel = v * CoordinateHelper.defaultRelTolerance * 2.0;
		assertTrue(CoordinateHelper.isAlmostEqual(-2.0, -3.0, 1.1, 0.6));
		assertTrue(! CoordinateHelper.isAlmostEqual(-2.0, -3.0, 0.9, 0.6));
		assertTrue(! CoordinateHelper.isAlmostEqual(-2.0, -3.0, 1.1, 0.4));
		assertTrue(CoordinateHelper.isAlmostEqual(v, v + deltaBelowAbs));
		assertTrue(CoordinateHelper.isAlmostEqual(v + deltaBelowAbs, v));
		assertTrue(CoordinateHelper.isAlmostEqual(v, v + deltaBelowRel));
		assertTrue(CoordinateHelper.isAlmostEqual(v + deltaBelowRel, v));
		assertTrue(! CoordinateHelper.isAlmostEqual(v, v + deltaAboveAbs));
		assertTrue(! CoordinateHelper.isAlmostEqual(v + deltaAboveAbs, v));
		assertTrue(! CoordinateHelper.isAlmostEqual(v, v + deltaAboveRel));
		assertTrue(! CoordinateHelper.isAlmostEqual(v + deltaAboveRel, v));
		assertTrue(CoordinateHelper.isAlmostEqual(0.0, -deltaBelowAbs));
		assertTrue(CoordinateHelper.isAlmostEqual(-deltaBelowAbs, 0.0));
		assertTrue(! CoordinateHelper.isAlmostEqual(-deltaBelowAbs / 2.0, deltaBelowAbs / 2.0));
	}
}
