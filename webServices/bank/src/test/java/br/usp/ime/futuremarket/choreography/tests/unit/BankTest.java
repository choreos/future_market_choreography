package br.usp.ime.futuremarket.choreography.tests.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.usp.ime.futuremarket.choreography.Bank;
import br.usp.ime.futuremarket.choreography.BankImpl;
import br.usp.ime.futuremarket.choreography.CustomerInfo;
import br.usp.ime.futuremarket.choreography.PurchaseInfo;

public class BankTest {
    @Test
    public void shouldDeleteDeliveryStatusAfterRetrieval() {
        final PurchaseInfo purchaseInfo = new PurchaseInfo();
        final CustomerInfo customerInfo = new CustomerInfo();
        final Bank bank = new BankImpl(false);

        bank.requestPayment(purchaseInfo, customerInfo);
        assertEquals("authorized", bank.requestPayment(purchaseInfo, customerInfo));
    }
}