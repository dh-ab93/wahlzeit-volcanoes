package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Volcano extends DataObject {

	protected String name;
	protected Location location;
	protected int elevationMeters;
	
	/*
	 * public constructor needed for deserialization
	 */
	/**
	 * @methodtype constructor
	 */
	public Volcano() {
	}
	
	/**
	 * @methodtype getter
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @methodtype setter
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @methodtype getter
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @methodtype setter
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * @methodtype getter
	 */
	public int getElevationMeters() {
		return elevationMeters;
	}

	/**
	 * @methodtype setter
	 */
	public void setElevationMeters(int elevationMeters) {
		this.elevationMeters = elevationMeters;
	}
	
}
