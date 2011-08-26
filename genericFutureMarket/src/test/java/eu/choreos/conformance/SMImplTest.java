package eu.choreos.conformance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.services.SMRegistryWS;
import eu.choreos.services.ShipperWS;
import eu.choreos.utils.RunWS;
import eu.choreos.vv.abstractor.Role;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;

public class SMImplTest {
	
	private final String SM1_WSDL = "http://localhost:8084/petals/services/supermarket1?wsdl";
	private final String SM2_WSDL = "http://localhost:8084/petals/services/supermarket2?wsdl";
	
	@BeforeClass
	public static void setUp(){
		RunWS.start(new SMRegistryWS(), "smregistry");
		RunWS.start(new ShipperWS(), "shipperWS");
	}
	
	@AfterClass
	public static void tearDown(){
		RunWS.stop("smregistry");
		RunWS.stop("shipperWS");
	}
	
	@Test
	public void sm1ShouldPlayTheSupermarketRole() throws Exception{
		// Future =p
		// Choreography futureMart = new Choreography("xxxx");
		// Role supermarket = futureMart.findRole("supermarket", 1);
		
		Role supermarket = new Role("supermarket",SM1_WSDL);
		assertTrue(supermarket.runTests(SMRole.class));
	}
	
	@Test
	public void sm2ShouldPlayTheSupermarketRole() throws Exception{
		Role supermarket = new Role("supermarket",SM2_WSDL);
		assertTrue(supermarket.runTests(SMRole.class));
	}
	
	@Test
	public void sm1ShouldReturnShipperEndpointWhenReceivingAPurchaseRequest() throws Exception{
		// Role supermarket = futureMart.findRole("supermarket", 1)
		// supermarket.request
		
		WSClient sm1 = new WSClient(SM1_WSDL);
		
		Item request = new ItemImpl("purchase");
		Item orderID = new ItemImpl("id");
		orderID.setContent("1");
		request.addChild(orderID);
		
		Item personalData = getPersonalData();
		request.addChild(personalData);
		
		Item response = sm1.request("purchase", request);
		
		//Nao tenho como saber se ele vai se comunicar mesmo com o papel shipper
		assertEquals("Shipper1", response.getChild("confirmation").getContent());
		
	}

	private Item getPersonalData() {
		Item personalData = new ItemImpl("data");
		
		Item name = new ItemImpl("name");
		name.setContent("Audrey H. Bowers");
		personalData.addChild(name);
		
		Item address = new ItemImpl("address");
		address.setContent("2421 West Drive");
		personalData.addChild(address);
		
		Item zipCode = new ItemImpl("zipcode");
		zipCode.setContent("60606");
		personalData.addChild(zipCode);
		return personalData;
	}
}
