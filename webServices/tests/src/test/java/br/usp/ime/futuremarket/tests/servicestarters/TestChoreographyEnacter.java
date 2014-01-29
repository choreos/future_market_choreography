package br.usp.ime.futuremarket.tests.servicestarters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.commons.lang.StringUtils;

import br.usp.ime.futuremarket.Bank;
import br.usp.ime.futuremarket.Registry;
import br.usp.ime.futuremarket.Shipper;
import br.usp.ime.futuremarket.Supermarket;
import br.usp.ime.futuremarket.choreography.Portal;

public class TestChoreographyEnacter {

	public static void main(String[] args) {
		System.out.println("Enacting...");
		RegistryStarter.main(args);
		BankStarter.main(args);
		SupermarketsStarter.main(args);
		ShippersStarter.main(args);
		PortalsStarter.main(args);

		performSetInvocationAddress();
		System.out.println("Enactment completed!");
	}

	private static void performSetInvocationAddress() {
		try {

			Registry registry = getClient(Registry.class,
					RegistryStarter.REGISTRY_ADDRESS,
					"http://futuremarket.ime.usp.br/registry",
					RegistryStarter.REGISTRY);

			registry.setInvocationAddress("bank", BankStarter.BANK,
					Collections.singletonList(BankStarter.BANK_ADDRESS));

			registry.setInvocationAddress(
					"manufacturer",
					SupermarketsStarter.MANUFACTURER,
					Collections
							.singletonList(SupermarketsStarter.MANUFACTURER_ADDRESS));

			registry.setInvocationAddress(
					"supplier",
					SupermarketsStarter.SUPPLIER1,
					Collections
							.singletonList(SupermarketsStarter.SUPPLIER1_ADDRESS));
			registry.setInvocationAddress(
					"supplier",
					SupermarketsStarter.SUPPLIER2,
					Collections
							.singletonList(SupermarketsStarter.SUPPLIER2_ADDRESS));
			registry.setInvocationAddress(
					"supplier",
					SupermarketsStarter.SUPPLIER3,
					Collections
							.singletonList(SupermarketsStarter.SUPPLIER3_ADDRESS));

			registry.setInvocationAddress(
					"supermarket",
					SupermarketsStarter.SUPERMARKET1,
					Collections
							.singletonList(SupermarketsStarter.SUPERMARKET1_ADDRESS));
			registry.setInvocationAddress(
					"supermarket",
					SupermarketsStarter.SUPERMARKET2,
					Collections
							.singletonList(SupermarketsStarter.SUPERMARKET2_ADDRESS));
			registry.setInvocationAddress(
					"supermarket",
					SupermarketsStarter.SUPERMARKET3,
					Collections
							.singletonList(SupermarketsStarter.SUPERMARKET3_ADDRESS));
			registry.setInvocationAddress(
					"supermarket",
					SupermarketsStarter.SUPERMARKET4,
					Collections
							.singletonList(SupermarketsStarter.SUPERMARKET4_ADDRESS));
			registry.setInvocationAddress(
					"supermarket",
					SupermarketsStarter.SUPERMARKET5,
					Collections
							.singletonList(SupermarketsStarter.SUPERMARKET5_ADDRESS));

			registry.setInvocationAddress("shipper", ShippersStarter.SHIPPER1,
					Collections.singletonList(ShippersStarter.SHIPPER1_ADDRESS));
			registry.setInvocationAddress("shipper", ShippersStarter.SHIPPER2,
					Collections.singletonList(ShippersStarter.SHIPPER2_ADDRESS));

			registry.setInvocationAddress("portal", PortalsStarter.PORTAL1,
					Collections.singletonList(PortalsStarter.PORTAL1_ADDRESS));
			registry.setInvocationAddress("portal", PortalsStarter.PORTAL2,
					Collections.singletonList(PortalsStarter.PORTAL2_ADDRESS));

			Bank bank = getClient(Bank.class, BankStarter.BANK_ADDRESS,
					"http://futuremarket.ime.usp.br/bank", BankStarter.BANK);
			bank.setInvocationAddress("registry", RegistryStarter.REGISTRY,
					Collections.singletonList(RegistryStarter.REGISTRY_ADDRESS));

			Supermarket manufacturer = getClient(Supermarket.class,
					SupermarketsStarter.MANUFACTURER_ADDRESS,
					"http://futuremarket.ime.usp.br/choreography/supermarket",
					SupermarketsStarter.SUPERMARKET);
			manufacturer
					.setInvocationAddress(
							"registry",
							RegistryStarter.REGISTRY,
							Collections
									.singletonList(RegistryStarter.REGISTRY_ADDRESS));

			Supermarket supplier1 = getClient(Supermarket.class,
					SupermarketsStarter.SUPPLIER1_ADDRESS,
					"http://futuremarket.ime.usp.br/choreography/supermarket",
					SupermarketsStarter.SUPERMARKET);
			Supermarket supplier2 = getClient(Supermarket.class,
					SupermarketsStarter.SUPPLIER2_ADDRESS,
					"http://futuremarket.ime.usp.br/choreography/supermarket",
					SupermarketsStarter.SUPERMARKET);
			Supermarket supplier3 = getClient(Supermarket.class,
					SupermarketsStarter.SUPPLIER3_ADDRESS,
					"http://futuremarket.ime.usp.br/choreography/supermarket",
					SupermarketsStarter.SUPERMARKET);
			supplier1
					.setInvocationAddress(
							"registry",
							RegistryStarter.REGISTRY,
							Collections
									.singletonList(RegistryStarter.REGISTRY_ADDRESS));
			supplier2
					.setInvocationAddress(
							"registry",
							RegistryStarter.REGISTRY,
							Collections
									.singletonList(RegistryStarter.REGISTRY_ADDRESS));
			supplier3
					.setInvocationAddress(
							"registry",
							RegistryStarter.REGISTRY,
							Collections
									.singletonList(RegistryStarter.REGISTRY_ADDRESS));

			Supermarket supermarket1 = getClient(Supermarket.class,
					SupermarketsStarter.SUPERMARKET1_ADDRESS,
					"http://futuremarket.ime.usp.br/choreography/supermarket",
					SupermarketsStarter.SUPERMARKET);
			Supermarket supermarket2 = getClient(Supermarket.class,
					SupermarketsStarter.SUPERMARKET2_ADDRESS,
					"http://futuremarket.ime.usp.br/choreography/supermarket",
					SupermarketsStarter.SUPERMARKET);
			Supermarket supermarket3 = getClient(Supermarket.class,
					SupermarketsStarter.SUPERMARKET3_ADDRESS,
					"http://futuremarket.ime.usp.br/choreography/supermarket",
					SupermarketsStarter.SUPERMARKET);
			Supermarket supermarket4 = getClient(Supermarket.class,
					SupermarketsStarter.SUPERMARKET4_ADDRESS,
					"http://futuremarket.ime.usp.br/choreography/supermarket",
					SupermarketsStarter.SUPERMARKET);
			Supermarket supermarket5 = getClient(Supermarket.class,
					SupermarketsStarter.SUPERMARKET5_ADDRESS,
					"http://futuremarket.ime.usp.br/choreography/supermarket",
					SupermarketsStarter.SUPERMARKET);
			supermarket1
					.setInvocationAddress(
							"registry",
							RegistryStarter.REGISTRY,
							Collections
									.singletonList(RegistryStarter.REGISTRY_ADDRESS));
			supermarket2
					.setInvocationAddress(
							"registry",
							RegistryStarter.REGISTRY,
							Collections
									.singletonList(RegistryStarter.REGISTRY_ADDRESS));
			supermarket3
					.setInvocationAddress(
							"registry",
							RegistryStarter.REGISTRY,
							Collections
									.singletonList(RegistryStarter.REGISTRY_ADDRESS));
			supermarket4
					.setInvocationAddress(
							"registry",
							RegistryStarter.REGISTRY,
							Collections
									.singletonList(RegistryStarter.REGISTRY_ADDRESS));
			supermarket5
					.setInvocationAddress(
							"registry",
							RegistryStarter.REGISTRY,
							Collections
									.singletonList(RegistryStarter.REGISTRY_ADDRESS));

			Shipper shipper1 = getClient(Shipper.class,
					ShippersStarter.SHIPPER1_ADDRESS,
					"http://futuremarket.ime.usp.br/shipper",
					ShippersStarter.SHIPPER);
			Shipper shipper2 = getClient(Shipper.class,
					ShippersStarter.SHIPPER2_ADDRESS,
					"http://futuremarket.ime.usp.br/shipper",
					ShippersStarter.SHIPPER);
			shipper1.setInvocationAddress("registry", RegistryStarter.REGISTRY,
					Collections.singletonList(RegistryStarter.REGISTRY_ADDRESS));
			shipper2.setInvocationAddress("registry", RegistryStarter.REGISTRY,
					Collections.singletonList(RegistryStarter.REGISTRY_ADDRESS));

			Portal portal1 = getClient(Portal.class,
					PortalsStarter.PORTAL1_ADDRESS,
					"http://futuremarket.ime.usp.br/choreography/portal",
					PortalsStarter.PORTAL);
			Portal portal2 = getClient(Portal.class,
					PortalsStarter.PORTAL2_ADDRESS,
					"http://futuremarket.ime.usp.br/choreography/portal",
					PortalsStarter.PORTAL);
			portal1.setInvocationAddress("registry", RegistryStarter.REGISTRY,
					Collections.singletonList(RegistryStarter.REGISTRY_ADDRESS));
			portal2.setInvocationAddress("registry", RegistryStarter.REGISTRY,
					Collections.singletonList(RegistryStarter.REGISTRY_ADDRESS));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private static <T> T getClient(final Class<T> resultClass,
			final String endpoint, final String namespace,
			final String serviceName) throws MalformedURLException {
		final String wsdl = endpoint + "?wsdl";

		final Service service = createService(namespace, StringUtils.capitalize(serviceName) + "ImplService", wsdl);

		return service.getPort(resultClass);
	}

	private static Service createService(final String namespace,
			final String serviceName, final String wsdl)
			throws MalformedURLException {
		final QName qname = new QName(namespace, serviceName);
		final URL url = new URL(wsdl);
		return Service.create(url, qname);
	}
}
