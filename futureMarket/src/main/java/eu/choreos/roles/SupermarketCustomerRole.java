package eu.choreos.roles;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class SupermarketCustomerRole {
	
	@WebMethod
	public double getPriceOfProductList(List<String> productNames){
		return 0.0;
	}
}
