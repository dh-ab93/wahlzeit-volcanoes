package org.wahlzeit.model;

/**
 * class invariants: x, y and z are finite double values (not NaN,
 * not infinity); are ensured by immutable class and initial checks in
 * constructor.
 */
public class CartesianCoordinate extends AbstractCoordinate {
	/*
	 * make CartesianCoordinate immutable
	 * +: need to check values of x, y, z only once (constructor)
	 */
	public final double x, y, z;

	void assertClassInvariants() {
		assertTrue(Double.isFinite(x), RuntimeException.class);
		assertTrue(Double.isFinite(y), RuntimeException.class);
		assertTrue(Double.isFinite(z), RuntimeException.class);
	}
	
	/**
	 * right-handed coordinate system where y points right, z points up
	 * @throws IllegalArgumentException if any argument is not a finite double value
	 */
	public CartesianCoordinate(double x, double y, double z) {
		assertArgIsFinite(x);
		assertArgIsFinite(y);
		assertArgIsFinite(z);
		this.x = x;
		this.y = y;
		this.z = z;
		assertClassInvariants();
	}
	
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		return this;
	}

	@Override
	public double getCartesianDistance(Coordinate other) {
		assertTrue(other != null, IllegalArgumentException.class);
		CartesianCoordinate o = other.asCartesianCoordinate();
		double distX = this.x - o.x;
		double distY = this.y - o.y;
		double distZ = this.z - o.z;
		double distance = Math.sqrt(distX * distX + distY * distY + distZ * distZ);
		assertTrue(Double.isFinite(distance), ArithmeticException.class);
		return distance;
	}
	
	@Override
	public SphericCoordinate asSphericCoordinate() {
		// from https://en.wikipedia.org/wiki/Spherical_coordinate_system#Cartesian_coordinates
		double radius = Math.sqrt(x*x + y*y + z*z); // range [0, +infty]
		assertTrue(Double.isFinite(radius), ArithmeticException.class);
		double theta = 0.0;
		double phi = 0.0;
		if(radius > 0.0) {
			theta = Math.acos(z / radius); // range [0,PI]
			phi = Math.atan2(y, x); // range [-PI,PI], will be normalized by constructor
		}
		return new SphericCoordinate(radius, theta, phi);
	}

	@Override
	public double getCentralAngle(Coordinate other) {
		return asSphericCoordinate().getCentralAngle(other);
	}
	
	@Override
	boolean doIsEqual(Coordinate other) {
		CartesianCoordinate o = other.asCartesianCoordinate();
		return isAlmostEqual(x, o.x) &&
				isAlmostEqual(y, o.y) &&
				isAlmostEqual(z, o.z);
	}
}
