package org.wahlzeit.model;

import org.junit.Test;

public class LocationTest {
	@Test
	public void testConstructor() {
		Coordinate c1 = new Coordinate(1.0, 2.0, 3.0);
		Location l = new Location(c1);
	}
}
