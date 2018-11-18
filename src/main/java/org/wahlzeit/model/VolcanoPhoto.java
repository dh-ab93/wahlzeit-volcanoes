package org.wahlzeit.model;

import java.util.GregorianCalendar;
import java.util.Date;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class VolcanoPhoto extends Photo {
	
	protected Volcano volcano;
	protected String license;
	protected String authorName;
	protected String source;
	protected GregorianCalendar dateTaken;

	/**
	 * @methodtype constructor
	 */
	public VolcanoPhoto() {
		super();
	}

	/**
	 * @methodtype constructor
	 */
	public VolcanoPhoto(PhotoId myId) {
		super(myId);
	}

	/**
	 * @methodtype getter
	 */
	public Volcano getVolcano() {
		return volcano;
	}

	/**
	 * @methodtype setter
	 */
	public void setVolcano(Volcano volcano) {
		this.volcano = volcano;
		incWriteCount();
	}

	/**
	 * @methodtype getter
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * @methodtype setter
	 */
	public void setLicense(String license) {
		this.license = license;
		incWriteCount();
	}

	/**
	 * @methodtype getter
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @methodtype setter
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
		incWriteCount();
	}

	/**
	 * @methodtype getter
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @methodtype setter
	 */
	public void setSource(String source) {
		this.source = source;
		incWriteCount();
	}

	/**
	 * @methodtype getter
	 */
	public GregorianCalendar getDateTaken() {
		return dateTaken;
	}

	/**
	 * @methodtype setter
	 */
	public void setDateTaken(GregorianCalendar dateTaken) {
		this.dateTaken = dateTaken;
		incWriteCount();
	}
	
}
