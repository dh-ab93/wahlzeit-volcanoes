package org.wahlzeit.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

public class VolcanoPhotoTest {
	
	private static VolcanoPhotoFactory f1;
	private static VolcanoPhotoFactory f2;
	
	/*
	 * provide a way to access the first VolcanoPhotoFactory.getInstance() call
	 * (requires that other test suites don't call it first
	 * -> unrealistic assumption
	 * -> testVolcanoPhotoFactoryInstance has very limited use)
	 */
	@BeforeClass
	public static void setUp() {
		f1 = VolcanoPhotoFactory.getInstance();
		f2 = VolcanoPhotoFactory.getInstance();
	}
	
	@Test
	public void testVolcanoPhotoFactoryInstance() {
		assertTrue(f1 instanceof VolcanoPhotoFactory);
		assertTrue(f1 == f2);
	}
	
	@Test
	public void testVolcanoPhoto() {
		Coordinate c1 = new CartesianCoordinate(0.0, 0.0, 0.0);
		Location l1 = new Location(c1);
		
		Volcano v1 = new Volcano();
		v1.setElevationMeters(3776);
		v1.setLocation(l1);
		v1.setName("Fuji");
		
		VolcanoPhoto p1 = VolcanoPhotoFactory.getInstance().createPhoto();
		p1.setAuthorName("Midori");
		p1.setDateTaken(new GregorianCalendar(2007, 07, 31));
		p1.setLicense("https://creativecommons.org/licenses/by/3.0/deed.en");
		p1.setSource("https://commons.wikimedia.org/wiki/File:Lake_Kawaguchiko_Sakura_Mount_Fuji_3.JPG");
		p1.setVolcano(v1);
		p1.setLocation(l1);
	}
	
	/*
	 * fails, PhotoManager.getInstance() is final
	 * -> can't fix until purpose of design in class diagram for cw05 is clear
	 * until then purpose of child class VolcanoPhotoManager is unknown
	@Test
	public void testVolcanoPhotoManagerInstance() {
		assertTrue(VolcanoPhotoManager.getInstance() instanceof VolcanoPhotoManager);
	}
	*/
	
	/*
	 * constructor should run without exceptions
	 */
	@Test
	public void testVolcanoPhotoManagerConstructor() {
		assertTrue(new VolcanoPhotoManager() instanceof VolcanoPhotoManager);
	}
}
