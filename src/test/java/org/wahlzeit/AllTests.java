package org.wahlzeit;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	org.wahlzeit.handlers.AllHandlerTests.class,
	org.wahlzeit.model.AllModelTests.class,
	org.wahlzeit.services.AllServicesTests.class,
	org.wahlzeit.utils.AllUtilsTests.class
})

public class AllTests { /* do nothing */ }
