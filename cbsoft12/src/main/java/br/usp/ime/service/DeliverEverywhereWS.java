package br.usp.ime.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import br.usp.ime.admin.ServiceEndpoints;
import br.usp.ime.wsmock.CorreiosMockUtils;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;

@WebService
public class DeliverEverywhereWS {

	@WebMethod
	@WebResult(name="delivery")
	public DeliveryInfo getDelivery(@WebParam(name="origin") String origin, @WebParam(name="destination") String destination) throws Exception  {
		boolean withSedex = Integer.parseInt(destination) > 55555555;
		
		Item result = getCorreiosWSResult(origin, destination, withSedex);
		
		DeliveryInfo info = new DeliveryInfo();
		info.setEstimative(result.getContentAsInt("PrazoEntrega"));
		info.setPrice(result.getContentAsDouble("Valor"));
		
		return info;
	}
	
	private String getCorreiosWSDL() {
		return ServiceEndpoints.CORREIOS.getEndpoint();
	}
	
	private Item getCorreiosWSResult(String origin, String destination, boolean withSedex) throws Exception {
		CorreiosMockUtils mockUtils = new CorreiosMockUtils();
		Item searchRequest = mockUtils.buildRequest(withSedex);

		WSClient client = new WSClient(getCorreiosWSDL() + "?wsdl");
		Item services = client.request("CalcPrecoPrazo", searchRequest)
				.getChild("CalcPrecoPrazoResult").getChild("Servicos");

		Item sedexService = services.getChild("cServico");

		return sedexService;
	}
}
