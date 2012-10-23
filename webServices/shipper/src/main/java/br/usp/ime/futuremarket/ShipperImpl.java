package br.usp.ime.futuremarket;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.futuremarket.choreography.FutureMarket;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br/shipper",
        endpointInterface = "br.usp.ime.futuremarket.Shipper")
public class ShipperImpl implements Shipper {
    private final Map<String, Delivery> deliveries;

    public ShipperImpl() throws IOException {
        deliveries = new HashMap<String, Delivery>();
    }
    
    @Override
    public String setInvocationAddress(String registerWsdl) throws IOException {
        final String name = getMyName();
        final FutureMarket futureMarket = new FutureMarket();

        futureMarket.register(name,registerWsdl);
        return "OK";
    }

    @WebMethod
    public boolean deliver(final Purchase purchase) {
        final String key = purchase.getUniqueId();
        final Delivery delivery = new Delivery();
        delivery.setPurchase(purchase);
        delivery.setDate(getToday());

        synchronized (deliveries) {
            deliveries.put(key, delivery);
        }

        return true;
    }

    private Calendar getToday() {
        final Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    @WebMethod
    public Delivery getDelivery(final Purchase purchase) {
        final String key = purchase.getUniqueId();

        Delivery delivery;
        synchronized (deliveries) {
            delivery = deliveries.remove(key);
        }

        if (delivery != null) {
            delivery.setStatus("delivered");
        }

        return delivery;
    }


    private String getMyName() throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final Properties properties = new Properties();

        properties.load(loader.getResourceAsStream("shipper.properties"));

        return properties.getProperty("name");
    }


}