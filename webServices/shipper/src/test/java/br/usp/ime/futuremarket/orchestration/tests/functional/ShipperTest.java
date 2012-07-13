package br.usp.ime.futuremarket.orchestration.tests.functional;

import static br.usp.ime.futuremarket.orchestration.FutureMarket.SHIPPER_ROLE;
import static br.usp.ime.futuremarket.orchestration.FutureMarket.SHIPPER_SERVICE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.usp.ime.futuremarket.orchestration.FutureMarket;
import br.usp.ime.futuremarket.orchestration.PurchaseInfo;
import br.usp.ime.futuremarket.orchestration.Shipper;

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