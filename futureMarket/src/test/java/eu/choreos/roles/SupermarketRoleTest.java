package eu.choreos.roles;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.choreos.vv.Item;
import br.usp.ime.choreos.vv.WSClient;
import eu.choreos.utils.RunWS;

public class SupermarketRoleTest {
	
	@BeforeClass
	public static void setUp(){
		RunWS.startSupermarketWS();
	}
	
	@AfterClass
	public static void tearDown(){
		RunWS.stopSupermarketWS();
	}

	@Test
	public void shouldHaveASearchForAProduct() throws Exception {
		WSClient supermarketRole = new WSClient("http://localhost:1234/supermarketRole?wsdl");
		assertTrue(supermarketRole.getOperations().contains("searchForProduct"));
	}
	
	@Test
	public void searchForProductShouldReturnAProduct() throws Exception {
		WSClient supermarketRole = new WSClient("http://localhost:1234/supermarketRole?wsdl");
		Item response = supermarketRole.request("searchForProduct", "a product");
		Item product = response.getChild("return");
		assertNotNull(product.getChild("name"));
		assertNotNull(product.getChild("price"));
	}
	

}
