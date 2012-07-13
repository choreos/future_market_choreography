package br.usp.ime.futuremarket.choreography.tests.functional;

import static br.usp.ime.futuremarket.choreography.FutureMarket.SHIPPER_ROLE;
import static br.usp.ime.futuremarket.choreography.FutureMarket.SHIPPER_SERVICE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.usp.ime.futuremarket.choreography.FutureMarket;
import br.usp.ime.futuremarket.choreography.PurchaseInfo;
import br.usp.ime.futuremarket.choreography.Shipper;

public class ShipperTest {
    private static Shipper shipper;

    @Test
    public void shouldBeRegisteredInRegistry() {
        FutureMarket futureMarket = new FutureMarket();
        shipper = futureMarket.getFirstClient(SHIPPER_ROLE, SHIPPER_SERVICE, Shipper.class);
    }

    @Test
    public void shouldReturnOkOnSetDelivery() {
        final PurchaseInfo purchaseInfo = new PurchaseInfo();
        assertEquals("OK", shipper.setDelivery(purchaseInfo));
    }
}