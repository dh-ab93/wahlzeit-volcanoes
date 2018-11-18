package org.wahlzeit.model;

public interface Coordinate {
	/**
	 * Converts the Coordinate to CartesianCoordinate.
	 * @methodtype conversion
	 */
	public abstract CartesianCoordinate asCartesianCoordinate();
	
	/**
	 * Returns the cartesian distance between this and another Coordinate.
	 * @methodtype getter
	 */
	public abstract double getCartesianDistance(Coordinate other);
	
	/**
	 * Converts the Coordinate to SphericCoordinate.
	 * @methodtype conversion
	 */
	public abstract SphericCoordinate asSphericCoordinate();
	
	/**
	 * Returns the central angle between this and another Coordinate.
	 * @methodtype getter
	 */
	public abstract double getCentralAngle(Coordinate other);
	
	/**
	 * Tests if this and another Coordinate point to almost the same point in space.
	 * Coordinates are compared in the coordinate system of this Coordinate.
	 * Coordinates are considered almost equal if all of their numerical components
	 * are almost equal. Numerical values are considered almost equal if their
	 * values differ by less than 1E-10 and (for non-zero values of equal sign only) their
	 * relative difference is less than 1E-10.
	 * @return true if both Coordinates are almost equal, else false
	 * @see https://en.wikipedia.org/wiki/Great-circle_distance#Formulas
	 * @methodtype boolean-query
	 */
	public abstract boolean isEqual(Coordinate other);
}
