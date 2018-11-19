package org.wahlzeit.model;

public class SphericCoordinate implements Coordinate {
	/*
	 * make SphericCoordinate immutable
	 * +: need to check values of radius, theta, phi only once (constructor)
	 */
	public final double radius, theta, phi;

	/**
	 * spherical coordinate system with
     * theta as polar angle [0,PI),
	 * phi as azimuthal angle [0, 2PI)
	 */
	public SphericCoordinate(double radius, double theta, double phi) {
		if(CoordinateHelper.isIllegalValue(radius) ||
				CoordinateHelper.isIllegalValue(theta) ||
				CoordinateHelper.isIllegalValue(phi)) {
			throw new IllegalArgumentException();
		}
		this.radius = Math.abs(radius);
		this.theta = CoordinateHelper.normalizeAngle(theta, 0.0, Math.PI);
		this.phi = CoordinateHelper.normalizeAngle(phi, 0.0, 2.0*Math.PI);
	}
	
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		/*
		 * from https://en.wikipedia.org/wiki/Spherical_coordinate_system#Cartesian_coordinates
		 * required ranges:
		 * radius in [0, +infty)
		 * theta  in [0, PI)
		 * phi    in [0, 2*PI)
		 */
		double x = radius * Math.sin(theta) * Math.cos(phi);
		double y = radius * Math.sin(theta) * Math.sin(phi);
		double z = radius * Math.cos(theta);
		return new CartesianCoordinate(x, y, z);
	}

	@Override
	public double getCartesianDistance(Coordinate other) {
		return asCartesianCoordinate().getCartesianDistance(other);
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		return this;
	}

	@Override
	public double getCentralAngle(Coordinate other) {
		if(other == null) {
			throw new IllegalArgumentException();
		}
		if(radius == 0.0) {
			throw new IllegalStateException();
		}
		SphericCoordinate o = other.asSphericCoordinate();
		if(o.radius == 0.0) {
			throw new IllegalArgumentException();
		}
		/*
		 * from https://en.wikipedia.org/wiki/Great-circle_distance#Formulas
		 * (lambda in source is azimuthal angle -> phi in our model,
		 * phi in source is polar angle -> theta in our model)
		 */
		double phi1 = theta;
		double phi2 = o.theta;
		double deltaLambda = Math.abs(phi - o.phi);
		return Math.acos(
				Math.sin(phi1) * Math.sin(phi2) +
				Math.cos(phi1) * Math.cos(phi2) * Math.cos(deltaLambda));
	}

	@Override
	public boolean isEqual(Coordinate other) {
		if(other == null) {
			return false;
		}
		if(other == this) {
			return true;
		}
		SphericCoordinate o = other.asSphericCoordinate();
		if(CoordinateHelper.isAlmostEqual(0.0, radius) || CoordinateHelper.isAlmostEqual(0.0, o.radius)) {
			return CoordinateHelper.isAlmostEqual(radius, o.radius);
		}
		return CoordinateHelper.isAlmostEqual(radius, o.radius) &&
				CoordinateHelper.isAlmostEqual(theta, o.theta) &&
				CoordinateHelper.isAlmostEqual(phi, o.phi);
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
