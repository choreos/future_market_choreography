package br.usp.ime.futuremarket.choreography;

import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import br.usp.ime.futuremarket.choreography.CustomerInfo;
import br.usp.ime.futuremarket.choreography.DeliveryInfo;
import br.usp.ime.futuremarket.choreography.ProductQuantity;
import br.usp.ime.futuremarket.choreography.PurchaseInfo;
import br.usp.ime.futuremarket.choreography.models.LowestPrice;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public interface Customer {

    @WebMethod
    public LowestPrice getLowestPriceForList(Set<ProductQuantity> products);

    @WebMethod
    public DeliveryInfo getShipmentData(PurchaseInfo purchaseInfo);

    @WebMethod
    public PurchaseInfo[] makePurchase(String listId, CustomerInfo customerInfo);

}
