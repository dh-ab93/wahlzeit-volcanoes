package org.wahlzeit.model;

public interface Coordinate {
	double equalityAbsTolerance = 1e-10;
	double equalityRelTolerance = 1e-10;

	/**
	 * Converts the Coordinate to CartesianCoordinate.
	 * @throws CoordinateError for contract violation in callee (bug in code)
	 * @MethodType conversion
	 */
	CartesianCoordinate asCartesianCoordinate() throws CoordinateError;
	
	/**
	 * Returns the cartesian distance between this and another Coordinate.
	 * @throws CoordinateUseException for contract violation in caller (illegal arguments / improper use of method in client code)
	 * @throws CoordinateError for contract violation in callee (bug in code behind the interface)
	 * @MethodType getter
	 */
	double getCartesianDistance(Coordinate other) throws CoordinateUseException, CoordinateError;
	
	/**
	 * Converts the Coordinate to SphericCoordinate.
	 * @throws CoordinateError for contract violation in callee (bug in code behind the interface)
	 * @MethodType conversion
	 */
	SphericCoordinate asSphericCoordinate() throws CoordinateError;
	
	/**
	 * Returns the central angle between this and another Coordinate.
	 * @throws CoordinateUseException for contract violation in caller (illegal arguments / improper use of method in client code)
	 * @throws CoordinateError for contract violation in callee (bug in code behind the interface)
	 * @MethodType getter
	 */
	double getCentralAngle(Coordinate other) throws CoordinateUseException, CoordinateError;
	
	/**
	 * Tests if this and another Coordinate point to almost the same point in space.
	 * Coordinates are compared in the cartesian coordinate system.
	 * Coordinates are considered almost equal if all of their numerical components
	 * are almost equal. Numerical values are considered almost equal if the relative
	 * difference of their values is less than 1E-10 or if they differ by less than
	 * 1E-10.
	 * @return true if both Coordinates are almost equal, else false
	 * @see "https://en.wikipedia.org/wiki/Great-circle_distance#Formulas"
	 * @throws CoordinateError for contract violation in callee (bug in code behind the interface)
	 * @MethodType boolean-query
	 */
	boolean isEqual(Coordinate other) throws CoordinateError;
}
