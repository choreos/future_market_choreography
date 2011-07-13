package eu.choreos.utils;

import javax.xml.ws.Endpoint;


import eu.choreos.roles.SupermarketCustomerRole;
import eu.choreos.roles.SupermarketRole;
import eu.choreos.services.FutureMartWS;

public class RunWS {
	
	private static Endpoint supermarketEndpoint;
	private static Endpoint customerEndpoint;
	private static Endpoint futureMartEndpoint;

	
	public static void startSupermarketWS(){
		supermarketEndpoint = startWS(new SupermarketRole(), "supermarketRole");
	}
	
	public static void startSupermarketCustomerWS() {
		customerEndpoint = startWS(new SupermarketCustomerRole(), "supermarketCustomerRole");	
	}
	
	public static void startFutureMartWS() {
		futureMartEndpoint = startWS(new FutureMartWS(), "futureMartWS");	
	}
	
	

	private static Endpoint startWS(Object ws, String wsName) {
		Endpoint endpoint = Endpoint.create(ws);
		endpoint.publish("http://localhost:1234/" + wsName);
		return endpoint;
	}
	
	
	public static void stopSupermarketWS(){
		supermarketEndpoint.stop();
	}
	
	public static void stopSupermarketCustomerWS(){
		customerEndpoint.stop();
	}
	
	public static void stopFutureMartWS(){
		futureMartEndpoint.stop();
	}
	
	public static void main(String[] args) {
		RunWS.startSupermarketCustomerWS();
		RunWS.startSupermarketWS();
		RunWS.startFutureMartWS();
	}

}
