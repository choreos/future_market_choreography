package eu.choreos.services;

import javax.xml.ws.Endpoint;

public class SM6PortType_SM6Port_Server{

	public static void main(String args[]) throws java.lang.Exception {

		Object implementor1 = new SM1PortTypeImpl();
		String address1 = "http://localhost:4321/WS/SM1";
		Endpoint.publish(address1, implementor1);

		Object implementor2 = new SM2PortTypeImpl();
		String address2 = "http://localhost:4321/WS/SM2";
		Endpoint.publish(address2, implementor2);

		Object implementor3 = new SM3PortTypeImpl();
		String address3 = "http://localhost:4321/WS/SM3";
		Endpoint.publish(address3, implementor3);

		Object implementor4 = new SM4PortTypeImpl();
		String address4 = "http://localhost:4321/WS/SM4";
		Endpoint.publish(address4, implementor4);

		Object implementor5 = new SM5PortTypeImpl();
		String address5 = "http://localhost:4321/WS/SM5";
		Endpoint.publish(address5, implementor5);

		Object implementor6 = new SM6PortTypeImpl();
		String address6 = "http://localhost:4321/WS/SM6";
		Endpoint.publish(address6, implementor6);

	}
}
