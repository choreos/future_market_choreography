package br.usp.ime.futuremarket.choreography;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import br.usp.ime.futuremarket.CustomerInfo;
import br.usp.ime.futuremarket.Delivery;
import br.usp.ime.futuremarket.Purchase;
import br.usp.ime.futuremarket.ShopList;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)

public interface Broker {

    @WebMethod
    public ShopList getLowestPrice(ShopList list) throws IOException;

    @WebMethod
    public Delivery getDelivery(Purchase purchase) throws MalformedURLException;

    @WebMethod
    public Set<Purchase> purchase(ShopList list, CustomerInfo customer) throws IOException;
}