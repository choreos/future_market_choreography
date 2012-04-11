package br.usp.ime.futuremarket;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
        endpointInterface = "br.usp.ime.futuremarket.Shipper")
public class ShipperImpl implements Shipper {

    private static final String REL_PATH = "shipper/shipper";
    private final ConcurrentMap<String, DeliveryInfo> deliveryInfoList;
    private long deliveryId;

    public ShipperImpl() {
        this(true);
    }

    public ShipperImpl(final boolean useRegistry) {
        deliveryInfoList = new ConcurrentHashMap<String, DeliveryInfo>();
        deliveryId = 0l;

        if (useRegistry) {
            register();
        }
    }

    private void register() {
        final FutureMarket futureMarket = new FutureMarket();
        futureMarket.register(FutureMarket.SHIPPER_ROLE, REL_PATH);
    }

    @WebMethod
    public String setDelivery(final PurchaseInfo purchaseInfo) {
        final DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setPurchase(purchaseInfo);
        deliveryInfo.setId(Long.toString(getDeliveryId()));
        deliveryInfo.setDate(new Date().toString());
        deliveryInfo.setStatus("done");

        deliveryInfoList.put(purchaseToIdentifier(purchaseInfo), deliveryInfo);

        return "OK";
    }

    private long getDeliveryId() {
    	return Math.round(Math.random() * Math.pow(2, 64));
    }

    @WebMethod
    public DeliveryInfo getDeliveryStatus(final PurchaseInfo purchaseInfo) {
        final String purchaseId = purchaseToIdentifier(purchaseInfo);
        final DeliveryInfo deliveryInfo = deliveryInfoList.get(purchaseId);

        if (deliveryInfo != null) {
            deliveryInfoList.remove(purchaseId);
        }

        return deliveryInfo;
    }

    private String purchaseToIdentifier(PurchaseInfo p) {
        return p.getId() + p.getSellerEndpoint();
    }
}