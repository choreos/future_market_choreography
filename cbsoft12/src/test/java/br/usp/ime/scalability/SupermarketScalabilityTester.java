package br.usp.ime.scalability;

import java.io.IOException;

import org.apache.xmlbeans.XmlException;

import eu.choreos.vv.ScalabilityTester;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

public class SupermarketScalabilityTester extends ScalabilityTester {

	private static final String REGISTRY_WSDL = "http://localhost:8080/registry/endpoint?wsdl";

	private static final String[] portals = {
			"http://localhost:8080/portal1/choreography?wsdl",
			"http://localhost:8080/portal2/choreography?wsdl",
			"http://localhost:8080/portal3/choreography?wsdl",
			"http://localhost:8080/portal4/choreography?wsdl",
			"http://localhost:8080/portal5/choreography?wsdl" };

	private static final String ENDPOINT = "/choreography?wsdl";
	
	private static final String[] productNames = {"bread", "milk", "beer", "butter", "juice", "ham", "wine", "jam", "sugar"};

	private WSClient registry;
	private WSClient client;

	private void includePortal(int index) throws WSDLException, XmlException,
			IOException, FrameworkException, InvalidOperationNameException {
		WSClient client = new WSClient(portals[index]);
		client.request("register");
	}

	private void removeAllPortals() throws Exception {
		for (String wsdl : portals) {
			removePortal(wsdl);
		}
	}

	private void removePortal(String wsdl) throws Exception {
		WSClient client;
		client = new WSClient(wsdl);
		client.request("unregister");
	}

	@Override
	public void resourceScaling(int resourceQuantity) throws WSDLException,
			XmlException, IOException, FrameworkException,
			InvalidOperationNameException {
		includePortal(resourceQuantity - 1);
	}

	@Override
	public void setUp() throws Exception {
		removeAllPortals();
		registry = new WSClient(REGISTRY_WSDL);
	}

	@Override
	public void beforeTest() throws Exception {
		Item result = registry.request("getServiceRoundRobin", "portal");
		String wsdl = result.getContent("return");
		client = new WSClient(wsdl + ENDPOINT);
	}

	@Override
	public void test() throws Exception {
		Item returnItem = getLowestPrice();
		purchase(returnItem.getChild("return").getChild("items"));
	}
	
	private Item getLowestPrice() throws InvalidOperationNameException, FrameworkException {
		return client.request("getLowestPrice", getProductList());
	}
	
	private void purchase(Item items) throws InvalidOperationNameException, FrameworkException {
		client.request("purchase", getShopList(items));
	}
	
	private Item getShopList(Item purchaseItems) {
		Item purchase = new ItemImpl("purchase");
		Item arg1 = purchase.addChild("arg1");
		arg1.addChild("creditCard").setContent("0000000000");
		arg1.addChild("address").setContent("my home...");
		arg1.addChild("name").setContent("john");
		Item arg0 = purchase.addChild("arg0");
		arg0.addChild(purchaseItems);
		
		return purchase;
	}

	private Item getProductList() {
		final Item getLowestPrice = new ItemImpl("getLowestPrice");
		Item arg0 = getLowestPrice.addChild("arg0");
		Item items = arg0.addChild("items");
		Item entry = items.addChild("entry");
		entry.addChild("value").setContent(someQuantity());
		entry.addChild("key").setContent(someProduct());
		
		return getLowestPrice;
	}
	
	private String someProduct() {
		final int index = (int)Math.floor(Math.random() * productNames.length);
		return productNames[index];
	}
	
	private String someQuantity() {
		return "" + Math.round(Math.random() * 10);
	}

	public static void main(String[] args) {
		SupermarketScalabilityTester tester = new SupermarketScalabilityTester();
		tester.setInitialRequestsPerMinute(300);
		tester.setInititalResoucesQuantity(1);
		tester.setNumberOfExecutionsPerTest(100);
		tester.setNumberOfTestsToRun(5);
		try {
			tester.run("label");
		} catch(Exception e) {
			e.printStackTrace();
		}
		tester.showChart("supermarket choreography");
	}
}
