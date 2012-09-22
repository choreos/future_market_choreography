package br.usp.ime.wsmock;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.servicesimulator.WSMock;

public class CalcPrecoPrazoTest {

	private static WSMock correiosMock;
	private static CorreiosMockUtils mockUtils;

	@BeforeClass
	public static void setUp() throws Exception {
		mockUtils = new CorreiosMockUtils();

		correiosMock = mockUtils.buildWSMock();
		correiosMock.start();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		correiosMock.stop();
	}

	@Test
	public void shouldReturnSedexShippingServiceInformation() throws Exception {
		Item searchRequest = mockUtils.buildRequest(true);

		WSClient client = new WSClient(correiosMock.getWsdl());
		Item services = client.request("CalcPrecoPrazo", searchRequest)
				.getChild("CalcPrecoPrazoResult").getChild("Servicos");

		Item sedexService = services.getChild("cServico");

		assertEquals("40010", sedexService.getContent("Codigo"));
		assertEquals("2", sedexService.getContent("PrazoEntrega"));
		assertEquals("14.54", sedexService.getContent("Valor"));

	}
	
	@Test
	public void shouldReturnPacShippingServiceInformation() throws Exception {
		Item searchRequest = mockUtils.buildRequest(false);

		WSClient client = new WSClient(correiosMock.getWsdl());
		Item services = client.request("CalcPrecoPrazo", searchRequest)
				.getChild("CalcPrecoPrazoResult").getChild("Servicos");

		Item pacService = services.getChild("cServico");

		assertEquals("41106", pacService.getContent("Codigo"));
		assertEquals("7", pacService.getContent("PrazoEntrega"));
		assertEquals("9.45", pacService.getContent("Valor"));

	}

}
