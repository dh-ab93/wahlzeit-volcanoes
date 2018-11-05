package org.wahlzeit.model;

import org.junit.runners.*;
import org.junit.runner.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	org.wahlzeit.model.AccessRightsTest.class,
	org.wahlzeit.model.CoordinateTest.class,
	org.wahlzeit.model.FlagReasonTest.class,
	org.wahlzeit.model.GenderTest.class,
	org.wahlzeit.model.GuestTest.class,
	org.wahlzeit.model.LocationTest.class,
	org.wahlzeit.model.PhotoFilterTest.class,
	org.wahlzeit.model.TagsTest.class,
	org.wahlzeit.model.UserStatusTest.class,
	org.wahlzeit.model.ValueTest.class,
	org.wahlzeit.model.persistence.AllModelPersistenceTests.class
})

public class AllModelTests { /* do nothing */ }
