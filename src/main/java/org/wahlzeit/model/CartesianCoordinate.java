package org.wahlzeit.model;

public class CartesianCoordinate implements Coordinate {
	/*
	 * make CartesianCoordinate immutable
	 * +: need to check values of x, y, z only once (constructor)
	 */
	public final double x, y, z;
	
	/**
	 * right-handed coordinate system where y points right, z points up
	 */
	public CartesianCoordinate(double x, double y, double z) {
		if(CoordinateHelper.isIllegalValue(x) ||
				CoordinateHelper.isIllegalValue(y) ||
				CoordinateHelper.isIllegalValue(z)) {
			throw new IllegalArgumentException();
		}
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		return this;
	}

	@Override
	public double getCartesianDistance(Coordinate other) {
		if(other == null) {
			throw new IllegalArgumentException();
		}
		CartesianCoordinate o = other.asCartesianCoordinate();
		double distX = this.x - o.x;
		double distY = this.y - o.y;
		double distZ = this.z - o.z;
		return Math.sqrt(distX * distX + distY * distY + distZ * distZ);
	}
	
	@Override
	public SphericCoordinate asSphericCoordinate() {
		// from https://en.wikipedia.org/wiki/Spherical_coordinate_system#Cartesian_coordinates
		double radius = Math.sqrt(x*x + y*y + z*z); // range [0, +infty)
		double theta = 0.0;
		double phi = 0.0;
		if(radius > 0) {
			theta = Math.acos(z / radius); // range [0,PI]
			phi = Math.atan2(y, x); // range [-PI,PI] will be normalized by constructor
		}
		return new SphericCoordinate(radius, theta, phi);
	}

	@Override
	public double getCentralAngle(Coordinate other) {
		return asSphericCoordinate().getCentralAngle(other);
	}
	
	@Override
	public boolean isEqual(Coordinate other) {
		if(other == null) {
			return false;
		}
		if(other == this) {
			return true;
		}
		CartesianCoordinate o = other.asCartesianCoordinate();
		return CoordinateHelper.isAlmostEqual(x, o.x) &&
				CoordinateHelper.isAlmostEqual(y, o.y) &&
				CoordinateHelper.isAlmostEqual(z, o.z);
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof Coordinate) {
			return isEqual((Coordinate) other);
		} else {
			return false;
		}
	}
}
