package br.usp.ime.futuremarket.tests.integration.orchestration;

import org.junit.BeforeClass;

import br.usp.ime.futuremarket.orchestration.FutureMarket;
import br.usp.ime.futuremarket.tests.integration.AbstractSupermarketTest;

public class SupermarketTest extends AbstractSupermarketTest {
    @BeforeClass
    public static void setFutureMarket() {
        market = new FutureMarket();
    }
}