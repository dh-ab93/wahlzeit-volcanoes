package org.wahlzeit.model;

import java.util.HashMap;

/**
 * class invariants: x, y and z are finite double values (not NaN,
 * not infinity); are ensured by immutable class and initial checks in
 * constructor.
 */
public class CartesianCoordinate extends AbstractCoordinate {
	protected static HashMap<String, CartesianCoordinate> cache = new HashMap<>();
	public static final double MAX_VALUE = Math.pow(2, 510);
	public static final CartesianCoordinate ORIGIN = new CartesianCoordinate();

	/*
	 * make CartesianCoordinate immutable
	 * +: need to check values of x, y, z only once (constructor)
	 */
	public final double x, y, z;

	void assertClassInvariants() throws CoordinateError {
		assertInternalCondition(
				Double.isFinite(x),
				"violation of class invariant: x is " + x + ", but must be a finite value"
		);
		assertInternalCondition(
				Double.isFinite(y),
				"violation of class invariant: y is " + y + ", but must be a finite value"
		);
		assertInternalCondition(
				Double.isFinite(z),
				"violation of class invariant: z is " + z + ", but must be a finite value"
		);
	}

	protected CartesianCoordinate() {
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}

	protected CartesianCoordinate(double x, double y, double z) throws CoordinateError {
		this.x = x;
		this.y = y;
		this.z = z;
		assertClassInvariants();
	}
	
	/**
	 * right-handed coordinate system where y points right, z points up
	 * All values have a guaranteed precision of 10 decimal digits in scientific notation.
	 * Instances are cached and shared - repeated calls with arguments which are numerically equal or below
	 * the precision threshold will return cached instances equal to the arguments within that precision.
	 * @throws CoordinateUseException if any argument is not a finite double value or has magnitude > MAX_VALUE
	 * @throws CoordinateError for contract violation in callee (bug in code)
	 * @MethodType factory
	 */
	public static CartesianCoordinate create(double x, double y, double z) throws CoordinateUseException, CoordinateError {
		assertArgIsFinite(x, "x");
		assertArgIsFinite(y, "y");
		assertArgIsFinite(z, "z");
		assertArgMaxMagnitude(x, "x", MAX_VALUE);
		assertArgMaxMagnitude(y, "y", MAX_VALUE);
		assertArgMaxMagnitude(z, "z", MAX_VALUE);
		String key = asString(x, y, z);
		CartesianCoordinate c = cache.get(key);
		if(c == null) {
			synchronized (CartesianCoordinate.class) {
				c = cache.get(key);
				if(c == null) {
					c = new CartesianCoordinate(x, y, z);
					cache.put(key, c);
				}
			}
		}
		return c;
	}
	
	@Override
	public CartesianCoordinate asCartesianCoordinate() {
		return this;
	}

	@Override
	public double getCartesianDistance(Coordinate other) throws CoordinateUseException, CoordinateError {
		assertPrecondition(other != null, "other may not be null");

		CartesianCoordinate o = other.asCartesianCoordinate(); // let CoordinateError pass on
		double distX = this.x - o.x;
		double distY = this.y - o.y;
		double distZ = this.z - o.z;
		double distance = Math.sqrt(distX * distX + distY * distY + distZ * distZ);

		assertInternalCondition(
				Double.isFinite(distance) && distance >= 0.0,
				String.format(
						"calculation of distance produced illegal result (" +
								"this coordinate: %s; " +
								"other coordinate: %s; " +
								"other coordinate as cartesian: %s; " +
								"computed result: %g)",
						this, other, o, distance
				)
		);
		return distance;
	}
	
	@Override
	public SphericCoordinate asSphericCoordinate() throws CoordinateError {
		// from https://en.wikipedia.org/wiki/Spherical_coordinate_system#Cartesian_coordinates
		double radius = Math.sqrt(x*x + y*y + z*z); // range [0, +infty]
		assertInternalCondition(
				Double.isFinite(radius) && radius >= 0.0,
				String.format(
						"calculation of radius produced illegal result (" +
								"this coordinate: %s; " +
								"computed radius = %g, but should be a finite positive value)",
						this, radius
				)
		);

		double theta = 0.0;
		double phi = 0.0;
		if(radius > 0.0) {
			theta = Math.acos(z / radius); // range [0,PI]
			phi = Math.atan2(y, x); // range [-PI,PI], will be normalized by constructor
		}
		SphericCoordinate result = null;
		try {
			result = SphericCoordinate.create(radius, theta, phi);
		} catch (CoordinateUseException e) {
			// we violated the contract, take the blame by wrapping in a CoordinateError
			throw new CoordinateError(e);
		}

		assertInternalCondition(
				result != null,
				"conversion to spheric coordinate produced a null reference"
		);
		return result;
	}

	@Override
	public double getCentralAngle(Coordinate other) throws CoordinateUseException, CoordinateError {
		/* instead of replicating all pre- and postconditions:
		return asCartesianCoordinate().getCentralAngle(other);
		*/

		// doing it the hard way because we are a public method (i.e. at the interface boundary)
		assertArgNotNull(other, "other");
		SphericCoordinate thisAsSphericCoordinate = asSphericCoordinate();
		assertPrecondition(
				thisAsSphericCoordinate.radius != 0.0,
				"getCentralAngle() is undefined on coordinates evaluating to radius 0"
		);
		SphericCoordinate o = other.asSphericCoordinate();
		assertPrecondition(
				o.radius != 0.0,
				"getCentralAngle() is undefined for coordinates evaluating to radius 0"
		);

		double centralAngle;
		try {
			centralAngle = thisAsSphericCoordinate.getCentralAngle(o);
		} catch (CoordinateUseException e) {
			// we violated the contract, take the blame by wrapping in a CoordinateError
			throw new CoordinateError(e);
		}

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
		// we are a primitive method -> trust isEqual to call us with other != null
		CartesianCoordinate o = other.asCartesianCoordinate();
		boolean result;
		try {
			// contract: isAlmostEqual expects all arguments to be finite values
			// this should be guaranteed by our class invariants
			result = isAlmostEqual(x, o.x) &&
					isAlmostEqual(y, o.y) &&
					isAlmostEqual(z, o.z);
		} catch (CoordinateUseException e) {
			// we violated the contract, take the blame by wrapping in a CoordinateError
			throw new CoordinateError(e);
		}
		return result;
	}

	/**
	 * @MethodType conversion
	 */
	@Override
	public String toString() {
		return asString(x, y, z);
	}

	/**
	 * @MethodType conversion
	 */
	static protected String asString(double x, double y, double z) {
		// precision = 10 (10 significant digits) according to Coordinate.equalityRelTolerance = 1e-10
		return String.format("CartesianCoordinate(%.10g, %.10g, %.10g)", x, y, z);
	}
}
