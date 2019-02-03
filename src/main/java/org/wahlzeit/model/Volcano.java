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

/*
-------------------------------------------------------------------------------
collaboration between Volcano and VolcanoType:

public collaboration Classification {
	public role Classifier {
		public String getName();
	}

	public role Subject {
		public Classifier getClassifier();
	}
}

public class Volcano binds Classification.Subject {
	...
	public VolcanoType getType();
	...
}

public class VolcanoType binds Classification.Classifier, Classification.Subject {
	...
	public String getName();
	public boolean isSubtypeOf(VolcanoType vt);
	public void setSupertype(VolcanoType vt);
	...
}

VolcanoType can be a subtype of another VolcanoType -> a type can be a subject of a classification as well.
(The method names in VolcanoType don't match exactly, but the concept still applies.)
 */

/*
-------------------------------------------------------------------------------
collaboration between Volcano and VolcanoPhoto:

public collaboration PartOf {
	public role Part {}

	public role Whole {
		public Part getPart();
		public void setPart(Part part);
	}
}

public class Volcano binds PartOf.Part { ... }
public class VolcanoPhoto binds PartOf.Whole {
	...
	public Volcano getVolcano();
	public void setVolcano(...);
	...
}
 */

/*
-------------------------------------------------------------------------------
collaboration between Volcano and VolcanoManager:

public collaboration PersistenceManagement {
	public role DataObject {}

	public role Manager {
		// needs to keep track of all DataObject instances from create() or loadDataObjects()
		public DataObject create(); // factory method
		public void loadDataObjects(); // from PersistenceLayer
		public void saveDataObjects(); // to PersistenceLayer
	}

	public role Client {
		// should only use Manager to create new instances of DataObject
	}

	public role PersistenceLayer {
		// calls Manager.load/saveDataObjects() at startup/shutdown
		// during startup: recreates persisted DataObjects and transfers them to Manager
		// during shutdown: persists the DataObjects given by Manager
	}
}

public class Volcano extends DataObject binds PersistenceManagement.DataObject { ... }
public class VolcanoManager extends ObjectManager binds PersistenceManagement.Manager {
	...
	protected HashMap<String, Volcano> existingVolcanoes;

	// factory method
	public Volcano getVolcano(String volcanoName, String volcanoTypeName) { ... }

	protected void loadVolcanoes() { ... }
	protected void saveVolcanoes() { ... }
	...
}
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
