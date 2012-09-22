package br.usp.ime.futuremarket.tests.acceptance;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ br.usp.ime.futuremarket.tests.acceptance.choreography.AcceptanceTest.class,
        br.usp.ime.futuremarket.tests.acceptance.orchestration.AcceptanceTest.class })
public class AllTests {

}
