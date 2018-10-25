package org.wahlzeit.model;

public class Location {
	public final Coordinate coordinate;
	
	public Location(Coordinate coordinate) {
		if(coordinate == null) {
			throw new IllegalArgumentException("Location() requires a non-null coordinate parameter");
		}
		this.coordinate = coordinate;
	}
}
