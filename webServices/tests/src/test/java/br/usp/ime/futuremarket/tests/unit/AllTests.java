package br.usp.ime.futuremarket.tests.unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 * To update test classes, run the command below
 * 
 * @author cadu
 */
// find webServices/*/src/test/java/br/usp/ime/futuremarket/tests/unit/ -name "*.java" | cut -d\/ -f12 | grep -v AllTests.java | sed 's/.java/.class/g' | paste -s -d,

@RunWith(Suite.class)
@SuiteClasses({ ProductTest.class, RoleTest.class, RegistryTest.class, ShipperTest.class,
        StockTest.class, br.usp.ime.futuremarket.tests.unit.choreography.WSInfoTest.class,
        br.usp.ime.futuremarket.tests.unit.orchestration.WSInfoTest.class })
public class AllTests {
}