package org.wahlzeit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CartesianCoordinateTest {
	@Test
	public void testCartesianCoordinateConstructor() {
		try {
			@SuppressWarnings("unused")
			Coordinate c1 = new CartesianCoordinate(0.0, 0.0, 0.0);
			double limit = CartesianCoordinate.MAX_VALUE;
			// test checks for illegal arguments
			assertTrue(CoordinateTest.throwsCoordinateUseException(
					() -> {
						new CartesianCoordinate(Double.POSITIVE_INFINITY, 0.0, 0.0);
						return null;
					}));
			assertTrue(CoordinateTest.throwsCoordinateUseException(
					() -> {
						new CartesianCoordinate(0.0, Double.NEGATIVE_INFINITY, 0.0);
						return null;
					}));
			assertTrue(CoordinateTest.throwsCoordinateUseException(
					() -> {
						new CartesianCoordinate(0.0, 0.0, Double.NaN);
						return null;
					}));
			assertTrue(CoordinateTest.throwsCoordinateUseException(
					() -> {
						new CartesianCoordinate(0.0, 0.0, -limit * 1.1);
						return null;
					}));
		} catch (AbstractCoordinateException e) {
			throw new AssertionError(e);
		}
	}

	@Test
	public void testCartesianCoordinateEquals() {
		try {
			Coordinate cc1 = new CartesianCoordinate(0.0, 0.0, 0.0);
			Coordinate cc2 = new CartesianCoordinate(3.0, 4.0, 5.0);
			Coordinate cc3 = new CartesianCoordinate(3.0, 4.0, 5.0);
			Coordinate cc4 = new CartesianCoordinate(
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
		} catch (AbstractCoordinateException e) {
			throw new AssertionError(e);
		}
	}
}
