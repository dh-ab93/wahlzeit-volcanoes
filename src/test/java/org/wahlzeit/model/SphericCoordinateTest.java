package org.wahlzeit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SphericCoordinateTest {
	@Test
	public void testSphericCoordinateConstructor() {
		@SuppressWarnings("unused")
		Coordinate c1 = new SphericCoordinate(0.0, 0.0, 0.0);
		
		// test checks for illegal arguments
		assertTrue(CoordinateTest.throwsIllegalArgumentException(
				() -> {new SphericCoordinate(Double.POSITIVE_INFINITY, 0.0, 0.0);}
				));
		assertTrue(CoordinateTest.throwsIllegalArgumentException(
				() -> {new SphericCoordinate(0.0, Double.NEGATIVE_INFINITY, 0.0);}
				));
		assertTrue(CoordinateTest.throwsIllegalArgumentException(
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
				0.0 + CoordinateHelper.defaultAbsTolerance / 10.0);
		Coordinate sc8 = new SphericCoordinate(1.0, 0.0, Math.PI);
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
		assertEquals(sc4, sc8);
		assertEquals(sc8, sc4);
	}
}
