package br.usp.ime.futuremarket;

import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface Supermarket {

    @WebMethod
    ShopList getPrices(ShopList shopList);

    @WebMethod
    Purchase purchase(ShopList shopList, CustomerInfo customerInfo) throws IOException;

    @WebMethod
    void reset();

}