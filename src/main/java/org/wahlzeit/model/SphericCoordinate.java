package org.wahlzeit.model;

/**
 * class invariants: radius, theta and phi are finite double values (not NaN,
 * not infinity); are ensured by immutable class and initial checks in
 * constructor.
 */
public class SphericCoordinate extends AbstractCoordinate {
	/*
	 * make SphericCoordinate immutable
	 * +: need to check values of radius, theta, phi only once (constructor)
	 */
	public final double radius, theta, phi;

	void assertClassInvariants() {
		assertTrue(Double.isFinite(radius) && radius >= 0.0, RuntimeException.class);
		assertTrue(Double.isFinite(theta) && theta >= 0.0 && theta < Math.PI, RuntimeException.class);
		assertTrue(Double.isFinite(phi) && phi >= 0.0 && phi < 2.0*Math.PI, RuntimeException.class);
	}

	/**
	 * spherical coordinate system with
     * theta as polar angle [0,PI),
	 * phi as azimuthal angle [0, 2PI).
	 * Theta and phi will be normalized to be inside that range.
	 * @throws IllegalArgumentException if any argument is not a finite double value
	 */
	public SphericCoordinate(double radius, double theta, double phi) {
		assertArgIsFinite(radius);
		assertArgIsFinite(theta);
		assertArgIsFinite(phi);
		this.radius = Math.abs(radius);
		this.theta = normalizeAngle(theta, 0.0, Math.PI);
		this.phi = normalizeAngle(phi, 0.0, 2.0*Math.PI);
		assertClassInvariants();
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
		assertTrue(other != null, IllegalArgumentException.class);
		assertTrue(radius != 0.0, IllegalStateException.class);
		SphericCoordinate o = other.asSphericCoordinate();
		assertTrue(o.radius != 0.0, IllegalArgumentException.class);
		/*
		 * from https://en.wikipedia.org/wiki/Great-circle_distance#Formulas
		 * (lambda in source is azimuthal angle -> phi in our model,
		 * phi in source is polar angle -> theta in our model)
		 */
		double phi1 = theta;
		double phi2 = o.theta;
		double deltaLambda = Math.abs(phi - o.phi);
		double acosArg = Math.sin(phi1) * Math.sin(phi2) +
				Math.cos(phi1) * Math.cos(phi2) * Math.cos(deltaLambda);
		double centralAngle = Math.acos(acosArg);
		return centralAngle;
	}

	@Override
	boolean doIsEqual(Coordinate other) {
		// complicated edge cases for radius near 0.0 or theta near 0.0 or PI -> keep it simple
		return asCartesianCoordinate().doIsEqual(other);
	}
}
