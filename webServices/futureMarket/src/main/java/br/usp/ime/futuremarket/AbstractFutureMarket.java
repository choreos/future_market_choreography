package br.usp.ime.futuremarket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public abstract class AbstractFutureMarket {
    private static final String PORT = "8080";
    private static final Map<String, Service> serviceCache = new HashMap<String, Service>();
    private final String registryWsdl;

    abstract protected String baseAddressToWsdl(final String baseAddress);

    abstract protected AbstractWSInfo getWSInfo();

    public AbstractFutureMarket(final String registryWsdl) {
        this.registryWsdl = registryWsdl;
    }

    public AbstractFutureMarket() throws IOException {
        this(getRegistryWsdlFromProperties());
    }

    /**
     * Use registry WSDL from properties file
     * 
     * @param name
     *            of the service == war file basename (e.g.: supermarket5)
     * @throws IOException
     */
    public void register(final String name) throws IOException {
        final String baseAddr = getMyBaseAddress(name);
        final AbstractWSInfo info = getWSInfo();
        info.setName(name);
        getRegistry().addService(info.getRole().toString(), name, baseAddr);
    }

    public <T> List<T> getClients(final Role role, final Class<T> resultClass) throws IOException {
        final List<String> baseAddresses = getRegistry().getServices(role.toString());

        T client;
        final AbstractWSInfo info = getWSInfo();
        final List<T> clients = new ArrayList<T>();
        for (String baseAddress : baseAddresses) {
            info.setRole(role);
            client = getClient(resultClass, baseAddress, info);
            clients.add(client);
        }

        return clients;
    }

    public <T> T getClientByRole(final Role role, final Class<T> resultClass) throws IOException {
        final String baseAddress = getRegistry().getServiceByRole(role.toString());
        final AbstractWSInfo info = getWSInfo();
        info.setBaseAddress(baseAddress);

        return getClient(resultClass, baseAddress, info);
    }

    public <T> T getClientByName(final String name, final Class<T> resultClass) throws IOException {
        final String baseAddress = getRegistry().getServiceByName(name);
        final AbstractWSInfo info = getWSInfo();
        info.setName(name);

        return getClient(resultClass, baseAddress, info);
    }

    public <T> T getClientRoundRobin(final Role role, final Class<T> resultClass)
            throws IOException {
        final String baseAddress = getRegistry().getServiceRoundRobin(role.toString());
        final AbstractWSInfo info = getWSInfo();
        info.setRole(role);

        return getClient(resultClass, baseAddress, info);
    }

    // Need to be suffixed by orchestration/choreography + ?wsdl
    public String getMyBaseAddress(final String name) throws UnknownHostException {
        final String hostName = getMyHostName();
        return "http://" + hostName + ":" + PORT + "/" + name + "/";
    }

    public String getBaseAddress(final String name) throws IOException {
        return getRegistry().getServiceByName(name);
    }

    public List<String> getBaseAddresses(final Role role) throws IOException {
        return getRegistry().getServices(role.toString());
    }

    public <T> T getClient(final String baseAddress, final Class<T> resultClass)
            throws MalformedURLException {
        final AbstractWSInfo info = getWSInfo();
        info.setBaseAddress(baseAddress);

        return getClient(resultClass, baseAddress, info);
    }

    public void unregister(final String name) throws IOException {
        final AbstractWSInfo info = getWSInfo();
        info.setName(name);
        getRegistry().removeService(info.getRole().toString(), name);
    }

    private <T> T getClient(final Class<T> resultClass, final String baseAddress,
            final AbstractWSInfo info) throws MalformedURLException {
        final String wsdl = baseAddressToWsdl(baseAddress);

        checkCache(info, wsdl);
        final Service service = serviceCache.get(wsdl);

        return service.getPort(resultClass);
    }

    private void checkCache(final AbstractWSInfo info, final String wsdl)
            throws MalformedURLException {
        if (!serviceCache.containsKey(wsdl)) {
            final String namespace = info.getNamespace();
            final String serviceName = info.getServiceName();
            cacheService(namespace, serviceName, wsdl);
        }
    }

    private void cacheService(final String namespace, final String serviceName, final String wsdl)
            throws MalformedURLException {
        synchronized (serviceCache) {
            if (!serviceCache.containsKey(wsdl)) {
                final Service service = createService(namespace, serviceName, wsdl);
                serviceCache.put(wsdl, service);
            }
        }
    }

    /* Slow */
    private Service createService(final String namespace, final String serviceName,
            final String wsdl) throws MalformedURLException {
        final QName qname = new QName(namespace, serviceName);
        final URL url = new URL(wsdl);
        return Service.create(url, qname);
    }

    private String getMyHostName() throws UnknownHostException {
        final InetAddress addr = InetAddress.getLocalHost();
        return addr.getCanonicalHostName();
    }

    /**
     * Public for testing purposes.
     * 
     * @return Registry
     * @throws IOException
     */
    public Registry getRegistry() throws IOException {
        if (!serviceCache.containsKey(registryWsdl)) {
            final AbstractWSInfo info = getWSInfo();
            info.setName("registry");
            checkCache(info, registryWsdl);
        }

        final Service service = serviceCache.get(registryWsdl);
        return service.getPort(Registry.class);
    }

    private static String getRegistryWsdlFromProperties() throws IOException {
        final Properties properties = new Properties();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        properties.load(loader.getResourceAsStream("config.properties"));
        return properties.getProperty("registry.wsdl");
    }

}