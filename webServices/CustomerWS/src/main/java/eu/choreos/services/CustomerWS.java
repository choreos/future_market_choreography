package eu.choreos.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import eu.choreos.CustomerInfo;
import eu.choreos.DeliveryInfo;
import eu.choreos.PurchaseInfo;
import eu.choreos.models.LowestPrice;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface CustomerWS {
	
	@WebMethod
	public LowestPrice getLowestPriceForList(String[] products);
	
	@WebMethod
	public DeliveryInfo getShipmentData(PurchaseInfo purchaseInfo);
	
	@WebMethod
	public PurchaseInfo[] makePurchase(String listId, CustomerInfo customerInfo);

}
