package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CoordinateTest {
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
		assertTrue(CoordinateTest.throwsIllegalArgumentException(
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
		assertEquals(Math.PI / 2.0, c2.getCentralAngle(c3), delta);
		assertEquals(Math.PI / 2.0, c3.getCentralAngle(c2), delta);
		assertEquals(0.0, c3.getCentralAngle(c4), delta);
		assertEquals(0.0, c4.getCentralAngle(c3), delta);
		// test null argument
		assertTrue(CoordinateTest.throwsIllegalArgumentException(
				() -> {c1.getCentralAngle(null);}
				));
		// test radius 0
		assertTrue(CoordinateTest.throwsIllegalStateException(
				() -> {c1.getCentralAngle(c3);}
				));
		assertTrue(CoordinateTest.throwsIllegalArgumentException(
				() -> {c3.getCentralAngle(c1);}
				));
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
	
	/**
	 * static helper method for all Coordinate*Test classes / test suites 
	 * @param r 
	 * @return true if the run() method of the Runnable throws an
	 * IllegalArgumentException, else false
	 */
	static boolean throwsIllegalArgumentException(Runnable r) {
		boolean catched = false;
		try {
			r.run();
			r.wait();
		} catch(IllegalArgumentException e) {
			catched = true;
		} catch(Exception e) {
			;
		}
		return catched;
	}
	
	/**
	 * static helper method for all Coordinate*Test classes / test suites 
	 * @param r 
	 * @return true if the run() method of the Runnable throws an
	 * IllegalStateException, else false
	 */
	static boolean throwsIllegalStateException(Runnable r) {
		boolean catched = false;
		try {
			r.run();
			r.wait();
		} catch(IllegalStateException e) {
			catched = true;
		} catch(Exception e) {
			;
		}
		return catched;
	}

}
