package org.wahlzeit.model;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VolcanoManagerTest {
	private static VolcanoManager m1;
	private static VolcanoManager m2;

	@BeforeClass
	public static void setUp() {
		m1 = VolcanoManager.getInstance();
		m2 = VolcanoManager.getInstance();
	}

	@Test
	public void testVolcanoManagerInstance() {
		assertTrue(m1 instanceof VolcanoManager);
		assertTrue(m1 == m2);
	}

	@Test
	public void testVolcanoTypeFactory() {
		VolcanoType vt1 = m1.getVolcanoType("stratovolcano");
		VolcanoType vt2 = m1.getVolcanoType("stratovolcano");
		VolcanoType vt3 = m1.getVolcanoType("conic volcano");
		vt1.setSupertype(vt3);
		assertTrue(vt1.getName().equals("stratovolcano"));
		assertTrue(vt1 == vt2);
		assertTrue(vt1.isSubtypeOf(vt3));
		assertTrue(vt2.isSubtypeOf(vt3));
		assertFalse(vt3.isSubtypeOf(vt3));
		assertFalse(vt3.isSubtypeOf(vt1));
		assertFalse(vt3.isSubtypeOf(null));
	}

	@Test
	public void testVolcanoFactory() {
		Volcano v1 = m1.getVolcano("Fuji", "stratovolcano");
		Volcano v2 = m1.getVolcano("Fuji", "stratovolcano");
		assertTrue(v1.getName().equals("Fuji"));
		assertTrue(v2.getName().equals("Fuji"));
		assertTrue(v1 == v2);
		assertTrue(v1.getType().getName().equals("stratovolcano"));
	}
}
