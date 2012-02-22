package br.usp.ime.futuremarket;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface Shipper {

    @WebMethod
    public String setDelivery(PurchaseInfo purchaseInfo);

    @WebMethod
    public DeliveryInfo getDeliveryStatus(PurchaseInfo purchaseInfo);

    @WebMethod
    public String getDateAndTime(String id);
}