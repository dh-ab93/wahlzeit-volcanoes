package org.wahlzeit.model;

import java.util.logging.Logger;

public class VolcanoPhotoManager extends PhotoManager {
	
	private static final VolcanoPhotoManager instance = new VolcanoPhotoManager();

	private static final Logger log = Logger.getLogger(VolcanoPhotoManager.class.getName());

	public VolcanoPhotoManager() {
		super();
	}
	
	public static synchronized VolcanoPhotoManager getInstance() {
		return instance;
	}
	
	/*
	 * overriding methods of parent class quickly leads to dead ends because of final methods
	 */
}
