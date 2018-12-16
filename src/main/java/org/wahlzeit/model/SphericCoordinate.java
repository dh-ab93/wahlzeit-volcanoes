package org.wahlzeit.model;

import java.util.HashMap;

/**
 * class invariants: radius, theta and phi are finite double values (not NaN,
 * not infinity); are ensured by immutable class and initial checks in
 * constructor.
 */
public class SphericCoordinate extends AbstractCoordinate {
	protected  static HashMap<String, SphericCoordinate> cache = new HashMap<>();
	public static final double MAX_RADIUS = Math.pow(2, 510);
	public static final SphericCoordinate ORIGIN = new SphericCoordinate();

	/*
	 * make SphericCoordinate immutable
	 * +: need to check values of radius, theta, phi only once (constructor)
	 */
	public final double radius, theta, phi;

	void assertClassInvariants() throws CoordinateError {
		assertInternalCondition(
				Double.isFinite(radius) && radius >= 0.0,
				"violation of class invariant: radius is " + radius + ", but must be a finite positive value"
		);
		assertInternalCondition(
				Double.isFinite(theta) && theta >= 0.0 && theta < Math.PI,
				"violation of class invariant: theta is " + theta + ", but must be in the range [0, PI)"
		);
		assertInternalCondition(
				Double.isFinite(phi) && phi >= 0.0 && phi < 2.0*Math.PI,
				"violation of class invariant: phi is " + phi + ", but must be in the range [0, 2*PI)"
		);
	}

	protected SphericCoordinate() {
		radius = 0.0;
		theta = 0.0;
		phi = 0.0;
	}

	protected SphericCoordinate(double radius, double theta, double phi) throws CoordinateError {
		this.radius = radius;
		this.theta = theta;
		this.phi = phi;
		assertClassInvariants();
	}

	/**
	 * spherical coordinate system with
     * theta as polar angle [0,PI),
	 * phi as azimuthal angle [0, 2PI).
	 * Theta and phi will be normalized to be inside that range.
	 * @throws CoordinateUseException if any argument is not a finite double value or if radius has magnitude > MAX_VALUE
	 * @throws CoordinateError for contract violation in callee (bug in code)
	 * @MethodType factory
	 */
	public static SphericCoordinate create(double radius, double theta, double phi) throws CoordinateUseException, CoordinateError {
		assertArgIsFinite(radius, "radius");
		// assure radius is kept in a safe range where calculations don't produce overflows
		assertArgMaxMagnitude(radius, "radius", MAX_RADIUS);
		assertArgIsFinite(theta, "theta");
		assertArgIsFinite(phi, "phi");
		double radius_ = Math.abs(radius);
		double theta_, phi_;
		try {
			theta_ = normalizeAngle(theta, 0.0, Math.PI);
			phi_ = normalizeAngle(phi, 0.0, 2.0 * Math.PI);
		} catch (CoordinateUseException e) {
			// we violated the contract, take the blame by wrapping in a CoordinateError
			throw new CoordinateError(
					String.format(
							"unexpected error while normalizing angles (" +
									"input: theta = %g, phi = %g)",
							theta, phi
					),
					e
			);
		}
		String key = asString(radius_, theta_, phi_);
		SphericCoordinate c = cache.get(key);
		if(c == null) {
			synchronized (SphericCoordinate.class) {
				c = cache.get(key);
				if(c == null) {
					c = new SphericCoordinate(radius_, theta_, phi_);
					cache.put(key, c);
				}
			}
		}
		return c;
	}
	
	@Override
	public CartesianCoordinate asCartesianCoordinate() throws CoordinateError {
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
		CartesianCoordinate result = null;
		try {
			result = CartesianCoordinate.create(x, y, z);
		} catch (CoordinateUseException e) {
			// unable to fulfill contract
			throw new CoordinateError(
					String.format(
							"unexpected error during conversion to cartesian coordinate (" +
									"this coordinate: %s; " +
									"computed values: x = %g, y = %g, z = %g)",
							this, x, y, z
					),
					e
			);
		}
		return result;
	}

	@Override
	public double getCartesianDistance(Coordinate other) throws CoordinateError, CoordinateUseException {
		assertArgNotNull(other, "other"); // throws CoordinateUseException
		CartesianCoordinate thisAsCartesianCoordinate = asCartesianCoordinate();
		CartesianCoordinate otherAsCartesianCoordinate = other.asCartesianCoordinate();
		try {
			return thisAsCartesianCoordinate.getCartesianDistance(otherAsCartesianCoordinate);
		} catch (CoordinateUseException e) {
			// we violated the contract, take the blame by wrapping in a CoordinateError
			throw new CoordinateError(e);
		}
	}

	@Override
	public SphericCoordinate asSphericCoordinate() {
		return this;
	}

	@Override
	public double getCentralAngle(Coordinate other) throws CoordinateUseException, CoordinateError {
		assertArgNotNull(other, "other");
		assertPrecondition(
				radius != 0.0,
				"getCentralAngle() is undefined on spheric coordinates with radius 0"
		);
		SphericCoordinate o = other.asSphericCoordinate();
		assertPrecondition(
				o.radius != 0.0,
				"getCentralAngle() is undefined for coordinates evaluating to radius 0"
		);

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

		assertInternalCondition(
				Double.isFinite(centralAngle) && centralAngle >= 0.0,
				String.format(
						"calculation of central angle produced illegal result (" +
								"this coordinate: %s; " +
								"other coordinate: %s; " +
								"computed result: %g)",
						this, other, centralAngle
				)
		);
		return centralAngle;
	}

	@Override
	boolean doIsEqual(Coordinate other) throws CoordinateError {
		// complicated edge cases for radius near 0.0 or theta near 0.0 or PI -> keep it simple
		return asCartesianCoordinate().doIsEqual(other);
	}

	/**
	 * @MethodType conversion
	 */
	@Override
	public String toString() {
		return asString(radius, theta, phi);
	}

	/**
	 * @MethodType conversion
	 */
	static protected String asString(double radius, double theta, double phi) {
		// precision = 10 (10 significant digits) according to Coordinate.equalityRelTolerance = 1e-10
		return String.format("SphericCoordinate(%.10g, %.10g, %.10g)", radius, theta, phi);
	}
}
