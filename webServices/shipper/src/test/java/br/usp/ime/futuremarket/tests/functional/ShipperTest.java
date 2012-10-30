package br.usp.ime.futuremarket.tests.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.Configuration;
import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Delivery;
import br.usp.ime.futuremarket.Product;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.Shipper;
import br.usp.ime.futuremarket.ShopList;
import br.usp.ime.futuremarket.ShopListItem;
import br.usp.ime.futuremarket.choreography.FutureMarket;

public class ShipperTest {
    private static Shipper shipper;

    @BeforeClass
    public static void getShipper() throws IOException {
        final FutureMarket futureMarket = new FutureMarket();
        final String registry = Configuration.getInstance().getRegistryWsdl();
        futureMarket.setRegistryWsdl(registry);
        shipper = futureMarket.getClientByRole(Role.SHIPPER, Shipper.class);
    }

    @Test
    public void shouldExistAShipperInRegistry() throws IOException {
        assertNotNull(shipper);
    }

    @Test
    public void shouldReturnTrueOnSetDelivery() {
        final Purchase purchase = getPurchase(1);
        assertEquals(true, shipper.deliver(purchase));
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
        final Product product = new Product("ShipperTestProduct");

        final ShopListItem item = new ShopListItem(product);
        item.setSeller("ShipperTestSeller");

        final ShopList list = new ShopList(item);

        return new Purchase(number, "shipper", list, new CustomerInfo());
    }
}