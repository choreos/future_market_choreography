package br.usp.ime.wsmock;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.servicesimulator.MockResponse;
import eu.choreos.vv.servicesimulator.WSMock;

public class CorreiosMockUtils {

    private static final String SEDEX = "40010";
    private static final String PAC = "41106";

    // private String correiosWsdl =
    // "http://ws.correios.com.br/calculador/CalcPrecoPrazo.asmx?WSDL";

    public WSMock buildWSMock() throws Exception {
	String correiosWSDL = getLocalWsdl();

	WSMock mock = new WSMock("correiosMock", "9000", correiosWSDL);

	Item withSedexRequest = buildRequest(true);
	Item withPacRequest = buildRequest(false);

	Item withSedexResponse = buildResponse(true);
	Item withPacResponse = buildResponse(false);

	MockResponse response1 = new MockResponse();
	response1.whenReceive(withSedexRequest).replyWith(withSedexResponse);

	MockResponse response2 = new MockResponse();
	response2.whenReceive(withPacRequest).replyWith(withPacResponse);

	mock.returnFor("CalcPrecoPrazo", response1, response2);
	return mock;
    }

    private String getLocalWsdl() {
	return "file://" + System.getProperty("user.dir") + "/resources/calc_preco_prazo.wsdl";
    }

    public Item buildRequest(boolean withSedex) {
	Item request = new ItemImpl("CalcPrecoPrazo");

	String destination = withSedex ? "12345678" : "99999999";

	request.addChild("sCepOrigem").setContent("11111111");
	request.addChild("sCepDestino").setContent(destination);

	request.addChild("nCdServico").setContent(SEDEX + "," + PAC);

	request.addChild("sCdAvisoRecebimento").setContent("N");
	request.addChild("nVlValorDeclarado").setContent("N");
	request.addChild("sCdMaoPropria").setContent("S");

	request.addChild("nCdEmpresa").setContent("12345");
	request.addChild("sDsSenha").setContent("1234");

	request.addChild("nVlAltura").setContent("15");
	request.addChild("nVlLargura").setContent("15");
	request.addChild("nVlComprimento").setContent("15");
	request.addChild("nVlDiametro").setContent("0");
	request.addChild("nVlPeso").setContent("2");
	request.addChild("nCdFormato").setContent("1");

	return request;
    }

    public Item buildResponse(boolean withSedex) throws Exception {
	Item response = new ItemImpl("CalcPrecoPrazoResponse");

	Item result = response.addChild("CalcPrecoPrazoResult");
	Item Servicos = result.addChild("Servicos");

	Item cServico = Servicos.addChild("cServico");

	if (withSedex) {
	    cServico.addChild("Codigo").setContent(SEDEX);
	    cServico.addChild("PrazoEntrega").setContent("2");
	    cServico.addChild("Valor").setContent("14.54");
	} else {
	    cServico.addChild("Codigo").setContent(PAC);
	    cServico.addChild("PrazoEntrega").setContent("7");
	    cServico.addChild("Valor").setContent("9.45");
	}

	cServico.addChild("EntregaDomiciliar").setContent("S");
	cServico.addChild("ValorAvisoRecebimento").setContent("0");
	cServico.addChild("Erro").setContent("0");
	cServico.addChild("MsgErro").setContent("");
	cServico.addChild("ValorMaoPropria").setContent("0");
	cServico.addChild("ValorValorDeclarado").setContent("0");
	cServico.addChild("EntregaSabado").setContent("S");

	return response;
    }
}
