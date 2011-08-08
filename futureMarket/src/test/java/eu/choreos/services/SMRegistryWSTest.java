package eu.choreos.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.WSClient;
import eu.choreos.utils.RunWS;

public class SMRegistryWSTest {
	
	@BeforeClass
	public static void setUp(){
		RunWS.start(new SMRegistryWS(), "smregistry");
	}

	
	@AfterClass
	public static void tearDown(){
		RunWS.stop("smregistry");
	}

	@Test
	public void shouldRegisterASupermarketWS() throws Exception{
		WSClient registry = new WSClient("http://localhost:1234/smregistry?wsdl");
		
		assertNull(registry.request("getList").getContent());
		
		registry.request("addSupermarket", "http://localhost/asupermarket?wsdl");
		String smEndPoint = registry.request("getList").getChild("return").getContent();
		
		assertEquals("http://localhost/asupermarket?wsdl", smEndPoint);
	}

}
