package br.usp.ime.futuremarket.tests.functional.orchestration;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import br.usp.ime.futuremarket.Configuration;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.Supermarket;
import br.usp.ime.futuremarket.orchestration.FutureMarket;

public class SupermarketTest {
    @Test
    public void testProxyCreation() throws IOException {
        final FutureMarket market = new FutureMarket();
        final String registry = Configuration.getInstance().getRegistryWsdl();
        market.setRegistryWsdl(registry);

        final Supermarket sm = market.getDependencyByRole(Role.SUPERMARKET, Supermarket.class).get(0);
        assertNotNull(sm);
    }
}