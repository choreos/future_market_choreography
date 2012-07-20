package br.usp.ime.futuremarket.choreography.tests.functional;

import static br.usp.ime.futuremarket.choreography.FutureMarket.BANK_ROLE;
import static br.usp.ime.futuremarket.choreography.FutureMarket.BANK_SERVICE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.usp.ime.futuremarket.choreography.Bank;
import br.usp.ime.futuremarket.choreography.CustomerInfo;
import br.usp.ime.futuremarket.choreography.FutureMarket;
import br.usp.ime.futuremarket.choreography.PurchaseInfo;

public class BankTest {
    private static Bank bank;

    @Test
    public void shouldBeRegisteredInRegistry() {
        FutureMarket futureMarket = new FutureMarket();
        bank = futureMarket.getFirstClient(BANK_ROLE, BANK_SERVICE, Bank.class);
    }

    @Test
    public void shouldReturnOkOnSetDelivery() {
        final PurchaseInfo purchaseInfo = new PurchaseInfo();
        final CustomerInfo customerInfo = new CustomerInfo();
        assertEquals("authorized", bank.requestPayment(purchaseInfo, customerInfo));
    }
}