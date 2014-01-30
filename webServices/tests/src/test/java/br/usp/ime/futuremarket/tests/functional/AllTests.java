package br.usp.ime.futuremarket.tests.functional;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 * Before run these tests, run TestChoreographyEnacter.
 */
@RunWith(Suite.class)
@SuiteClasses({ BankTest.class, RegistryTest.class, ShipperTest.class,
        br.usp.ime.futuremarket.tests.functional.choreography.PortalTest.class,
        br.usp.ime.futuremarket.tests.functional.orchestration.PortalTest.class,
        br.usp.ime.futuremarket.tests.functional.choreography.SupermarketTest.class,
        br.usp.ime.futuremarket.tests.functional.orchestration.SupermarketTest.class })
public class AllTests {
}