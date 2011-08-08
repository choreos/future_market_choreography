package eu.choreos.roles;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.Item;
import eu.choreos.vv.WSClient;
import eu.choreos.utils.RunWS;

public class SupermarketRoleTest {
	
	private final String END_POINT = "http://localhost:1234/supermarketRole?wsdl";
	
	@BeforeClass
	public static void setUp(){
		RunWS.start(new SupermarketRole(), "supermarketRole");
	}
	
	@AfterClass
	public static void tearDown(){
		RunWS.stop("supermarketRole");
	}

	@Test
	public void shouldHaveASearchForAProduct() throws Exception {
		WSClient supermarketRole = new WSClient(END_POINT);
		assertTrue(supermarketRole.getOperations().contains("searchForProduct"));
	}
	
	@Test
	public void searchForProductShouldReturnAProduct() throws Exception {
		WSClient supermarketRole = new WSClient(END_POINT);
		Item response = supermarketRole.request("searchForProduct", "a product");
		Item product = response.getChild("return");
		assertNotNull(product.getChild("name"));
		assertNotNull(product.getChild("price"));
	}
	
	@Test
	public void shouldRegisterSupermarketIntoSMRegistryWS() throws Exception {
		WSClient supermarketRole = new WSClient(END_POINT);
		assertTrue(supermarketRole.getOperations().contains("registerSupermarket"));
	}
	

}
