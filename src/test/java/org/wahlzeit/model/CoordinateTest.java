package org.wahlzeit.model;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CoordinateTest {
	@Test
	public void testHelperIllegalValue() {
		assertTrue(CoordinateHelper.isIllegalValue(Double.NaN));
		assertTrue(CoordinateHelper.isIllegalValue(Double.NEGATIVE_INFINITY));
		assertTrue(CoordinateHelper.isIllegalValue(Double.POSITIVE_INFINITY));
		assertTrue(! CoordinateHelper.isIllegalValue(Double.MIN_VALUE));
		assertTrue(! CoordinateHelper.isIllegalValue(0.0));
	}
	
	@Test
	public void testCartesianCoordinateConstructor() {
		@SuppressWarnings("unused")
		Coordinate c1 = new CartesianCoordinate(0.0, 0.0, 0.0);
		
		// test checks for illegal arguments
		assertTrue(throwsIllegalArgumentException(
				() -> {new CartesianCoordinate(Double.POSITIVE_INFINITY, 0.0, 0.0);}
				));
		assertTrue(throwsIllegalArgumentException(
				() -> {new CartesianCoordinate(0.0, Double.NEGATIVE_INFINITY, 0.0);}
				));
		assertTrue(throwsIllegalArgumentException(
				() -> {new CartesianCoordinate(0.0, 0.0, Double.NaN);}
				));
	}
	
	@Test
	public void testHelperNormalizeAngle() {
		assertEquals(0.5, CoordinateHelper.normalizeAngle(-1.5, 0.0, 1.0), 1e-6);
		assertEquals(0.0, CoordinateHelper.normalizeAngle(2.0, 0.0, 2.0), 1e-6);
		assertEquals(-2.0, CoordinateHelper.normalizeAngle(-2.0, -2.0, 0.0), 1e-6);
		assertEquals(-2.0, CoordinateHelper.normalizeAngle(2.0, -2.0, 0.0), 1e-6);
		assertTrue(throwsIllegalArgumentException(
				() -> {CoordinateHelper.normalizeAngle(1.0, 3.0, -4.0);}
				));
	}
	
	@Test
	public void testSphericCoordinateConstructor() {
		@SuppressWarnings("unused")
		Coordinate c1 = new SphericCoordinate(0.0, 0.0, 0.0);
		
		// test checks for illegal arguments
		assertTrue(throwsIllegalArgumentException(
				() -> {new SphericCoordinate(Double.POSITIVE_INFINITY, 0.0, 0.0);}
				));
		assertTrue(throwsIllegalArgumentException(
				() -> {new SphericCoordinate(0.0, Double.NEGATIVE_INFINITY, 0.0);}
				));
		assertTrue(throwsIllegalArgumentException(
				() -> {new SphericCoordinate(0.0, 0.0, Double.NaN);}
				));
		
		// test use of normalization of angles
		double delta = 1e-6;
		SphericCoordinate sc2 = new SphericCoordinate(-1.0, -Math.PI / 2.0, 2.0 * Math.PI);
		assertEquals(1.0, sc2.radius, delta);
		assertEquals(Math.PI / 2.0, sc2.theta, delta);
		assertEquals(0.0, sc2.phi, delta);
		SphericCoordinate sc3 = new SphericCoordinate(0.0, 2.0 * Math.PI, -Math.PI / 2.0);
		assertEquals(0.0, sc3.radius, delta);
		assertEquals(0.0, sc3.theta, delta);
		assertEquals(3.0 * Math.PI / 2.0, sc3.phi, delta);
	}
	
	@Test
	public void testCartesianDistance() {
		Coordinate c1 = new CartesianCoordinate(0.0, 0.0, 0.0);
		Coordinate c2 = new CartesianCoordinate(3.0, 4.0, 0.0);
		Coordinate c3 = new CartesianCoordinate(2.0, 1.0, 8.0);
		Coordinate c4 = new CartesianCoordinate(-1.0, -3.0, -4.0);
		double delta = 1e-6;
		assertEquals(5.0, c1.getCartesianDistance(c2), delta);
		assertEquals(5.0, c2.getCartesianDistance(c1), delta);
		assertEquals(13.0, c3.getCartesianDistance(c4), delta);
		assertEquals(13.0, c4.getCartesianDistance(c3), delta);
		// test check for null argument
		assertTrue(throwsIllegalArgumentException(
				() -> {c1.getCartesianDistance(null);}
				));
	}
	
	@Test
	public void testCentralAngle() {
		Coordinate c1 = new SphericCoordinate(0.0, 0.0, 0.0);
		Coordinate c2 = new SphericCoordinate(1.0, 0.0, 0.0);
		Coordinate c3 = new SphericCoordinate(2.0, Math.PI / 2.0, 0.0);
		Coordinate c4 = new SphericCoordinate(3.0, Math.PI / 2.0, Math.PI);
		double delta = 1e-6;
		assertEquals(0.0, c1.getCentralAngle(c2), delta);
		assertEquals(0.0, c2.getCentralAngle(c1), delta);
		assertEquals(Math.PI / 2.0, c2.getCentralAngle(c3), delta);
		assertEquals(Math.PI / 2.0, c3.getCentralAngle(c2), delta);
		assertEquals(0.0, c3.getCentralAngle(c4), delta);
		assertEquals(0.0, c4.getCentralAngle(c3), delta);
		// test null argument
		assertTrue(throwsIllegalArgumentException(
				() -> {c1.getCentralAngle(null);}
				));
	}
	
	@Test
	public void testHelperIsAlmostEqual() {
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
	
	@Test
	public void testCartesianCoordinateEquals() {
		Coordinate cc1 = new CartesianCoordinate(0.0, 0.0, 0.0);
		Coordinate cc2 = new CartesianCoordinate(3.0, 4.0, 5.0);
		Coordinate cc3 = new CartesianCoordinate(3.0, 4.0, 5.0);
		Coordinate cc4 = new CartesianCoordinate(
				3.0 + CoordinateHelper.defaultAbsTolerance / 10.0,
				4.0 + CoordinateHelper.defaultAbsTolerance / 10.0,
				5.0 + 5.0 * CoordinateHelper.defaultRelTolerance / 10.0
		);
		assertEquals(cc2, cc3);
		assertEquals(cc3, cc2);
		assertEquals(cc3, cc4);
		assertEquals(cc4, cc3);
		assertNotEquals(cc1, cc2);
		assertNotEquals(cc2, cc1);
		assertNotEquals(cc1, null);
		assertNotEquals(null, cc1);
	}
	
	@Test
	public void testSphericCoordinateEquals() {
		Coordinate sc1 = new SphericCoordinate(0.0, 0.0, 0.0);
		Coordinate sc2 = new SphericCoordinate(0.0, Math.PI / 2.0, 0.0);
		Coordinate sc3 = new SphericCoordinate(0.0, Math.PI / 2.0, Math.PI);
		Coordinate sc4 = new SphericCoordinate(1.0, 0.0, 0.0);
		Coordinate sc5 = new SphericCoordinate(1.0, Math.PI / 2.0, 0.0);
		Coordinate sc6 = new SphericCoordinate(1.0, Math.PI / 2.0, Math.PI);
		Coordinate sc7 = new SphericCoordinate(
				1.0 + 1.0 * CoordinateHelper.defaultRelTolerance / 10.0,
				0.0 + CoordinateHelper.defaultAbsTolerance / 10.0,
				0.0 + CoordinateHelper.defaultAbsTolerance / 10.0
		);
		assertEquals(sc1, sc2);
		assertEquals(sc2, sc1);
		assertEquals(sc1, sc3);
		assertEquals(sc3, sc1);
		assertEquals(sc4, sc7);
		assertEquals(sc7, sc4);
		assertNotEquals(sc1, sc4);
		assertNotEquals(sc4, sc1);
		assertNotEquals(sc4, sc5);
		assertNotEquals(sc5, sc4);
		assertNotEquals(sc4, sc6);
		assertNotEquals(sc6, sc4);
		assertNotEquals(sc1, null);
		assertNotEquals(null, sc1);
	}
	
	private boolean throwsIllegalArgumentException(Runnable r) {
		boolean catchedIllegalArgumentException = false;
		try {
			r.run();
			r.wait();
		} catch(IllegalArgumentException e) {
			catchedIllegalArgumentException = true;
		} catch(Exception e) {
			;
		}
		return catchedIllegalArgumentException;
	}
	
	@Test
	public void testCoordinateConversion() {
		CartesianCoordinate cc = new CartesianCoordinate(1.0, 2.0, 3.0);
		SphericCoordinate sc = new SphericCoordinate(1.0, 2.0, 3.0);
		assertTrue(cc.asCartesianCoordinate() instanceof CartesianCoordinate);
		assertTrue(sc.asCartesianCoordinate() instanceof CartesianCoordinate);
		assertTrue(cc.asSphericCoordinate() instanceof SphericCoordinate);
		assertTrue(sc.asSphericCoordinate() instanceof SphericCoordinate);
		assertTrue(! cc.asSphericCoordinate().isEqual(sc));
		assertTrue(! sc.asCartesianCoordinate().isEqual(cc));
		assertTrue(cc.asSphericCoordinate().asCartesianCoordinate().isEqual(cc));
		assertTrue(sc.asCartesianCoordinate().asSphericCoordinate().isEqual(sc));
		Coordinate c1 = new SphericCoordinate(3.7416573867739, 0.64052231267942, 1.1071487177941);
		assertTrue(cc.asSphericCoordinate().isEqual(c1));
		assertTrue(c1.isEqual(c1.asCartesianCoordinate()));
		Coordinate c2 = new CartesianCoordinate(-0.90019762973552, 0.12832006020246, -0.41614683654714);
		assertTrue(sc.asCartesianCoordinate().isEqual(c2.asCartesianCoordinate()));
		assertTrue(sc.asSphericCoordinate().isEqual(c2.asSphericCoordinate()));
	}
}
