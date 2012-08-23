package br.usp.ime.futuremarket.orchestration;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.ShopList;

@WebService
@SOAPBinding
public interface Portal extends br.usp.ime.futuremarket.choreography.Portal {

    @WebMethod
    boolean requestPayment(final double amount, CustomerInfo customer) throws IOException;

    @WebMethod
    Purchase smPurchase(ShopList list, CustomerInfo customer) throws IOException;

    @WebMethod
    boolean deliver(Purchase purchase) throws MalformedURLException;
}