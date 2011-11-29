package eu.choreos.utils;

import eu.choreos.vv.abstractor.Choreography;
import static eu.choreos.utils.AcceptanceTestUtils.PREFIX;
import eu.choreos.vv.abstractor.Role;
import eu.choreos.vv.abstractor.Service;

public class ChoreographyAbstractor {
	
	public static Choreography futureMarket;
	
	public static Choreography getChoreography() {
		futureMarket = new Choreography();
		
		// Creating Roles
		// ------------------------------------------------------------------------------------------------------------------------------------------
		Role supermarket = new Role("supermarket", "./roles/supermarket?wsdl"); 
		Role shipper = new Role("shipper", "./roles/supermarket?wsdl"); 
		Role customer = new Role("customer", "./roles/customer?wsdl"); 
		
		futureMarket.addRole(supermarket);
		futureMarket.addRole(shipper);
		futureMarket.addRole(customer);
		
		// Creating Services
		// ------------------------------------------------------------------------------------------------------------------------------------------
		Service registry = new Service();
		registry.setWSDL(PREFIX + "smregistry?wsdl");
		
		Service supermarket1 = new Service();
		supermarket1.setWSDL(PREFIX + "supermarket1?wsdl");
		supermarket1.addRole(supermarket);
		supermarket1.addService(registry, "supermarket");
		
		Service supermarket2 = new Service();
		supermarket2.setWSDL(PREFIX + "supermarket2?wsdl");
		supermarket2.addRole(supermarket);
		supermarket2.addService(registry, "supermarket");
		
		Service supermarket3 = new Service();
		supermarket3.setWSDL(PREFIX + "supermarket3?wsdl");
		supermarket3.addRole(supermarket);
		supermarket3.addService(registry, "supermarket");
		
		Service shipper1 = new Service();
		shipper1.setWSDL(PREFIX + "shipper1?wsdl");
		shipper1.addRole(shipper);
		
		Service customer1 = new Service();
		customer1.setWSDL(PREFIX + "customer?wsdl");
		customer1.addRole(customer);
		customer1.addService(registry, "customer");
		
		// Adding services into the choreography
		// ------------------------------------------------------------------------------------------------------------------------------------------
		futureMarket.addService(supermarket1,"supermarket");
		futureMarket.addService(supermarket2,"supermarket");
		futureMarket.addService(supermarket3,"supermarket");
		futureMarket.addService(shipper1, "shipper");
		futureMarket.addService(customer1, "customer");
		
		return futureMarket;
	}

}
