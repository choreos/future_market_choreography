package br.usp.ime.futuremarket.orchestration.tests.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.usp.ime.futuremarket.orchestration.Bank;
import br.usp.ime.futuremarket.orchestration.BankImpl;
import br.usp.ime.futuremarket.orchestration.CustomerInfo;
import br.usp.ime.futuremarket.orchestration.PurchaseInfo;

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