package br.usp.ime.futuremarket;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface Supermarket {

    @WebMethod
    public ProductPrice[] getPrices(String[] products);
    
    @WebMethod
    public PurchaseInfo purchase(String[] products, CustomerInfo customerInfo);
    
}