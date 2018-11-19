package org.wahlzeit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CartesianCoordinateTest {
	@Test
	public void testCartesianCoordinateConstructor() {
		@SuppressWarnings("unused")
		Coordinate c1 = new CartesianCoordinate(0.0, 0.0, 0.0);
		
		// test checks for illegal arguments
		assertTrue(CoordinateTest.throwsIllegalArgumentException(
				() -> {new CartesianCoordinate(Double.POSITIVE_INFINITY, 0.0, 0.0);}
				));
		assertTrue(CoordinateTest.throwsIllegalArgumentException(
				() -> {new CartesianCoordinate(0.0, Double.NEGATIVE_INFINITY, 0.0);}
				));
		assertTrue(CoordinateTest.throwsIllegalArgumentException(
				() -> {new CartesianCoordinate(0.0, 0.0, Double.NaN);}
				));
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
}
