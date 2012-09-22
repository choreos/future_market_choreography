package br.usp.ime.futuremarket.tests.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ br.usp.ime.futuremarket.tests.integration.choreography.SupermarketTest.class,
        br.usp.ime.futuremarket.tests.integration.orchestration.SupermarketTest.class })
public class AllTests {
}