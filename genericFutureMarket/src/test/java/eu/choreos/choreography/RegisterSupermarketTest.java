package eu.choreos.choreography;

import static org.junit.Assert.fail;
import static eu.choreos.choreography.AcceptanceTestUtils.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.vv.Item;
import eu.choreos.vv.WSClient;

public class RegisterSupermarketTest {
	private WSClient smregistry;
	
	@Before
	public void setUpSMRegistry() throws Exception {
		smregistry = new WSClient(SMREGISTRY_ENDPOINT);
	}
	
	@After
	public void removeRegisteredSupermarkets() throws Exception {
		removeSupermarkets(smregistry);
	}

	@Test
	public void shouldRegistrySupermarkets() throws Exception {
		registerSupermarkets();
		Item response = smregistry.request("getList", (Item) null);
		List<Item> list = response.getChildAsList("return");
		checkIfEndpointsExists(list);
	}
	
	private void checkIfEndpointsExists(List<Item> list) {
		for (int i = 1; i <= NUMBER_OF_SUPERMARKETS; i++) {
			String endpoint = generateEndpoint(i);
			verifyIfEndpointExists(endpoint, list);
		}
	}

	private void verifyIfEndpointExists(String endpoint, List<Item> list) {
		for (Item item : list) {
			if(item.getContent().equals(endpoint))
				return;
		}
		fail("endpoint " + endpoint + " does not exists");
	}
}
