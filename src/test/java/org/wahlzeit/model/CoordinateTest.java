package org.wahlzeit.model;

import org.junit.Test;

import java.util.concurrent.Callable;

import static org.junit.Assert.*;

public class CoordinateTest {
	@Test
	public void testCartesianDistance() throws AbstractCoordinateException {
		Coordinate c1 = CartesianCoordinate.create(0.0, 0.0, 0.0);
		Coordinate c2 = CartesianCoordinate.create(3.0, 4.0, 0.0);
		Coordinate c3 = CartesianCoordinate.create(2.0, 1.0, 8.0);
		Coordinate c4 = CartesianCoordinate.create(-1.0, -3.0, -4.0);
		double delta = 1e-6;
		assertEquals(5.0, c1.getCartesianDistance(c2), delta);
		assertEquals(5.0, c2.getCartesianDistance(c1), delta);
		assertEquals(13.0, c3.getCartesianDistance(c4), delta);
		assertEquals(13.0, c4.getCartesianDistance(c3), delta);
		// test check for null argument
		assertTrue(CoordinateTest.throwsCoordinateUseException(
				() -> {
					c1.getCartesianDistance(null);
					return null;
				}));
		double limit = CartesianCoordinate.MAX_VALUE;
		Coordinate c5 = CartesianCoordinate.create(limit, limit, limit);
		Coordinate c6 = CartesianCoordinate.create(-limit, -limit, -limit);
		Coordinate c7 = SphericCoordinate.create(limit, 0.0, 0.0);
		double distance56 = c5.getCartesianDistance(c6);
		double distance67 = c6.getCartesianDistance(c7);
		assertTrue(Double.isFinite(distance56) && distance56 > 0.0);
		assertTrue(Double.isFinite(distance67) && distance67 > 0.0);
	}
	
	@Test
	public void testCentralAngle() throws AbstractCoordinateException {
		Coordinate c1 = SphericCoordinate.create(0.0, 0.0, 0.0);
		Coordinate c2 = SphericCoordinate.create(1.0, 0.0, 0.0);
		Coordinate c3 = SphericCoordinate.create(2.0, Math.PI / 2.0, 0.0);
		Coordinate c4 = SphericCoordinate.create(3.0, Math.PI / 2.0, Math.PI);
		double delta = 1e-6;
		assertEquals(Math.PI / 2.0, c2.getCentralAngle(c3), delta);
		assertEquals(Math.PI / 2.0, c3.getCentralAngle(c2), delta);
		assertEquals(0.0, c3.getCentralAngle(c4), delta);
		assertEquals(0.0, c4.getCentralAngle(c3), delta);
		// test null argument
		assertTrue(CoordinateTest.throwsCoordinateUseException(
				() -> {
					c1.getCentralAngle(null);
					return null;
				}));
		// test radius 0
		assertTrue(CoordinateTest.throwsCoordinateUseException(
				() -> {
					c1.getCentralAngle(c3);
					return null;
				}));
		assertTrue(CoordinateTest.throwsCoordinateUseException(
				() -> {
					c3.getCentralAngle(c1);
					return null;
				}));
	}
	
	@Test
	public void testCoordinateConversion() throws AbstractCoordinateException {
		CartesianCoordinate cc = CartesianCoordinate.create(1.0, 2.0, 3.0);
		SphericCoordinate sc = SphericCoordinate.create(1.0, 2.0, 3.0);
		assertTrue(cc.asCartesianCoordinate() instanceof CartesianCoordinate);
		assertTrue(sc.asCartesianCoordinate() instanceof CartesianCoordinate);
		assertTrue(cc.asSphericCoordinate() instanceof SphericCoordinate);
		assertTrue(sc.asSphericCoordinate() instanceof SphericCoordinate);
		assertTrue(!cc.asSphericCoordinate().isEqual(sc));
		assertTrue(!sc.asCartesianCoordinate().isEqual(cc));
		assertTrue(cc.asSphericCoordinate().asCartesianCoordinate().isEqual(cc));
		assertTrue(sc.asCartesianCoordinate().asSphericCoordinate().isEqual(sc));
		Coordinate c1 = SphericCoordinate.create(3.7416573867739, 0.64052231267942, 1.1071487177941);
		assertTrue(cc.asSphericCoordinate().isEqual(c1));
		assertTrue(c1.isEqual(c1.asCartesianCoordinate()));
		Coordinate c2 = CartesianCoordinate.create(-0.90019762973552, 0.12832006020246, -0.41614683654714);
		assertTrue(sc.asCartesianCoordinate().isEqual(c2.asCartesianCoordinate()));
		assertTrue(sc.asSphericCoordinate().isEqual(c2.asSphericCoordinate()));
		// test atan2 behavior for 0.0
		CartesianCoordinate c3 = CartesianCoordinate.create(0.0, 0.0, 3.0);
		SphericCoordinate c4 = SphericCoordinate.create(3.0, 0.0, 0.0);
		assertTrue(c3.asSphericCoordinate().isEqual(c4));
	}
	
	/**
	 * static helper method for all Coordinate*Test classes / test suites 
	 * @param c
	 * @return true if the run() method of the Runnable throws a
	 * CoordinateUseException, else false
	 * @throws AssertionError if any other exception is thrown to signal test failure
	 */
	static boolean throwsCoordinateUseException(Callable<Void> c) {
		try {
			c.call();
			c.wait();
		} catch(CoordinateUseException e) {
			return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * static helper method for all Coordinate*Test classes / test suites
	 * @param c
	 * @return true if the run() method of the Runnable throws a
	 * CoordinateError, else false
	 * @throws AssertionError if any other exception is thrown to signal test failure
	 */
	static boolean throwsCoordinateError(Callable<Void> c) {
		try {
			c.call();
			c.wait();
		} catch(CoordinateError e) {
			return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
