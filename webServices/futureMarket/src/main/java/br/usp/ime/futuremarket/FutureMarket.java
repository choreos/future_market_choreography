package br.usp.ime.futuremarket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class FutureMarket {
    private static Registry registry;
    static final ClassLoader loader = FutureMarket.class.getClassLoader();

    private static final String NAMESPACE = "http://futuremarket.ime.usp.br";

    private static final String REGISTRY_SERVICE = "RegistryImplService";

    public static final String SHIPPER_ROLE = "Shipper";
    public static final String SHIPPER_SERVICE = "ShipperImplService";

    public static final String SUPERMARKET_ROLE = "Supermarket";
    public static final String SUPERMARKET_SERVICE = "SupermarketImplService";

    public static final String SUPPLIER_ROLE = "Supplier";
    public static final String MANUFACTURER_ROLE = "Manufacturer";

    public static final String ORCHESTRATOR_ROLE = "Orchestrator";
    public static final String ORCHESTRATOR_SERVICE = "OrchestratorImplService";
    
    public static final String BANK_ROLE = "Bank";
    public static final String BANK_SERVICE = "BankImplService";

    public FutureMarket() {

    }
    
    private static Registry getRegistry() {
    	if (registry == null) {
            registry = getRegistryClient();
        }
    	return registry;
    }

    protected void register(final String role, String name, final String relativePath) {
        final String wsdl = getMyWsdl(relativePath);

        getRegistry().add(role, name, wsdl);
    }

    private static Registry getRegistryClient() {
        final String wsdl = getRegistryWsdl();
        return getClient(Registry.class, wsdl, REGISTRY_SERVICE);
    }

    public <T> T getFirstClient(final String role, final String serviceName,
            final Class<T> resultClass) {
        final String wsdl = getRegistry().getFirst(role);

        return getClient(resultClass, wsdl, serviceName);
    }
    
    public <T> T getClientByName(final String name, final String serviceName,
            final Class<T> resultClass) {
        final String wsdl = getRegistry().getServiceEndpoint(name);

        return getClient(resultClass, wsdl, serviceName);
    }

    public <T> List<T> getClients(final String role, final String serviceName,
            final Class<T> resultClass) {
        final List<T> clients = new ArrayList<T>();
        final List<String> wsdls = getRegistry().getList(role);

        T t;
        for (String wsdl : wsdls) {
            t = getClient(resultClass, wsdl, serviceName);
            clients.add(t);
        }

        return clients;
    }

    private static <T> T getClient(final Class<T> resultClass, final String wsdl, final String serviceName) {
        final QName qname = new QName(NAMESPACE, serviceName);
        URL url = null;

        try {
            url = new URL(wsdl);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURL: " + wsdl);
            e.printStackTrace();
        }

        final Service service = Service.create(url, qname);

        return service.getPort(resultClass);
    }

    private static String getRegistryWsdl() {
        final Properties properties = new Properties();

        try {
            properties.load(loader.getResourceAsStream("config.properties"));
        } catch (IOException e) {
            System.out.println("Could not read resources/config.properties");
        }

        return properties.getProperty("registry.wsdl");
    }

    public String getMyWsdl(final String relativePath) {
        final String hostName = getMyHostName();

        return "http://" + hostName + ":8080/" + relativePath + "?wsdl";
    }

    private String getMyHostName() {
        InetAddress addr = null;

        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Unknown host while discovering my own wsdl");
            e.printStackTrace();
        }

        return addr.getCanonicalHostName();
    }
    
    public void reset(String role) {
    	for(String endpoint: getRegistry().getList(role)) {
			Supermarket supermarket = getClient(Supermarket.class, endpoint, FutureMarket.SUPERMARKET_SERVICE);
			supermarket.reset();
		}
    }
}