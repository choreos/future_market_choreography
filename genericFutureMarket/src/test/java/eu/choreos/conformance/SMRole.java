package eu.choreos.conformance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Test;

import eu.choreos.services.SMRegistryWS;
import eu.choreos.utils.RunWS;
import eu.choreos.vv.abstractor.ConformanceTestCase;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

public class SMRole extends ConformanceTestCase{
	
	private String endpoint;
	
	public SMRole(String entry) {
		super(entry);
		endpoint = entry;
	}
	
	@AfterClass
	public static void tearDown(){
		RunWS.restart(new SMRegistryWS(), "smregistry");
	}

	@Test
	public void shouldHaveASearchForAProduct() throws Exception {
		WSClient supermarketRole = new WSClient(endpoint);
		assertTrue(supermarketRole.getOperations().contains("searchForProduct"));
	}
	
	@Test
	public void shouldRegisterSupermarketIntoSMRegistryWS() throws Exception {
		WSClient supermarketRole = new WSClient(endpoint);
		assertTrue(supermarketRole.getOperations().contains("registerSupermarket"));
	}
	
	@Test
	public void searchForProductShouldReturnAProduct() throws Exception {
		WSClient supermarketRole = new WSClient(endpoint);
		Item response = supermarketRole.request("searchForProduct", "product1");
		Item product = response.getChild("return");
		assertNotNull(product.getChild("name"));
		assertNotNull(product.getChild("price"));
	}
	
	@Test
	public void registerSupermarketShouldReturnATextMessage() throws Exception {
		WSClient supermarketRole = new WSClient(endpoint);
		Item response = supermarketRole.request("registerSupermarket", "a supermarket");
		
		assertEquals("OK", response.getChild("out").getContent());
	}
	
	

}
