package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import com.googlecode.objectify.annotation.Entity;

/*
instantiation documentation for Volcano class:

intended use: factory method VolcanoManager.getInstance().getVolcano(volcanoName, volcanoTypeName)
getVolcano() directly calls the Volcano(name, type) constructor, if necessary
(i.e. if it did not find a cached instance with that name)
	delegation: separate object
	selection: on-the-spot
	configuration: in-code
	instantiation: in-code
	initialization: by-fixed-signature (only final fields; non-final fields in-second-step through setter methods)
	building: default

the Volcano(name, type) constructor is accessible as well
(e.g. for test suites, or if object sharing and persistence is not wanted)
 */

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
