package eu.choreos.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import eu.choreos.DeliveryInfo;
import eu.choreos.PurchaseInfo;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface ShipperWS {
	
	@WebMethod
	public String setDelivery(PurchaseInfo purchaseInfo);
	
	@WebMethod
	public DeliveryInfo getDeliveryStatus(PurchaseInfo purchaseInfo);

	@WebMethod
	public String getDateAndTime(String id);

}
