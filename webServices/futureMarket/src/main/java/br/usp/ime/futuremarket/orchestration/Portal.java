package br.usp.ime.futuremarket.orchestration;

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
    boolean requestPayment(final double amount, CustomerInfo customer);

    @WebMethod
    Purchase smPurchase(ShopList list, CustomerInfo customer);

    @WebMethod
    boolean deliver(Purchase purchase);
}