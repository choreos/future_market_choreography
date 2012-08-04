package br.usp.ime.futuremarket.tests.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.Delivery;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.ServiceName;
import br.usp.ime.futuremarket.Shipper;
import br.usp.ime.futuremarket.choreography.FutureMarket;

public class ShipperTest {
    private static Shipper shipper;

    @BeforeClass
    public static void getShipper() throws IOException {
        final FutureMarket futureMarket = new FutureMarket();
        shipper = futureMarket.getClientByRole(Role.SHIPPER, ServiceName.SHIPPER, Shipper.class);
    }

    @Test
    public void shouldExistAShipperInRegistry() throws IOException {
        assertNotNull(shipper);
    }

    @Test
    public void shouldReturnOkOnSetDelivery() {
        final Purchase purchase = getPurchase(1);
        assertEquals("OK", shipper.deliver(purchase));
    }

    @Test
    public void deliveryShouldPreservePurchaseInfo() {
        final Purchase purchase = getPurchase(2);
        shipper.deliver(purchase);
        final Delivery delivery = shipper.getDelivery(purchase);

        assertNotNull(delivery);
        assertNotNull(delivery.getPurchase());
        assertEquals(purchase.getNumber(), delivery.getPurchase().getNumber());
    }

    private Purchase getPurchase(final long number) {
        final Purchase purchase = new Purchase();
        purchase.setNumber(number);

        return purchase;
    }
}