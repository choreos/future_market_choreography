package br.usp.ime.futuremarket.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.usp.ime.futuremarket.Delivery;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.Shipper;
import br.usp.ime.futuremarket.ShipperImpl;

public class ShipperTest {
    private Shipper shipper;

    @Before
    public void createShipper() throws IOException {
        shipper = new ShipperImpl(false);
    }

    @Test
    public void shouldDeleteDeliveryStatusAfterRetrieval() throws IOException {
        final Purchase purchase = getPurchase(1);

        shipper.deliver(purchase);

        assertNotNull(shipper.getDelivery(purchase));
        assertNull(shipper.getDelivery(purchase));
    }

    @Test
    public void deliveryShouldPreservePurchaseInfo() throws IOException {
        final Purchase purchase = getPurchase(42);
        shipper.deliver(purchase);
        final Delivery delivery = shipper.getDelivery(purchase);

        assertEquals(purchase.getNumber(), delivery.getPurchase().getNumber());
    }

    private Purchase getPurchase(final long number) {
        final Purchase purchase = new Purchase();
        purchase.setNumber(number);

        return purchase;
    }
}