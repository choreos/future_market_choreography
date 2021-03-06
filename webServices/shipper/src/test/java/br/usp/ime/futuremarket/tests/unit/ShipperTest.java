package br.usp.ime.futuremarket.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Delivery;
import br.usp.ime.futuremarket.Product;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.Shipper;
import br.usp.ime.futuremarket.ShipperImpl;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.ShopListItem;

public class ShipperTest {
    private Shipper shipper;

    @Before
    public void createShipper() throws IOException {
        shipper = new ShipperImpl();
    }

    @Test
    public void shouldDeleteDeliveryStatusAfterRetrieval() throws IOException {
        final Purchase purchase = getPurchase(41);

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

    private Purchase getPurchase(final int number) {
        final Product product = new Product("ShipperTestProduct");

        final ShopListItem item = new ShopListItem(product);
        item.setSeller("ShipperTestSeller");

        final ShopList list = new ShopList(item);

        return new Purchase(number, "shipper", list, new CustomerInfo());
    }
}