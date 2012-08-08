package br.usp.ime.futuremarket.tests.unit;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import br.usp.ime.futuremarket.Bank;
import br.usp.ime.futuremarket.BankImpl;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Purchase;

public class BankTest {
    @Test
    public void shouldDeleteDeliveryStatusAfterRetrieval() throws IOException {
        final Purchase purchase = new Purchase();
        final CustomerInfo customer = new CustomerInfo();
        final Bank bank = new BankImpl(false);

        bank.requestPayment(purchase, customer);
        assertEquals("authorized", bank.requestPayment(purchase, customer));
    }
}