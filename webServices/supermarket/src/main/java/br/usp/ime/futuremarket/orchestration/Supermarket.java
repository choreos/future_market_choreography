package br.usp.ime.futuremarket.orchestration;

import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.usp.ime.futuremarket.orchestration.CustomerInfo;
import br.usp.ime.futuremarket.orchestration.ProductPrice;
import br.usp.ime.futuremarket.orchestration.ProductQuantity;
import br.usp.ime.futuremarket.orchestration.PurchaseInfo;

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
    
    @WebMethod
    public void reset();

}