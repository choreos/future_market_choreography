package br.usp.ime.wsmock;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.admin.ChoreographyManager;
import br.usp.ime.admin.ServiceEndpoints;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.servicesimulator.WSMock;

public class DeliverEveryWhereWSTest {

	private static WSMock correiosMock;
	private static CorreiosMockUtils mockUtils;
	private static ChoreographyManager manager;
	private static final String DELIVER_EVERYWHERE_WSDL = "http://localhost:1234/deliverEVW?wsdl";

	@BeforeClass
	public static void setUp() throws Exception {
		mockUtils = new CorreiosMockUtils();

		correiosMock = mockUtils.buildWSMock();
		correiosMock.start();
		
		manager = new ChoreographyManager();
		manager.start(ServiceEndpoints.DELIVER_EVW);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		correiosMock.stop();
		manager.stop(ServiceEndpoints.DELIVER_EVW);
	}

	@Test
	public void shouldReturnSedexShippingServiceInformation() throws Exception {
		Item searchRequest = new ItemImpl("getDelivery");
		searchRequest.addChild("origin").setContent("03389219");
		searchRequest.addChild("destination").setContent("66666666");

		WSClient client = new WSClient(DELIVER_EVERYWHERE_WSDL);
		Item deliveryInfo = client.request("getDelivery", searchRequest).getChild("delivery");

		assertEquals("2",deliveryInfo .getContent("estimative"));
		assertEquals("14.54", deliveryInfo.getContent("price"));

	}
	
	@Test
	public void shouldReturnPacShippingServiceInformation() throws Exception {
		Item searchRequest = new ItemImpl("getDelivery");
		searchRequest.addChild("origin").setContent("03389219");
		searchRequest.addChild("destination").setContent("44444444");

		WSClient client = new WSClient(DELIVER_EVERYWHERE_WSDL);
		Item deliveryInfo = client.request("getDelivery", searchRequest).getChild("delivery");

		assertEquals("7",deliveryInfo .getContent("estimative"));
		assertEquals("9.45", deliveryInfo.getContent("price"));

	}
	
}
