package eu.choreos.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.Service;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class WsdlInfo {

	public static Document getDocument(String xml) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		InputSource source = new InputSource(new StringReader(xml));
		try {
			Document a = dbf.newDocumentBuilder().parse(source);
			return a;
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getWsdlValue(Document doc,String definitionNode) {
		NodeList definitions = doc.getElementsByTagName("definitions");
		for (int index = 0; index <= definitions.getLength(); index++) {
			Node node = definitions.item(index);
			for (int j = 0; j <= definitions.getLength(); j++) {
				if (node != null) {
					Node item = node.getAttributes().item(j);
					if (item != null) {
						if(item.getNodeName().equalsIgnoreCase(definitionNode))
							return item.getNodeValue();
					}
				}
			}
		}
		return null;
	}

	public static QName createQName(String wsdl) {
		QName qName = null;
		try {
			String wsdlStream = XMLOpen.openUrl(wsdl);
			Document doc = getDocument(wsdlStream);
			String serviceName = getWsdlValue(doc,"name");
			String targetNameSpace = getWsdlValue(doc,"targetNamespace");
			qName = new QName(targetNameSpace,serviceName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qName;
	}
	public static Service createService(String wsdl){
		Service s = null;
		QName qName = createQName(wsdl);
		URL url = null;
		try {
			url = new URL(wsdl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		s = Service.create(url,qName);
		return s;
	}
	
	public static <T> T getPort(String wsdl,Class<T> c){
		Service service = WsdlInfo.createService(wsdl);
		return service.getPort(c);
	}

	
	

}
