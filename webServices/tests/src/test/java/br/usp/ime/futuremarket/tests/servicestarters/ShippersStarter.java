package br.usp.ime.futuremarket.tests.servicestarters;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.xml.ws.Endpoint;

import br.usp.ime.futuremarket.Shipper;
import br.usp.ime.futuremarket.ShipperImpl;

public class ShippersStarter {
	static final String SHIPPER1 = "shipper1";
	static final String SHIPPER2 = "shipper2";
	private static final int SHIPPER1_PORT = 12004;
	private static final int SHIPPER2_PORT = 12005;
	public static final String SHIPPER1_ADDRESS = "http://0.0.0.0:"+SHIPPER1_PORT+"/"+SHIPPER1+"/choreography";
	public static final String SHIPPER2_ADDRESS = "http://0.0.0.0:"+SHIPPER2_PORT+"/"+SHIPPER2+"/choreography";
	public static final String SHIPPER = "shipper";
	private static Endpoint endpoint;
	
	public static void main(String[] args) {
		start();
	}

	private static void start() {
		startShipper(SHIPPER1, SHIPPER1_ADDRESS);
		startShipper(SHIPPER2, SHIPPER2_ADDRESS);
	}

	private static void startShipper(String serviceName, String serviceAddress) {
		Shipper shipper = null;
		try {
			shipper = new ShipperImpl();
			Field serviceNameField = ShipperImpl.class.getSuperclass().getDeclaredField("serviceName");
			serviceNameField.setAccessible(true);
			serviceNameField.set(shipper, serviceName);
			endpoint = Endpoint.create(shipper);
			endpoint.publish(serviceAddress);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
