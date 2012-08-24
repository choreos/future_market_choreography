package br.usp.ime.futuremarket.tests.integration.choreography;

import org.junit.BeforeClass;

import br.usp.ime.futuremarket.choreography.FutureMarket;
import br.usp.ime.futuremarket.tests.integration.AbstractSupermarketTest;

public class SupermarketTest extends AbstractSupermarketTest {
    @BeforeClass
    public static void setFutureMarket() {
        market = new FutureMarket();
    }
}