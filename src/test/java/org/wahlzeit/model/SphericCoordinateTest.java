package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SphericCoordinateTest {
	@Test
	public void testCreate() throws AbstractCoordinateException {
		Coordinate c1 = SphericCoordinate.create(1.0, Math.PI / 2.0, 0.0);
		Coordinate c2 = SphericCoordinate.create(1.0, Math.PI / 2.0, 0.0);
		Coordinate c3 = SphericCoordinate.create(1.0, Math.PI / 2.0, Math.PI / 2.0);
		assertSame(c1, c2);
		assertNotSame(c1, c3);
		double limit = SphericCoordinate.MAX_RADIUS;
		// test checks for illegal arguments
		assertTrue(CoordinateTest.throwsCoordinateUseException(
				() -> {
					SphericCoordinate.create(Double.POSITIVE_INFINITY, 0.0, 0.0);
					return null;
				}));
		assertTrue(CoordinateTest.throwsCoordinateUseException(
				() -> {
					SphericCoordinate.create(0.0, Double.NEGATIVE_INFINITY, 0.0);
					return null;
				}));
		assertTrue(CoordinateTest.throwsCoordinateUseException(
				() -> {
					SphericCoordinate.create(0.0, 0.0, Double.NaN);
					return null;
				}));
		assertTrue(CoordinateTest.throwsCoordinateUseException(
				() -> {
					SphericCoordinate.create(limit * 1.1, 0.0, 0.0);
					return null;
				}));

		// test use of normalization of angles
		double delta = 1e-6;
		SphericCoordinate sc2 = SphericCoordinate.create(-1.0, -Math.PI / 2.0, 2.0 * Math.PI);
		assertEquals(1.0, sc2.radius, delta);
		assertEquals(Math.PI / 2.0, sc2.theta, delta);
		assertEquals(0.0, sc2.phi, delta);
		SphericCoordinate sc3 = SphericCoordinate.create(0.0, 2.0 * Math.PI, -Math.PI / 2.0);
		assertEquals(0.0, sc3.radius, delta);
		assertEquals(0.0, sc3.theta, delta);
		assertEquals(3.0 * Math.PI / 2.0, sc3.phi, delta);
	}

	@Test
	public void testSphericCoordinateEquals() throws AbstractCoordinateException {
		Coordinate sc1 = SphericCoordinate.create(0.0, 0.0, 0.0);
		Coordinate sc2 = SphericCoordinate.create(0.0, Math.PI / 2.0, 0.0);
		Coordinate sc3 = SphericCoordinate.create(0.0, Math.PI / 2.0, Math.PI);
		Coordinate sc4 = SphericCoordinate.create(1.0, 0.0, 0.0);
		Coordinate sc5 = SphericCoordinate.create(1.0, Math.PI / 2.0, 0.0);
		Coordinate sc6 = SphericCoordinate.create(1.0, Math.PI / 2.0, Math.PI);
		Coordinate sc7 = SphericCoordinate.create(
				1.0 + 1.0 * AbstractCoordinate.equalityRelTolerance / 10.0,
				0.0 + AbstractCoordinate.equalityAbsTolerance / 10.0,
				0.0 + AbstractCoordinate.equalityAbsTolerance / 10.0);
		Coordinate sc8 = SphericCoordinate.create(1.0, 0.0, Math.PI);
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
