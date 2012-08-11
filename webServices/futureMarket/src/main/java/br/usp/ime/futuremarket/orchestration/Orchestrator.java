package br.usp.ime.futuremarket.orchestration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

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
    ShopList getLowestPrice(ShopList list) throws IOException;

    @WebMethod
    boolean requestPayment(final double amount, CustomerInfo customer) throws IOException;

    @WebMethod
    Set<Purchase> purchase(ShopList list, CustomerInfo customer) throws IOException;

    @WebMethod
    Purchase smPurchase(ShopList list, CustomerInfo customer) throws IOException;

    @WebMethod
    boolean deliver(Purchase purchase) throws MalformedURLException;

    @WebMethod
    Delivery getDelivery(Purchase purchase) throws MalformedURLException;
}