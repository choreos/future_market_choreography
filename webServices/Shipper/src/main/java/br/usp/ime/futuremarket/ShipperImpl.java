package br.usp.ime.futuremarket;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
        endpointInterface = "br.usp.ime.futuremarket.Shipper")
public class ShipperImpl extends FutureMarket implements Shipper {

    private static final String REL_PATH = "shipper/shipper";
    private static final String ROLE = "Shipper";

    HashMap<Integer, String> deliveries = new HashMap<Integer, String>();
    private final HashMap<String, DeliveryInfo> deliveryInfoList;
    private long id = 1L;

    public ShipperImpl() {
        super.register(ROLE, REL_PATH);
        deliveryInfoList = new HashMap<String, DeliveryInfo>();
    }

    @WebMethod
    public String setDelivery(PurchaseInfo purchaseInfo) {
        DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setPurchase(purchaseInfo);
        deliveryInfo.setId("" + id++);
        deliveryInfo.setDate(new Date().toString());
        deliveryInfo.setStatus("done");

        deliveryInfoList.put(purchaseToIdentifier(purchaseInfo), deliveryInfo);

        return "OK";
    }

    @WebMethod
    public DeliveryInfo getDeliveryStatus(PurchaseInfo purchaseInfo) {
        if (deliveryInfoList.containsKey(purchaseToIdentifier(purchaseInfo))) {
            return deliveryInfoList.get(purchaseToIdentifier(purchaseInfo));
        }
        return null;
    }

    @WebMethod
    public String getDateAndTime(String id) {
        String zipCode = deliveries.get(Integer.parseInt(id));

        Calendar c = null;

        if (Integer.parseInt(zipCode.substring(0, 3)) <= 300)
            c = setTime(8, 12, 32);
        else if (Integer.parseInt(zipCode.substring(0, 3)) <= 600)
            c = setTime(15, 30, 42);
        else if (Integer.parseInt(zipCode.substring(0, 3)) <= 750)
            c = setTime(20, 15, 00);
        else
            c = setTime(22, 30, 00);

        return c.getTime().toString();
    }

    private Calendar setTime(int hour, int minute, int second) {
        Calendar c1 = new GregorianCalendar(2011, Calendar.DECEMBER, 24);
        c1.set(2011, Calendar.DECEMBER, 24, hour, minute, second);

        return c1;
    }

    private String purchaseToIdentifier(PurchaseInfo p) {
        return p.getId() + p.getSellerEndpoint();
    }
}