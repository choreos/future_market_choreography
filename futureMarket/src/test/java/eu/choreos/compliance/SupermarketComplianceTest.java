package eu.choreos.compliance;

import static eu.choreos.utils.ChoreographyAbstractor.getChoreography;
import static eu.choreos.vv.assertions.RehearsalAsserts.assertRole;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.abstractor.Choreography;
import eu.choreos.vv.abstractor.Role;
import eu.choreos.vv.abstractor.Service;

public class SupermarketComplianceTest {
	
	private static Choreography futureMarket;
	private static Role supermarket;
	
	@BeforeClass
	public static void setUp() {
		futureMarket = getChoreography();
		supermarket = new Role("supermarket", "./roles/supermarket?wsdl"); 
	}
	
	@Test
	public void servicesMustBeCompliantWithTheSupermarketRole() throws Exception {
		List<Service> supermarkets = futureMarket.getServicesForRole("supermarket");
		for (Service service : supermarkets)
			assertRole(supermarket, service,  SMRoleTest.class); 
    }
	
	@Test
	public void serviceMustBeCompliantWithTheSupermarketRole() throws Exception {
		Role oracle = new Role("supermarket", "./roles/supermarket?wsdl");
		Service supermarket = new Service();
		supermarket.setWSDL("http://localhost:1234/supermarket1?wsdl");
		
		assertRole (oracle, supermarket, SMRoleTest.class); 
    }

}
