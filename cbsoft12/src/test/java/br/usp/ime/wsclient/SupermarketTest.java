package br.usp.ime.wsclient;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;

public class SupermarketTest {

	private static final String SUPERMARKET1_WSDL = "http://batcave:8080/supermarket1/choreography?wsdl";

	@Test
	public void supermarket1ServiceShouldReturnThePriceOfAProduct() throws Exception {
		WSClient client = new WSClient(SUPERMARKET1_WSDL);
		Item requestRoot = getShopList("milk");
		Item response = client.request("getPrices", requestRoot);
		
		double actualPrice = response.getChild("return")
									 .getChild("items")
									 .getChild("entry")
									 .getChild("value")
									 .getChild("product")
									 .getContentAsDouble("price");
		
		assertEquals(1.0, actualPrice, 0.00001 );
	}

	private Item getShopList(String productName) {
		Item shopList = new ItemImpl("getPrices");
		Item arg0 = shopList.addChild("arg0");
		Item items = arg0.addChild("items");
		Item entry = items.addChild("entry");
		Item value = entry.addChild("value");
		Item product = value.addChild("product");
		product.addChild("price").setContent("10000");
		product.addChild("name").setContent(productName);
		value.addChild("quantity").setContent("1");
		value.addChild("seller").setContent("?");
		entry.addChild("key").setContent(productName);
		
		return shopList;
	}

}
