package br.usp.ime.futuremarket.tests.functional;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.usp.ime.futuremarket.choreography.tests.functional.BrokerTest;

@RunWith(Suite.class)
@SuiteClasses({ BankTest.class, BrokerTest.class, RegistryTest.class, ShipperTest.class })
public class AllFunctionalTests {
}