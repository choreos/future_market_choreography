package br.usp.ime.futuremarket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.futuremarket.choreography.FutureMarket;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
        endpointInterface = "br.usp.ime.futuremarket.Shipper")
public class ShipperImpl implements Shipper {
    private final Map<String, Delivery> deliveries;

    public ShipperImpl() throws IOException {
        this(true);
    }

    // Unit tests won't register
    public ShipperImpl(final boolean useRegistry) throws IOException {
        deliveries = new HashMap<String, Delivery>();

        if (useRegistry) {
            register();
        }
    }

    @WebMethod
    public boolean deliver(final Purchase purchase) {
        final String key = purchase.getUniqueId();
        final Delivery delivery = new Delivery();
        delivery.setPurchase(purchase);

        synchronized (this) {
            deliveries.put(key, delivery);
        }

        return true;
    }

    @WebMethod
    public Delivery getDelivery(final Purchase purchase) {
        final String key = purchase.getUniqueId();

        Delivery delivery;
        synchronized (this) {
            delivery = deliveries.remove(key);
        }

        return delivery;
    }

    private void register() throws IOException {
        final String name = getMyName();
        final FutureMarket futureMarket = new FutureMarket();

        futureMarket.register(Role.SHIPPER, name);
    }

    private String getMyName() throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final Properties properties = new Properties();

        properties.load(loader.getResourceAsStream("shipper.properties"));

        return properties.getProperty("name");
    }
}