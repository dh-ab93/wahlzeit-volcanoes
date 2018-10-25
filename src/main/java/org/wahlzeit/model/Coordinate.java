package org.wahlzeit.model;

public class Coordinate {
	public final double x, y, z;
	
	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getDistance(Coordinate other) {
		double distX = this.x - other.x;
		double distY = this.y - other.y;
		double distZ = this.z - other.z;
		return Math.sqrt(distX * distX + distY * distY + distZ * distZ);
	}
	
	public boolean isEqual(Object other) {
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
}
