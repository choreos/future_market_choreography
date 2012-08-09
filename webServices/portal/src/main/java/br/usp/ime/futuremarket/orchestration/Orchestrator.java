package br.usp.ime.futuremarket.orchestration;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Delivery;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.ShopList;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface Orchestrator {

    @WebMethod
    ShopList getLowestPrices(ShopList list) throws IOException;

    @WebMethod
    Delivery getShipmentData(Purchase purchase) throws MalformedURLException;

    @WebMethod
    Purchase purchase(ShopList shopList, CustomerInfo customer);

    @WebMethod
    Purchase smPurchase(ShopList shopList, CustomerInfo customer, String baseAddr);

    @WebMethod
    String requestPayment(Purchase purchase, CustomerInfo customerInfo);
}