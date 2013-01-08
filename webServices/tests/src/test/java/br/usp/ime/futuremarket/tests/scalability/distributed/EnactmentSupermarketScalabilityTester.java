package br.usp.ime.futuremarket.tests.scalability.distributed;

import eu.choreos.vv.ScalabilityTester;
import eu.choreos.vv.aggregations.Mean;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.increasefunctions.LinearIncrease;
import eu.choreos.vv.loadgenerator.UniformLoadGenerator;

public class EnactmentSupermarketScalabilityTester extends ScalabilityTester {

	private static final String ENDPOINT = "/choreography?wsdl";

	private static final String[] productNames = { "bread", "milk", "beer",
			"butter", "juice", "ham", "wine", "jam", "sugar" };

	private WSClient registry;
	private WSClient client;
//	private FutureMarketEnacter enacter = new FutureMarketEnacter();

	private Item getLowestPrice() throws InvalidOperationNameException,
			FrameworkException {
		return client.request("getLowestPrice", getProductList());
	}

	private void purchase(Item items) throws InvalidOperationNameException,
			FrameworkException {
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
		final int index = (int) Math.floor(Math.random() * productNames.length);
		return productNames[index];
	}

	private String someQuantity() {
		return "" + Math.round(Math.random() * 10);
	}

	@Override
	public void setUp() throws Exception {
		registry = new WSClient(this.getEnacter().getServiceUri(FutureMarketEnacter.REG_NAME));
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

	public static void main(String[] args) {
		EnactmentSupermarketScalabilityTester tester = new EnactmentSupermarketScalabilityTester();

		tester.setEnacter(new FutureMarketEnacter());
		tester.setAggregator(new Mean());
		tester.setScalabilityFunction(new LinearIncrease());
		tester.setLoadGenerator(new UniformLoadGenerator());

		tester.setInititalResoucesQuantity(1);
		tester.setInitialRequestsPerMinute(300);
		tester.setNumberOfSteps(3);
		tester.setNumberOfExecutionsPerStep(5);
		try {
			tester.run("get price and purchase");
		} catch (Exception e) {
			e.printStackTrace();
		}
		tester.showChart("supermarket choreography");
	}
}
