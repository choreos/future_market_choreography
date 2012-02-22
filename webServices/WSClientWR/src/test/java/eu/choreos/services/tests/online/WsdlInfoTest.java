package eu.choreos.services.tests.online;

import static org.junit.Assert.assertEquals;

import javax.xml.namespace.QName;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.services.RegistryWS;
import eu.choreos.services.WsdlInfo;

public class WsdlInfoTest {
	private static String xml;
	private static String registry;
	private static String supermarket1;

	@BeforeClass
	public static void SetUp(){
		registry = "http://localhost:8080/registry/registry?wsdl";
		supermarket1 = "http://localhost:8080/supermarket1/supermarket1?wsdl";
		xml = "<a><definitions><name>teste</name><targetNamespace>teste1</targetNamespace></definitions></a>";
	}

	@Test
	public void shouldparserXML() {
		WsdlInfo.getDocument(xml);
	}
	
	@Test
	public void shouldCreateQName(){
		assertEquals(new QName("http://registry.choreos.eu","RegistryWSImplService"), WsdlInfo.createQName(registry));
		assertEquals(new QName("http://services.choreos.eu/","SM1Service"), WsdlInfo.createQName(supermarket1));
	}
	
	
	@Test
	public void shouldReturnTheSecondElement() {
		RegistryWS smr= WsdlInfo.getPort(registry,RegistryWS.class);
		System.out.println(smr.add("a","sad"));
		System.out.println(smr.add("a","sas"));
		System.out.println(smr.add("b","sad"));
		System.out.println(smr.add("b","sad"));
		System.out.println(smr.getFirst("a"));
		System.out.println(smr.getList("a"));
	}

}
