package eu.choreos.services;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.utils.RunWS;

import br.usp.ime.choreos.vv.WSClient;

public class SMRegistryWSTest {
	
	@BeforeClass
	public static void setUp(){
		RunWS.startSMRegistryWS();
	}

	
	@AfterClass
	public static void tearDown(){
		RunWS.stopSMRegistryWS();
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
