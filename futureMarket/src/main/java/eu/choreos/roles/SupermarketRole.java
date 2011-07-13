package eu.choreos.roles;

import javax.jws.WebMethod;
import javax.jws.WebService;

import eu.choreos.models.Product;

@WebService
public class SupermarketRole {
	
	@WebMethod
	public Product searchForProduct(String name){
		return new Product();
	}

}
