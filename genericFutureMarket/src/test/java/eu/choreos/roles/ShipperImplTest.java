package eu.choreos.roles;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.services.ShipperWS;
import eu.choreos.utils.RunWS;
import eu.choreos.vv.Item;
import eu.choreos.vv.ItemImpl;
import eu.choreos.vv.WSClient;

public class ShipperImplTest {

	private final String SHIPPER_WSDL = "http://localhost:8084/petals/services/shipper1?wsdl";
	
	@Before
	public void setUp(){
		RunWS.start(new ShipperWS(), "shipperWS");
	}
	
	@After
	public void tearDown(){
		RunWS.stop("shipperWS");
	}
	
	@Test
	public void shouldReturnTheCorrectDeliveryTimeAndDate() throws Exception {
		WSClient shipper = new WSClient(SHIPPER_WSDL);
		
		Item request = new ItemImpl("setDeliveryData");
		Item orderID = new ItemImpl("id");
		orderID.setContent("1");
		request.addChild(orderID);
		
		Item personalData = getPersonalData();
		request.addChild(personalData);
		
		shipper.request("setDeliveryData", request);
		Item response = shipper.request("getDeliveryData", "1");
		
		assertEquals("Sat Dec 24 20:15:00 BRST 2011", response.getChild("result").getContent());
	}

	
	private Item getPersonalData() {
		Item personalData = new ItemImpl("personalData");
		
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
