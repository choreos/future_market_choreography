package br.usp.ime.futuremarket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class Main {

    private final static String PREFIX = "pre";

    /**
     * @param args
     * @throws SOAPException
     * @throws IOException
     * @throws FactoryConfigurationError
     * @throws XMLStreamException
     */
    public static void main(final String[] args) throws SOAPException, IOException,
            XMLStreamException, FactoryConfigurationError {
        final String wsdl = args[0] + "?wsdl";
        final String partnerRole = args[1];
        final String partnerEndpoint = args[2];

        final String namespace = parseNamespace(wsdl);
        final SOAPMessage msg = getSOAPMessage(namespace, partnerRole, partnerEndpoint);
        final SOAPConnection conn = getSOAPConnection();
        final URL called = new URL(wsdl);
        conn.call(msg, called);

        conn.close();
    }

    private static String parseNamespace(final String wsdl) throws XMLStreamException,
            FactoryConfigurationError, IOException {
        final URL url = new URL(wsdl);
        final InputStreamReader streamReader = new InputStreamReader(url.openStream());
        final BufferedReader wsdlInputStream = new BufferedReader(streamReader);
        final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        final XMLEventReader reader = xmlInputFactory.createXMLEventReader(wsdlInputStream);

        String elementName, namespace = "";
        XMLEvent event;
        StartElement element;
        while (reader.hasNext()) {
            event = reader.nextEvent();
            if (event.isStartElement()) {
                element = event.asStartElement();
                elementName = element.getName().getLocalPart();
                if ("import".equals(elementName)) {
                    final QName qname = new QName("namespace"); // NOPMD
                    namespace = element.getAttributeByName(qname).getValue();
                    break;
                }
            }
        }

        return namespace;
    }

    private static SOAPConnection getSOAPConnection() throws UnsupportedOperationException,
            SOAPException {
        final SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
        return sfc.createConnection();
    }

    private static SOAPMessage getSOAPMessage(final String namespace, final String partnerRole,
            final String partnerEndpoint) throws SOAPException {
        final MessageFactory msgFactory = MessageFactory.newInstance();
        final SOAPMessage msg = msgFactory.createMessage();

        final SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();
        envelope.addNamespaceDeclaration(PREFIX, namespace);

        final SOAPBody body = msg.getSOAPBody();
        final QName bodyName = new QName("setInvocationAddress");
        final SOAPBodyElement bodyElement = body.addBodyElement(bodyName);
        bodyElement.setPrefix(PREFIX);

        final QName role = new QName("arg0");
        final SOAPElement roleElement = bodyElement.addChildElement(role);
        roleElement.addTextNode(partnerRole);

        final QName address = new QName("arg1");
        final SOAPElement addressElement = bodyElement.addChildElement(address);
        addressElement.addTextNode(partnerEndpoint);

        return msg;
    }

}