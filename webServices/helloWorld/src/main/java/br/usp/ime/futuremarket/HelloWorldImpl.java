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
            System.out.println("ERROR: MalformedURLException: " + wsdl);
            throw e;
        }
        
        return helloWorld;
    }

    private static String getWsdl() throws IOException {
        Properties properties = new Properties();
        properties.load(HelloWorldImpl.class.getClassLoader().getResourceAsStream("config.properties"));
        
        return properties.getProperty("helloWorld.wsdl");
    }

}
