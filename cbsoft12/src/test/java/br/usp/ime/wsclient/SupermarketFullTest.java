package br.usp.ime.wsclient;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;

public class SupermarketFullTest {

	private static final String SUPERMARKET1_WSDL = "http://batcave:8080/supermarket1/choreography?wsdl";

	@Test
	public void shouldGetTheCorrectOperationNames() throws Exception {
		WSClient client = new WSClient(SUPERMARKET1_WSDL);
		List<String> operationNames = client.getOperations();
		
		assertEquals("getPrices", operationNames.get(0));
		assertEquals("purchase", operationNames.get(1));
	}
	
	@Test
	public void shouldReturnProductInformationWhenSearchForAProduct() throws Exception {
		WSClient client = new WSClient(SUPERMARKET1_WSDL);
		Item requestRoot = getShopList("beer");
		Item response = client.request("getPrices", requestRoot)
										.getChild("return")
										.getChild("items")
										.getChild("entry")
										.getChild("value");
		
		Item product = response.getChild("product");
		
		assertEquals("beer", product.getContent("name"));
		assertEquals(30.0, product.getContentAsDouble("price"), 0.0001);
		assertEquals("http://batcave:8080/supermarket1", response.getContent("seller"));
	}
	
	private Item getShopList(String productName) {
		Item shopList = new ItemImpl("getPrices");
		Item arg0 = shopList.addChild("arg0");
		Item items = arg0.addChild("items");
		Item entry = items.addChild("entry");
		Item value = entry.addChild("value");
		Item product = value.addChild("product");
		product.addChild("price").setContent("0");
		product.addChild("name").setContent(productName);
		value.addChild("quantity").setContent("1");
		value.addChild("seller").setContent("?");
		entry.addChild("key").setContent(productName);
		
		return shopList;
	}

}
