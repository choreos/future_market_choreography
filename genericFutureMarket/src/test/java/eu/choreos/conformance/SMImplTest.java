package eu.choreos.conformance;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.services.SMRegistryWS;
import eu.choreos.utils.RunWS;
import eu.choreos.vv.abstractor.Role;

public class SMImplTest {
	
	private final String SM1_WSDL = "http://localhost:8084/petals/services/supermarket1?wsdl";
	private final String SM2_WSDL = "http://localhost:8084/petals/services/supermarket2?wsdl";
	
	@BeforeClass
	public static void setUp(){
		RunWS.start(new SMRegistryWS(), "smregistry");
	}
	
	@AfterClass
	public static void tearDown(){
		RunWS.stop("smregistry");
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

}
