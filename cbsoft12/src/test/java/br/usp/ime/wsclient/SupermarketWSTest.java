package br.usp.ime.wsclient;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.admin.ChoreographyManager;
import br.usp.ime.admin.ServiceEndpoints;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;

public class SupermarketWSTest {

	private static final String SM1_WSDL = "http://localhost:1221/sm1?wsdl";
	private static ChoreographyManager manager;

	@BeforeClass
	public static void setUp() throws Exception {
		manager = new ChoreographyManager();
		manager.start(ServiceEndpoints.SM1);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		manager.stop(ServiceEndpoints.SM1);
	}

	@Test
	public void shouldReturnTheCorrectOperationNames() throws Exception {
		WSClient client = new WSClient(SM1_WSDL);
		List<String> operationNames = client.getOperations();

		assertEquals("checkPriceOf", operationNames.get(0));
		assertEquals("purchase", operationNames.get(1));
	}

	@Test
	public void supermarketShouldReturnAPriceWhenSearchForAProduct()
			throws Exception {
		WSClient client = new WSClient(SM1_WSDL);
		Item price = client.request("checkPriceOf", "beer");

		assertEquals(3.50, price.getContentAsDouble("result"), 0.0001);
	}

	@Test
	public void supermarketShouldReturnAPaymentStatusMessageWhenPurchaseAProduct()
			throws Exception {
		WSClient client = new WSClient(SM1_WSDL);
		Item personalInfo = createPersonalInfo();
		Item invoice = client.request("purchase", personalInfo).getChild(
				"invoice");

		assertEquals("John Locke", invoice.getContent("customerName"));
		assertEquals("beer", invoice.getContent("productName"));
		assertEquals("Payment Authorized", invoice.getContent("paymentStatus"));
	}

	private Item createPersonalInfo() {
		Item purchase = new ItemImpl("purchase");
		purchase.addChild("productName").setContent("beer");
		Item info = purchase.addChild("info");
		info.addChild("creditCard").setContent("12345");
		info.addChild("phone").setContent("99999999");
		info.addChild("address").setContent("Somewhere over the rainbow");
		info.addChild("name").setContent("John Locke");

		return purchase;
	}

}
