package org.wahlzeit.model;

public class Coordinate {
	/**
	 * make Coordinate immutable
	 * +: need to check values of x, y, z only once (constructor)
	 */
	public final double x, y, z;
	
	public Coordinate(double x, double y, double z) {
		if(isIllegalValue(x) || isIllegalValue(y) || isIllegalValue(z)) {
			throw new IllegalArgumentException();
		}
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getDistance(Coordinate other) {
		if(other == null) {
			throw new IllegalArgumentException("null is an illegal argument");
		}
		double distX = this.x - other.x;
		double distY = this.y - other.y;
		double distZ = this.z - other.z;
		return Math.sqrt(distX * distX + distY * distY + distZ * distZ);
	}
	
	public boolean isEqual(Object other) {
		if(other == null) {
			return false;
		}
		if(other == this) {
			return true;
		}
		if(!(other instanceof Coordinate)) {
			return false;
		}
		Coordinate o = (Coordinate) other;
		return this.x == o.x && this.y == o.y && this.z == o.z;
	}
	
	@Override
	public boolean equals(Object other) {
		return isEqual(other);
	}
	
	/**
	 * Checks for illegal double values
	 * @param v: double value to check
	 * @return True if v is NaN or (pos./neg.) infinity, False otherwise
	 */
	private boolean isIllegalValue(double v) {
		return ! Double.isFinite(v);
	}
}
