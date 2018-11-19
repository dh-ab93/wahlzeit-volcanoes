package org.wahlzeit.model;

import java.util.logging.Logger;

import org.wahlzeit.services.LogBuilder;

public class VolcanoPhotoFactory extends PhotoFactory {
	/*
	 * no new functionality, just need to replicate all static fields and methods
	 * of its parent class, and retrofit some methods from Photo to VolcanoPhoto
	 */
	
	private static final Logger log = Logger.getLogger(VolcanoPhotoFactory.class.getName());
	
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	private static final VolcanoPhotoFactory instance = new VolcanoPhotoFactory();

	protected VolcanoPhotoFactory() {
		// do nothing
	}
	
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	public static void initialize() {
		getInstance(); // drops result due to getInstance() side-effects
	}
	
	/**
	 * Public singleton access method.
	 */
	public static synchronized VolcanoPhotoFactory getInstance() {
		return instance;
	}

	/**
	 * @methodtype factory
	 */
	@Override
	public VolcanoPhoto createPhoto() {
		return new VolcanoPhoto();
	}

	/**
	 * Creates a new photo with the specified id
	 */
	@Override
	public VolcanoPhoto createPhoto(PhotoId id) {
		return new VolcanoPhoto(id);
	}
	
}
