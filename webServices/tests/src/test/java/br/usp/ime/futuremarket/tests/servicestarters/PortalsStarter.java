package br.usp.ime.futuremarket.tests.servicestarters;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.xml.ws.Endpoint;

import br.usp.ime.futuremarket.choreography.Portal;
import br.usp.ime.futuremarket.choreography.PortalImpl;

public class PortalsStarter {
	static final String PORTAL1 = "portal1";
	static final String PORTAL2 = "portal2";
	private static final int PORTAL1_PORT = 12002;
	private static final int PORTAL2_PORT = 12003;
	public static final String PORTAL1_ADDRESS = "http://0.0.0.0:"+PORTAL1_PORT+"/"+PORTAL1+"/choreography";
	public static final String PORTAL2_ADDRESS = "http://0.0.0.0:"+PORTAL2_PORT+"/"+PORTAL2+"/choreography";
	public static final String PORTAL = "portal";
	private static Endpoint endpoint;
	
	public static void main(String[] args) {
		start();
	}

	private static void start() {
		startPortal(PORTAL1, PORTAL1_ADDRESS);
		startPortal(PORTAL2, PORTAL2_ADDRESS);
	}

	private static void startPortal(String serviceName, String serviceAddress) {
		Portal portal = null;
		try {
			portal = new PortalImpl();
			Field serviceNameField = PortalImpl.class.getSuperclass().getSuperclass().getDeclaredField("serviceName");
			serviceNameField.setAccessible(true);
			serviceNameField.set(portal, serviceName);
			endpoint = Endpoint.create(portal);
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
