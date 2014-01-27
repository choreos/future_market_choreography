package br.usp.ime.futuremarket.tests.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
    private static List<Shipper> shippers;

    @BeforeClass
    public static void getShipper() throws IOException {
        final FutureMarket futureMarket = new FutureMarket();
        final String registry = Configuration.getInstance().getRegistryWsdl();
        futureMarket.setRegistryWsdl(registry);
        shippers = futureMarket.getDependencyByRole(Role.SHIPPER, Shipper.class);
    }

    @Test
    public void shouldExistAShipperInRegistry() throws IOException {
        assertNotNull(shippers);
    }

    @Test
    public void shouldReturnTrueOnSetDelivery() {
        final Purchase purchase = getPurchase(1);
        assertEquals(true, shippers.get(0).deliver(purchase));
    }

    @Test
    public void deliveryShouldPreservePurchaseInfo() {
        final Purchase purchase = getPurchase(2);
        shippers.get(0).deliver(purchase);
        final Delivery delivery = shippers.get(0).getDelivery(purchase);

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