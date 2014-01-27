package br.usp.ime.futuremarket;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding
public interface Supermarket extends EnactmentEngine {

    @WebMethod
    ShopList getPrices(ShopList shopList);

    @WebMethod
    Purchase purchase(ShopList shopList, CustomerInfo customerInfo);

    @WebMethod
    void reset();

}