package br.usp.ime.interceptor;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.interceptor.MessageInterceptor;

public class ShipperIntegrationTest {

    private static final String SHIPPER_ENDPOINT = "http://localhost:8080/shipper";
    private static final String SHIPPER_WSDL = SHIPPER_ENDPOINT + "/choreography?wsdl";

    private static final String SHIPPER_PROXY_ENDPOINT = "http://localhost:9009/shipper";
    private static final String PORTAL_WSDL = "http://localhost:8080/portal/choreography?wsdl";

    private static Registry registry;
    private static MessageInterceptor interceptor;

    @BeforeClass
    public static void setUp() throws Exception {
	interceptor = new MessageInterceptor("9009");
	interceptor.setName("shipper");
	interceptor.interceptTo(SHIPPER_WSDL);

	registry = new Registry();
	registry.removeService("shipper", "shipper");
	registry.addService("shipper", "shipper", SHIPPER_PROXY_ENDPOINT);
    }

    @AfterClass
    public static void tearDown() throws Exception {
	interceptor.stop();

	registry.removeService("shipper", "shipper");
	registry.addService("shipper", "shipper", SHIPPER_ENDPOINT);
    }

    @Test
    public void shipperShouldReceiveADeliveryMessage() throws Exception {
	WSClient client = new WSClient(PORTAL_WSDL);

	Item purchaseRequest = buildPurchaseRequest();
	client.request("purchase", purchaseRequest);

	Item message = interceptor.getMessages().get(0);

	Item orderInfo = message.getChild("arg0");
	Item customerInfo = orderInfo.getChild("customerInfo");
	Item productInfo = orderInfo.getChild("shopList")
									 .getChild("items")
									 .getChild("entry")
									 .getChild("value")
									 .getChild("product");

	assertEquals("deliver", message.getName());

	assertEquals("John Locke", customerInfo.getContent("name"));
	assertEquals("Lost island", customerInfo.getContent("address"));

	assertEquals("milk", productInfo.getContent("name"));
	assertEquals("1.0", productInfo.getContent("price"));
    }

    private Item buildPurchaseRequest() throws Exception {
	Item purchase = new ItemImpl("purchase");
	Item arg1 = purchase.addChild("arg1");
	
	arg1.addChild("creditCard").setContent("12344567789009877654");
	arg1.addChild("address").setContent("Lost island");
	arg1.addChild("name").setContent("John Locke");
	
	Item arg0 = purchase.addChild("arg0");
	Item items = arg0.addChild("items");
	
	Item entry = items.addChild("entry");
	entry.addChild("key").setContent("milk");
	Item value = entry.addChild("value");
	Item product = value.addChild("product");
	product.addChild("price").setContent("1.0");
	product.addChild("name").setContent("milk");
	
	value.addChild("quantity").setContent("1");
	value.addChild("seller").setContent("http://localhost:8080/supermarket1");

	return purchase;
    }
}
