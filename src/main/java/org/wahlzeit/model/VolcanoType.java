package org.wahlzeit.model;

import com.googlecode.objectify.annotation.Entity;
import org.wahlzeit.services.DataObject;

/*
instantiation documentation for VolcanoType class:

intended use: factory method VolcanoManager.getInstance().getVolcanoType(volcanoTypeName)
getVolcanoType() directly calls the VolcanoType(name) constructor, if necessary
(i.e. if it did not find a cached instance with that name)
	delegation: separate object
	selection: on-the-spot
	configuration: in-code
	instantiation: in-code
	initialization: by-fixed-signature (only final fields; non-final fields in-second-step through setter methods)
	building: default

the VolcanoType(name) constructor is accessible as well
(e.g. for test suites, or if object sharing and persistence is not wanted)
 */

@Entity
public class VolcanoType extends DataObject {

	protected final String name;
	protected VolcanoType supertype = null;

	VolcanoType(String name) {
		this.name = name;
	}

	/**
	 * @param vt another volcano type
	 * @return true, iff this volcano type is a subtype of the other volcano type (can be an indirect subtype)
	 * @methodtype boolean-query
	 */
	public boolean isSubtypeOf(VolcanoType vt) {
		if(vt == null || vt == this || supertype == null) {
			return false;
		}
		return supertype == vt || supertype.isSubtypeOf(vt);
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
	public VolcanoType getSupertype() {
		return supertype;
	}

	/**
	 * @methodtype set
	 */
	public void setSupertype(VolcanoType vt) {
		supertype = vt;
	}

}
