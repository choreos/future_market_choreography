package eu.choreos.utils;

import javax.xml.ws.Endpoint;

import eu.choreos.roles.SupermarketCustomerRole;
import eu.choreos.roles.SupermarketRole;
import eu.choreos.services.FutureMartWS;
import eu.choreos.services.SMRegistryWS;

public class RunWS {
	
	private static Endpoint supermarket;
	private static Endpoint customer;
	private static Endpoint futureMart;
	private static Endpoint smRegistry;
	public static final String REGISTRY_ENDPOINT = "http://localhost:1234/smregistry";
	
	public static void startSupermarketWS(){
		supermarket = startWS(new SupermarketRole(), "supermarketRole");
	}
	
	public static void startSupermarketCustomerWS() {
		customer = startWS(new SupermarketCustomerRole(), "supermarketCustomerRole");	
	}
	
	public static void startFutureMartWS() {
		futureMart = startWS(new FutureMartWS(), "futureMartWS");	
	}
	
	public static void startSMRegistryWS() {
		smRegistry = startWS(new SMRegistryWS(), "smregistry");	
	}
	
	

	private static Endpoint startWS(Object ws, String wsName) {
		Endpoint endpoint = Endpoint.create(ws);
		endpoint.publish("http://localhost:1234/" + wsName);
		return endpoint;
	}
	
	
	public static void stopSupermarketWS(){
		supermarket.stop();
	}
	
	public static void stopSupermarketCustomerWS(){
		customer.stop();
	}
	
	public static void stopFutureMartWS(){
		futureMart.stop();
	}
	
	public static void stopSMRegistryWS(){
		smRegistry.stop();
	}
	
	public static void main(String[] args) {
		RunWS.startSupermarketCustomerWS();
		RunWS.startSupermarketWS();
		RunWS.startFutureMartWS();
		RunWS.startSMRegistryWS();
	}

}
