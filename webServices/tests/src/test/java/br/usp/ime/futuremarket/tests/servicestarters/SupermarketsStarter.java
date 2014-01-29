package br.usp.ime.futuremarket.tests.servicestarters;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

import javax.xml.ws.Endpoint;

import br.usp.ime.futuremarket.Supermarket;
import br.usp.ime.futuremarket.choreography.SupermarketImpl;

public class SupermarketsStarter {
	static final String MANUFACTURER = "manufacturer";
	
	static final String SUPERMARKET1 = "supermarket1";
	static final String SUPERMARKET2 = "supermarket2";
	static final String SUPERMARKET3 = "supermarket3";
	static final String SUPERMARKET4 = "supermarket4";
	static final String SUPERMARKET5 = "supermarket5";

	static final String SUPPLIER1 = "supplier1";
	static final String SUPPLIER2 = "supplier2";
	static final String SUPPLIER3 = "supplier3";
	
	private static final int MANUFACTURER_PORT = 12006;
	
	private static final int SUPERMARKET1_PORT = 12007;
	private static final int SUPERMARKET2_PORT = 12008;
	private static final int SUPERMARKET3_PORT = 12009;
	private static final int SUPERMARKET4_PORT = 12010;
	private static final int SUPERMARKET5_PORT = 12011;
	
	private static final int SUPPLIER1_PORT = 12012;
	private static final int SUPPLIER2_PORT = 12013;
	private static final int SUPPLIER3_PORT = 12014;
	
	public static final String MANUFACTURER_ADDRESS = "http://0.0.0.0:"+MANUFACTURER_PORT+"/"+MANUFACTURER+"/choreography";
	
	public static final String SUPERMARKET1_ADDRESS = "http://0.0.0.0:"+SUPERMARKET1_PORT+"/"+SUPERMARKET1+"/choreography";
	public static final String SUPERMARKET2_ADDRESS = "http://0.0.0.0:"+SUPERMARKET2_PORT+"/"+SUPERMARKET2+"/choreography";
	public static final String SUPERMARKET3_ADDRESS = "http://0.0.0.0:"+SUPERMARKET3_PORT+"/"+SUPERMARKET3+"/choreography";
	public static final String SUPERMARKET4_ADDRESS = "http://0.0.0.0:"+SUPERMARKET4_PORT+"/"+SUPERMARKET4+"/choreography";
	public static final String SUPERMARKET5_ADDRESS = "http://0.0.0.0:"+SUPERMARKET5_PORT+"/"+SUPERMARKET5+"/choreography";
	
	public static final String SUPPLIER1_ADDRESS = "http://0.0.0.0:"+SUPPLIER1_PORT+"/"+SUPPLIER1+"/choreography";
	public static final String SUPPLIER2_ADDRESS = "http://0.0.0.0:"+SUPPLIER2_PORT+"/"+SUPPLIER2+"/choreography";
	public static final String SUPPLIER3_ADDRESS = "http://0.0.0.0:"+SUPPLIER3_PORT+"/"+SUPPLIER3+"/choreography";

	public static final String SUPERMARKET = "supermarket";
	
	private static Endpoint endpoint;
	
	public static void main(String[] args) {
		start();
	}

	private static void start() {
		startSupermarket(MANUFACTURER, MANUFACTURER_ADDRESS);
		
		startSupermarket(SUPERMARKET1, SUPERMARKET1_ADDRESS);
		startSupermarket(SUPERMARKET2, SUPERMARKET2_ADDRESS);
		startSupermarket(SUPERMARKET3, SUPERMARKET3_ADDRESS);
		startSupermarket(SUPERMARKET4, SUPERMARKET4_ADDRESS);
		startSupermarket(SUPERMARKET5, SUPERMARKET5_ADDRESS);
		
		startSupermarket(SUPPLIER1, SUPPLIER1_ADDRESS);
		startSupermarket(SUPPLIER2, SUPPLIER2_ADDRESS);
		startSupermarket(SUPPLIER3, SUPPLIER3_ADDRESS);
	}

	private static void startSupermarket(String serviceName, String serviceAddress) {
		Supermarket supermarket = null;
		try {
			Field propField = SupermarketImpl.class.getSuperclass().getDeclaredField("PROP");
			propField.setAccessible(true);
			
			Field modifiers = Field.class.getDeclaredField("modifiers");
			modifiers.setAccessible(true);
			modifiers.setInt(propField, propField.getModifiers() & ~Modifier.FINAL);

			Properties prop = new Properties();
			final ClassLoader loader = Thread.currentThread()
					.getContextClassLoader();
			final InputStream propFile = loader
					.getResourceAsStream(serviceName + ".properties");
			prop.load(propFile);
			
			propField.set(null, prop);
			propFile.close();

			// set static properties before instantiation
			supermarket = new SupermarketImpl();
			
			Field serviceNameField = SupermarketImpl.class.getSuperclass().getSuperclass().getDeclaredField("serviceName");
			serviceNameField.setAccessible(true);
			serviceNameField.set(supermarket, serviceName);
			endpoint = Endpoint.create(supermarket);
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
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
