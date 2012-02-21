package eu.choreos.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Properties;

import javax.jws.WebMethod;
import javax.jws.WebService;

import eu.choreos.DeliveryInfo;
import eu.choreos.PurchaseInfo;

@WebService(targetNamespace = "http://shipper.choreos.eu",
	endpointInterface = "eu.choreos.services.ShipperWS",
	serviceName = "ShipperWSImplService"
)
public class ShipperWSImpl implements ShipperWS {

	HashMap<Integer, String> deliveries = new HashMap<Integer, String>();
	private HashMap<String, DeliveryInfo> deliveryInfoList;
	static RegistryWS registry;
	final ClassLoader loader = ShipperWSImpl.class.getClassLoader();

	long id = 1L;

	public ShipperWSImpl() throws FileNotFoundException, IOException{
		deliveryInfoList = new HashMap<String, DeliveryInfo>();
		register();
	}
	
	private void register() throws IOException, FileNotFoundException {
		registry = WsdlInfo.getPort(getRegistryWsdl(), RegistryWS.class);
		registry.add("Shipper",getMyWsdl());
	}

	private String getMyWsdl() throws MalformedURLException, UnknownHostException {
		final String hostName = getMyHostName();
		return "http://" + hostName + ":8080/shipper/shipper?wsdl";
	}
	
	private String getMyHostName() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getCanonicalHostName();
    }
	
	private String getRegistryWsdl() throws FileNotFoundException, IOException {
        return getWsdl("registry.wsdl");
    }

    private String getWsdl(String name) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(loader.getResourceAsStream("config.properties"));
        return properties.getProperty(name);
    }

	@WebMethod
	public String setDelivery(PurchaseInfo purchaseInfo) {
		DeliveryInfo deliveryInfo = new DeliveryInfo();
		deliveryInfo.setPurchase(purchaseInfo);
		deliveryInfo.setId("" + id++);
		deliveryInfo.setDate(new Date().toString());
		deliveryInfo.setStatus("done");
		
		deliveryInfoList.put(purchaseToIdentifier(purchaseInfo), deliveryInfo);
		
		return "OK";
	}
	
	@WebMethod
	public DeliveryInfo getDeliveryStatus(PurchaseInfo purchaseInfo){
		if ( deliveryInfoList.containsKey(purchaseToIdentifier(purchaseInfo)) ){
			return deliveryInfoList.get(purchaseToIdentifier(purchaseInfo));
		}
		return null;
	}

	@WebMethod
	public String getDateAndTime(String id) {
		String zipCode = deliveries.get(Integer.parseInt(id));

		Calendar c = null;

		if (Integer.parseInt(zipCode.substring(0, 3)) <= 300)
			c = setTime(8, 12, 32);
		else if (Integer.parseInt(zipCode.substring(0, 3)) <= 600)
			c = setTime(15, 30, 42);
		else if (Integer.parseInt(zipCode.substring(0, 3)) <= 750)
			c = setTime(20, 15, 00);
		else
			c = setTime(22, 30, 00);

		return c.getTime().toString();
	}

	private Calendar setTime(int hour, int minute, int second) {
		Calendar c1 = new GregorianCalendar(2011, Calendar.DECEMBER, 24);
		c1.set(2011, Calendar.DECEMBER, 24, hour, minute, second);

		return c1;
	}
	
	private String purchaseToIdentifier(PurchaseInfo p){
		return p.getId() + p.getSellerEndpoint();
	}

}
