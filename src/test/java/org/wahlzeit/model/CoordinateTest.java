package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CoordinateTest {
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
