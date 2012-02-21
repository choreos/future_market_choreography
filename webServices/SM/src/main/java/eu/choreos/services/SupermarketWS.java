package eu.choreos.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import eu.choreos.CustomerInfo;
import eu.choreos.ProductPrice;
import eu.choreos.PurchaseInfo;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface SupermarketWS {

	@WebMethod
	public abstract ProductPrice[] getPrices(String[] products);

	@WebMethod
	public abstract PurchaseInfo purchase(String[] products,
			CustomerInfo customerInfo);

}