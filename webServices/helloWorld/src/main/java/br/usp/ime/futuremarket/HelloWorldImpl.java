package br.usp.ime.futuremarket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br",
        endpointInterface = "br.usp.ime.futuremarket.HelloWorld")
public class HelloWorldImpl implements HelloWorld {
    private static final String NAMESPACE = "http://futuremarket.ime.usp.br";
    private static final String SERVICE_NAME = "HelloWorldImplService";
    private static final String PROPS_FILE = "helloWorld.properties";
    private static final String PROPS_KEY = "helloWorld.wsdl";

    @Override
    public String sayHello(final String name) {
        return "Hello, " + name + "!";
    }

    public static HelloWorld getWSClient() throws IOException {
        final String wsdl = getWsdl();
        final QName qname = new QName(NAMESPACE, SERVICE_NAME);
        URL url = null;
        HelloWorld helloWorld = null;

        try {
            url = new URL(wsdl);
            final Service service = Service.create(url, qname);
            helloWorld = service.getPort(HelloWorld.class);
        } catch (MalformedURLException e) {
            System.out.println("ERROR: MalformedURLException. WSDL is " + wsdl);
            throw e;
        }

        return helloWorld;
    }

    private static String getWsdl() throws IOException {
        Properties properties = new Properties();
        properties.load(HelloWorldImpl.class.getClassLoader().getResourceAsStream(PROPS_FILE));

        if (!properties.containsKey(PROPS_KEY)) {
            System.err.println("ERROR: Couldn't find " + PROPS_KEY + " in file " + PROPS_FILE);
        }

        return properties.getProperty(PROPS_KEY);
    }

}
