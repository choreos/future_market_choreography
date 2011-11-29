package eu.choreos.mocks;

import org.junit.Before;

import eu.choreos.vv.servicesimulator.MockResponse;
import eu.choreos.vv.servicesimulator.WSMock;

public class SmRegistryMockTest {
	
	@Before
	public void publishSMRegistryMock() throws Exception{
		String realUri =" http://opencirrus-08006.hpl.hp.com:8084/petals/services/smregistry?wsdl";
		
		WSMock registryMock = new WSMock("registryMock", realUri);
		registryMock.setPort("1234");
		
		MockResponse response = new MockResponse().whenReceive("*").replyWith("registered");
		registryMock.returnFor("addSupermarket", response);
		registryMock.start();
	}

}
