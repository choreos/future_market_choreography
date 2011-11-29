package eu.choreos.compliance;

import static eu.choreos.utils.AcceptanceTestUtils.formatEndpoint;
import static eu.choreos.utils.ChoreographyAbstractor.getChoreography;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.abstractor.Choreography;
import eu.choreos.vv.abstractor.ComplianceTestCase;
import eu.choreos.vv.abstractor.Service;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

public class SMRoleTest extends ComplianceTestCase{
	
	 String endpoint;
	private static WSClient smClient;
	
	public SMRoleTest(String endpoint) {
		super(endpoint);
		this.endpoint = endpoint;
	}
	
	@Before
	public  void tearDown() throws Exception{
		smClient = new WSClient(endpoint);
		smClient.setEndpoint(formatEndpoint(endpoint));
	}
	
	@AfterClass
	public static void cleanRegistry() throws Exception{
		Choreography futureMart = getChoreography(); 
		Service customer = futureMart.getServicesForRole("customer").get(0);
		Service smregisgtry = customer.getServicesForRole("customer").get(0);
		String endpoint = formatEndpoint(smregisgtry.getWSDL());
		
		WSClient client = new WSClient (smregisgtry.getWSDL());
		client.setEndpoint(endpoint);
		client.request("removeSupermarket", "a supermarket endpoint");
	}

	@Test
	public void shouldHaveASearchForAProductOperation() throws Exception {
		assertTrue(smClient.getOperations().contains("searchForProduct"));
	}
	
	@Test
	public void shouldRegisterSupermarketIntoSMRegistryWS() throws Exception {
		assertTrue(smClient.getOperations().contains("registerSupermarket"));
	}
	

	@Test
	public void shouldHaveAPurchaseOperation() throws Exception {
		assertTrue(smClient.getOperations().contains("purchase"));
	}
	
	@Test
	public void searchForProductShouldReturnAProduct() throws Exception {
		Item response = smClient.request("searchForProduct", "product1");
		Item product = response.getChild("return");
		
		assertNotNull(product.getChild("name"));
		assertNotNull(product.getChild("price"));
	}
	
	@Test
	public void registerSupermarketShouldReturnATextMessage() throws Exception {
		Item response = smClient.request("registerSupermarket", "a supermarket endpoint");
		
		assertEquals("OK", response.getChild("out").getContent());
	}
	
	

}
