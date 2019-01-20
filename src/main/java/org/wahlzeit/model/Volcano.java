package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Volcano extends DataObject {

	protected final String name;
	protected Location location = null;
	protected int elevationMeters = 0;
	protected final VolcanoType type;
	
	Volcano(String name, VolcanoType type) {
		this.name = name;
		this.type = type;
	}
	
	/**
	 * @methodtype get
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @methodtype get
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @methodtype set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * @methodtype get
	 */
	public int getElevationMeters() {
		return elevationMeters;
	}

	/**
	 * @methodtype set
	 */
	public void setElevationMeters(int elevationMeters) {
		this.elevationMeters = elevationMeters;
	}

	/**
	 * @methodtype get
	 */
	public VolcanoType getType() {
		return type;
	}
}
