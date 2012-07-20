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

public abstract class AbstractFutureMarket {
    private static final String NAMESPACE = "http://futuremarket.ime.usp.br";
    private static final String PORT = "8080";
    private static Registry registry;

    abstract protected String baseAddressToWsdl(final String baseAddress);

    // Name is the name of the service == war file basename
    public void register(final String role, final String name) throws IOException {
        final String baseAddress = getMyBaseAddress(name);
        getRegistry().addService(role, name, baseAddress);
    }

    public <T> List<T> getClients(final String role, final String serviceName,
            final Class<T> resultClass) throws IOException {
        final List<String> baseAddresses = getRegistry().getServices(role);

        T client;
        String wsdl;
        final List<T> clients = new ArrayList<T>();
        for (String baseAddress : baseAddresses) {
            wsdl = baseAddressToWsdl(baseAddress);
            client = getClient(resultClass, wsdl, serviceName);
            clients.add(client);
        }

        return clients;
    }

    public <T> T getClientByRole(final String role, final String serviceName,
            final Class<T> resultClass) throws IOException {
        final String baseAddress = getRegistry().getServiceByRole(role);
        final String wsdl = baseAddressToWsdl(baseAddress);

        return getClient(resultClass, wsdl, serviceName);
    }

    public <T> T getClientByName(final String name, final String serviceName,
            final Class<T> resultClass) throws IOException {
        final String baseAddress = getRegistry().getServiceByName(name);
        final String wsdl = baseAddressToWsdl(baseAddress);

        return getClient(resultClass, wsdl, serviceName);
    }

    public <T> T getClientRoundRobin(final String role, final String serviceName,
            final Class<T> resultClass) throws IOException {
        final String baseAddress = getRegistry().getServiceRoundRobin(role);
        final String wsdl = baseAddressToWsdl(baseAddress);

        return getClient(resultClass, wsdl, serviceName);
    }

    // Need to be suffixed by orchestration/choreography (+ ?wsdl)
    public String getMyBaseAddress(final String name) throws UnknownHostException {
        final String hostName = getMyHostName();

        return "http://" + hostName + ":" + PORT + "/" + name;
    }

    public String getBaseAddress(final String name) throws IOException {
        return getRegistry().getServiceByName(name);
    }

    public List<String> getBaseAddresses(final String role) throws IOException {
        return getRegistry().getServices(role);
    }

    public <T> T getClient(final String baseAddress, final String serviceName,
            final Class<T> resultClass) throws MalformedURLException {
        final String wsdl = baseAddressToWsdl(baseAddress);
        return getClient(resultClass, wsdl, serviceName);
    }

    private static <T> T getClient(final Class<T> resultClass, final String wsdl,
            final String serviceName) throws MalformedURLException {
        final QName qname = new QName(NAMESPACE, serviceName);
        final URL url = new URL(wsdl);

        final Service service = Service.create(url, qname);

        return service.getPort(resultClass);
    }

    private Registry getRegistry() throws IOException {
        if (registry == null) {
            registry = getRegistryClient();
        }

        return registry;
    }

    private Registry getRegistryClient() throws IOException {
        final String wsdl = getRegistryWsdl();
        return getClient(Registry.class, wsdl, ServiceName.REGISTRY);
    }

    private String getRegistryWsdl() throws IOException {
        final Properties properties = new Properties();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        properties.load(loader.getResourceAsStream("config.properties"));
        return properties.getProperty("registry.wsdl");
    }

    private String getMyHostName() throws UnknownHostException {
        final InetAddress addr = InetAddress.getLocalHost();
        final String hostname = addr.getCanonicalHostName();

        return hostname;
    }
}
