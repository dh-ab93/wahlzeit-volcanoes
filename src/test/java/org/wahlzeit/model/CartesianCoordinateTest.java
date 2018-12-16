package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CartesianCoordinateTest {
	@Test
	public void testCreate() throws AbstractCoordinateException {
		Coordinate c1 = CartesianCoordinate.create(0.0, 0.0, 0.0);
		Coordinate c2 = CartesianCoordinate.create(0.0, 0.0, 0.0);
		Coordinate c3 = CartesianCoordinate.create(0.0, 0.0, 1.0);
		assertSame(c1, c2);
		assertNotSame(c1, c3);
		double limit = CartesianCoordinate.MAX_VALUE;
		// test checks for illegal arguments
		assertTrue(CoordinateTest.throwsCoordinateUseException(
				() -> {
					CartesianCoordinate.create(Double.POSITIVE_INFINITY, 0.0, 0.0);
					return null;
				}));
		assertTrue(CoordinateTest.throwsCoordinateUseException(
				() -> {
					CartesianCoordinate.create(0.0, Double.NEGATIVE_INFINITY, 0.0);
					return null;
				}));
		assertTrue(CoordinateTest.throwsCoordinateUseException(
				() -> {
					CartesianCoordinate.create(0.0, 0.0, Double.NaN);
					return null;
				}));
		assertTrue(CoordinateTest.throwsCoordinateUseException(
				() -> {
					CartesianCoordinate.create(0.0, 0.0, -limit * 1.1);
					return null;
				}));
	}

	@Test
	public void testCartesianCoordinateEquals() throws AbstractCoordinateException {
		Coordinate cc1 = CartesianCoordinate.create(0.0, 0.0, 0.0);
		Coordinate cc2 = CartesianCoordinate.create(3.0, 4.0, 5.0);
		Coordinate cc3 = CartesianCoordinate.create(3.0, 4.0, 5.0);
		Coordinate cc4 = CartesianCoordinate.create(
				3.0 + AbstractCoordinate.equalityAbsTolerance / 10.0,
				4.0 + AbstractCoordinate.equalityAbsTolerance / 10.0,
				5.0 + 5.0 * AbstractCoordinate.equalityRelTolerance / 10.0
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
}
