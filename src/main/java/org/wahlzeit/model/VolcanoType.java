package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

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
