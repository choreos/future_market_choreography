package br.usp.ime.futuremarket.tests.functional;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import br.usp.ime.futuremarket.Bank;
import br.usp.ime.futuremarket.Configuration;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.choreography.FutureMarket;

public class BankTest {
    private static Bank bank;

    @Test
    public void shouldBeRegisteredInRegistry() throws IOException {
        final FutureMarket futureMarket = new FutureMarket();
        final String registry = Configuration.getInstance().getRegistryWsdl();
        futureMarket.setRegistryWsdl(registry);

        bank = futureMarket.getDependencyByRole(Role.BANK, Bank.class).get(0);

        assertNotNull(bank);
    }
}