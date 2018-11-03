package org.wahlzeit.testSuites;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	org.wahlzeit.services.mailing.EmailServiceTest.class
})

public class EmailServiceTestSuite { /* do nothing */ }
