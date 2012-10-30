package br.usp.ime.futuremarket;

public final class Configuration {
    private static final Configuration INSTANCE = new Configuration();
    private String registryWsdl = "http://127.0.0.1:8080/registry/endpoint?wsdl";

    private Configuration() {
    }

    public static Configuration getInstance() {
        return INSTANCE;
    }

    public void setRegistryWsdl(final String wsdl) {
        registryWsdl = wsdl;
    }

    public String getRegistryWsdl() {
        return registryWsdl;
    }
}