package br.usp.ime.futuremarket.choreography.tests.unit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import br.usp.ime.futuremarket.choreography.PurchaseInfo;
import br.usp.ime.futuremarket.choreography.Shipper;
import br.usp.ime.futuremarket.choreography.ShipperImpl;

public class ShipperTest {
    @Test
    public void shouldDeleteDeliveryStatusAfterRetrieval() {
        final PurchaseInfo purchaseInfo = new PurchaseInfo();
        final Shipper shipper = new ShipperImpl(false);

        shipper.setDelivery(purchaseInfo);
        assertNotNull(shipper.getDeliveryStatus(purchaseInfo));
        assertNull(shipper.getDeliveryStatus(purchaseInfo));
    }
}