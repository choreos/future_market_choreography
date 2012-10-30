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

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public abstract class AbstractFutureMarket {
    private static final String PORT = "8080";
    private static final Map<String, Service> CACHE = new HashMap<String, Service>();
    private String registryWsdl;

    abstract protected String baseAddressToWsdl(final String baseAddress);

    abstract protected AbstractWSInfo getWSInfo();

    public void setRegistryWsdl(final String wsdl) {
        registryWsdl = wsdl;
    }

    /**
     * Use registry WSDL from properties file
     * 
     * @param serviceName
     *            war file basename (e.g.: supermarket5)
     * @throws IOException
     */
    public void register(final String serviceName) throws IOException {
        final String baseAddr = getMyBaseAddress(serviceName);
        final String role = getRole(serviceName);
        getRegistry().addService(role, serviceName, baseAddr);
    }

    private String getRole(final String name) {
        final AbstractWSInfo info = getWSInfo();
        info.setName(name);
        return info.getRole().toString();
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
        final Service service = CACHE.get(wsdl);

        return service.getPort(resultClass);
    }

    private void checkCache(final AbstractWSInfo info, final String wsdl)
            throws MalformedURLException {
        if (!CACHE.containsKey(wsdl)) {
            final String namespace = info.getNamespace();
            final String serviceName = info.getServiceName();
            cacheService(namespace, serviceName, wsdl);
        }
    }

    private void cacheService(final String namespace, final String serviceName, final String wsdl)
            throws MalformedURLException {
        synchronized (CACHE) {
            if (!CACHE.containsKey(wsdl)) {
                final Service service = createService(namespace, serviceName, wsdl);
                CACHE.put(wsdl, service);
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
        if (!CACHE.containsKey(registryWsdl)) {
            final AbstractWSInfo info = getWSInfo();
            info.setName("registry");
            checkCache(info, registryWsdl);
        }

        final Service service = CACHE.get(registryWsdl);
        return service.getPort(Registry.class);
    }
}