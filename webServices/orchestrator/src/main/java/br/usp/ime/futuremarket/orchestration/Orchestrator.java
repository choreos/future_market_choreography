package br.usp.ime.futuremarket.orchestration;

import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import br.usp.ime.futuremarket.orchestration.CustomerInfo;
import br.usp.ime.futuremarket.orchestration.DeliveryInfo;
import br.usp.ime.futuremarket.orchestration.ProductQuantity;
import br.usp.ime.futuremarket.orchestration.PurchaseInfo;
import br.usp.ime.futuremarket.orchestration.models.LowestPrice;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public interface Orchestrator {

    @WebMethod
    public LowestPrice getLowestPriceForList(Set<ProductQuantity> products);

    @WebMethod
    public DeliveryInfo getShipmentData(PurchaseInfo purchaseInfo);

    @WebMethod
    public PurchaseInfo[] makePurchase(String listId, CustomerInfo customerInfo);
    
    @WebMethod
    public PurchaseInfo[] makeSMPurchase(String name, Set<ProductQuantity> products, CustomerInfo customerInfo);

    @WebMethod
	public String requestPayment(PurchaseInfo purchaseInfo, CustomerInfo customerInfo);

}
