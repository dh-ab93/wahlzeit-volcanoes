package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CoordinateTest {
	@Test
	public void testConstructor() {
		Coordinate c1 = new Coordinate(0.0, 0.0, 0.0);
		
		boolean exceptionAsExpected;
		
		exceptionAsExpected = false;
		try {
			new Coordinate(Double.POSITIVE_INFINITY, 0.0, 0.0);
		} catch(IllegalArgumentException e) {
			exceptionAsExpected = true;
		} catch(Exception e) {
			;
		}
		assertTrue(exceptionAsExpected);
		
		exceptionAsExpected = false;
		try {
			new Coordinate(0.0, Double.NEGATIVE_INFINITY, 0.0);
		} catch(IllegalArgumentException e) {
			exceptionAsExpected = true;
		} catch(Exception e) {
			;
		}
		assertTrue(exceptionAsExpected);
		
		exceptionAsExpected = false;
		try {
			new Coordinate(0.0, 0.0, Double.NaN);
		} catch(IllegalArgumentException e) {
			exceptionAsExpected = true;
		} catch(Exception e) {
			;
		}
		assertTrue(exceptionAsExpected);
	}
	
	@Test
	public void testDistance() {
		Coordinate c1 = new Coordinate(0.0, 0.0, 0.0);
		Coordinate c2 = new Coordinate(3.0, 4.0, 0.0);
		Coordinate c3 = new Coordinate(2.0, 1.0, 8.0);
		Coordinate c4 = new Coordinate(-1.0, -3.0, -4.0);
		double delta = 1e-6;
		assertEquals(5.0, c1.getDistance(c2), delta);
		assertEquals(5.0, c2.getDistance(c1), delta);
		assertEquals(13.0, c3.getDistance(c4), delta);
		assertEquals(13.0, c4.getDistance(c3), delta);
		// test null argument
		boolean exceptionAsExpected = false;
		try {
			c1.getDistance(null);
		} catch(IllegalArgumentException e) {
			exceptionAsExpected = true;
		} catch(Exception e) {
			;
		}
		assertTrue(exceptionAsExpected);
	}
	
	@Test
	public void testEquals() {
		Coordinate c1 = new Coordinate(0.0, 0.0, 0.0);
		Coordinate c2 = new Coordinate(3.0, 4.0, 5.0);
		Coordinate c3 = new Coordinate(3.0, 4.0, 5.0);
		assertEquals(c2, c3);
		assertEquals(c3, c2);
		assertNotEquals(c1, c2);
		assertNotEquals(c2, c1);
		assertNotEquals(c1, null);
		assertNotEquals(null, c1);
	}
}
