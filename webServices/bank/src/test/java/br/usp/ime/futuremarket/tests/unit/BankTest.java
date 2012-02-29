package br.usp.ime.futuremarket.tests.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.usp.ime.futuremarket.Bank;
import br.usp.ime.futuremarket.BankImpl;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.PurchaseInfo;

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