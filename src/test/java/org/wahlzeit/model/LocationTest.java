package org.wahlzeit.model;

import org.junit.Test;

public class LocationTest {
	@Test
	public void testConstructor() {
		Coordinate c1 = new CartesianCoordinate(1.0, 2.0, 3.0);
		Coordinate c2 = new SphericCoordinate(1.0, 2.0, 3.0);
		Location l1 = new Location(c1);
		Location l2 = new Location(c2);
	}
}
