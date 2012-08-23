package br.usp.ime.futuremarket.orchestration.tests.functional;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.Supermarket;
import br.usp.ime.futuremarket.orchestration.FutureMarket;

public class SupermarketTest {
    @Test
    public void testProxyCreation() throws IOException {
        final FutureMarket market = new FutureMarket();
        final Supermarket sm = market.getClientByRole(Role.SUPERMARKET, Supermarket.class);
        assertNotNull(sm);
    }
}