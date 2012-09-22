package br.usp.ime.futuremarket.choreography;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Delivery;
import br.usp.ime.futuremarket.ProductList;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.ShopList;

@WebService
@SOAPBinding
public interface Portal {

	@WebMethod
    ShopList getLowestPrice(ProductList list) throws IOException;

    @WebMethod
    Delivery getDelivery(Purchase purchase) throws MalformedURLException;

    @WebMethod
    Set<Purchase> purchase(ShopList list, CustomerInfo customer) throws IOException;
    
    @WebMethod
    public void register() throws IOException;
    
    @WebMethod
    public void unregister() throws IOException;
    
    
}