package org.wahlzeit.services;

import org.junit.runners.*;
import org.junit.runner.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	org.wahlzeit.services.EmailAddressTest.class,
	org.wahlzeit.services.LogBuilderTest.class,
	org.wahlzeit.services.mailing.AllServicesMailingTests.class
})

public class AllServicesTests { /* do nothing */ }
