package eu.choreos.services;

import static org.junit.Assert.*;

import javax.xml.namespace.QName;

import org.junit.BeforeClass;
import org.junit.Test;

public class WsdlInfoTest {
private static String xml;
private static String registry;
private static String supermarket1;
@BeforeClass
public static void SetUp(){
	registry = "http://localhost:8080/smregistry/smregistry?wsdl";
	supermarket1 = "http://localhost:8080/smsupermarket1/smsupermarket1?wsdl";
	xml = "<a><definitions><name>teste</name><targetNamespace>teste1</targetNamespace></definitions></a>";
}
	@Test
	public void shouldparserXML() {
		WsdlInfo.getDocument(xml);
	}
	@Test
	public void shouldCreateQName(){
		assertEquals(WsdlInfo.createQName(registry),new QName("http://smregistry.choreos.eu","SMRegistryWSImplService"));
		assertEquals(WsdlInfo.createQName(supermarket1),new QName("http://services.choreos.eu/","SM1Service"));
	}
	@Test
	public void shouldReturnTheSecondElement() {
		SMRegistryWS smr= WsdlInfo.getPort(registry,SMRegistryWS.class);
		System.out.println(smr.add("a","sad"));
		System.out.println(smr.add("a","sas"));
		System.out.println(smr.add("b","sad"));
		System.out.println(smr.add("b","sad"));
		System.out.println(smr.getFirst("a"));
		System.out.println(smr.getList("a"));
	}
}
