package eu.choreos.services;

import javax.xml.ws.Endpoint;

public class MainWebService {
	public static void main(String[] args) {
		Endpoint ws = Endpoint.create(new SupermarketWS());
		ws.publish("http://localhost:4321/WS/SM" + args[0]);
	}
}
