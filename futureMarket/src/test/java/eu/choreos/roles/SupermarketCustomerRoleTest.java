package eu.choreos.roles;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.choreos.vv.WSClient;
import eu.choreos.utils.RunWS;

public class SupermarketCustomerRoleTest {
	
	@BeforeClass
	public static void setUp(){
		RunWS.startSupermarketCustomerWS();
	}
	
	@AfterClass
	public static void tearDown(){
		RunWS.stopSupermarketCustomerWS();
	}

	@Test
	public void shouldHaveAGetPriceOfProductListOperation() throws Exception {
		WSClient supermarketCustomerRole = new WSClient("http://localhost:1234/supermarketCustomerRole?wsdl");
		assertTrue(supermarketCustomerRole.getOperations().contains("getPriceOfProductList"));
	}

}
