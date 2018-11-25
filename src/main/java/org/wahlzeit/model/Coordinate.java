package org.wahlzeit.model;

public interface Coordinate {
	double equalityAbsTolerance = 1e-10;
	double equalityRelTolerance = 1e-10;

	/**
	 * Converts the Coordinate to CartesianCoordinate.
	 * @MethodType conversion
	 */
	CartesianCoordinate asCartesianCoordinate();
	
	/**
	 * Returns the cartesian distance between this and another Coordinate.
	 * @MethodType getter
	 */
	double getCartesianDistance(Coordinate other);
	
	/**
	 * Converts the Coordinate to SphericCoordinate.
	 * @MethodType conversion
	 */
	SphericCoordinate asSphericCoordinate();
	
	/**
	 * Returns the central angle between this and another Coordinate.
	 * @MethodType getter
	 */
	double getCentralAngle(Coordinate other);
	
	/**
	 * Tests if this and another Coordinate point to almost the same point in space.
	 * Coordinates are compared in the coordinate system of this Coordinate.
	 * Coordinates are considered almost equal if all of their numerical components
	 * are almost equal. Numerical values are considered almost equal if the relative
	 * difference of their values is less than 1E-10 or if they differ by less than
	 * 1E-10.
	 * @return true if both Coordinates are almost equal, else false
	 * @see "https://en.wikipedia.org/wiki/Great-circle_distance#Formulas"
	 * @MethodType boolean-query
	 */
	boolean isEqual(Coordinate other);
}
