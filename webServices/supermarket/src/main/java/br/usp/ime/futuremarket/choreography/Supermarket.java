package br.usp.ime.futuremarket.choreography;

import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.usp.ime.futuremarket.choreography.CustomerInfo;
import br.usp.ime.futuremarket.choreography.ProductPrice;
import br.usp.ime.futuremarket.choreography.ProductQuantity;
import br.usp.ime.futuremarket.choreography.PurchaseInfo;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface Supermarket {

    @WebMethod
    public ProductPrice[] getPrices(Set<ProductQuantity> products);

    @WebMethod
    public PurchaseInfo purchase(Set<ProductQuantity> products, CustomerInfo customerInfo);

    @WebMethod
    public String getWsdl();
    
    @WebMethod
    public ProductQuantity[] getStock(); 

}