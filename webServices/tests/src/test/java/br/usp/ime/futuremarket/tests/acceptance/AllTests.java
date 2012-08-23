package br.usp.ime.futuremarket.tests.acceptance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ br.usp.ime.futuremarket.choreography.tests.acceptance.AcceptanceTest.class,
        br.usp.ime.futuremarket.orchestration.tests.acceptance.AcceptanceTest.class })
public class AllTests {

}
