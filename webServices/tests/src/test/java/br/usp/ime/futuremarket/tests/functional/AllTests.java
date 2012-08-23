package br.usp.ime.futuremarket.tests.functional;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BankTest.class, RegistryTest.class, ShipperTest.class,
        br.usp.ime.futuremarket.choreography.tests.functional.PortalTest.class,
        br.usp.ime.futuremarket.orchestration.tests.functional.PortalTest.class,
        br.usp.ime.futuremarket.choreography.tests.functional.SupermarketTest.class,
        br.usp.ime.futuremarket.orchestration.tests.functional.SupermarketTest.class })
public class AllTests {
}