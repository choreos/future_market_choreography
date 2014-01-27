package br.usp.ime.futuremarket;

import java.io.IOException;
import java.util.Arrays;

import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.EEClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceType;

import br.usp.ime.futuremarket.choreography.WSInfo;

public class Enacter {

	private static final String EE_HOST = "http://localhost:9100/enactmentengine/";
//	private static final String REPO = "http://www.linux.ime.usp.br/~cadu/futuremarket/";
	private static final String REPO = "http://www.ime.usp.br/~tfurtado/fm_repo/";
	private static final String REG_NAME = "registry";
	private static final String REG_ROLE = Role.REGISTRY.toString();

	private DeployableServiceSpec registrySpec;

	public Choreography enact() throws EnactmentException,
			ChoreographyNotFoundException {
		final ChoreographySpec chorSpec = getChorSpec();
		
		final EEClient eeClient = new EEClient(EE_HOST);
		final String chorId = eeClient.createChoreography(chorSpec);
		final Choreography chor = eeClient.enactChoreography(chorId);

		return chor;
	}

	private ChoreographySpec getChorSpec() {
		final ChoreographySpec spec = new ChoreographySpec();

		registrySpec = getServiceSpec("registry");
		spec.addServiceSpec(registrySpec);

		spec.addServiceSpec(getServiceSpec("bank"));

		spec.addServiceSpec(getServiceSpec("manufacturer"));

		spec.addServiceSpec(getServiceSpec("portal1"));
		spec.addServiceSpec(getServiceSpec("portal2"));

		spec.addServiceSpec(getServiceSpec("shipper1"));
		spec.addServiceSpec(getServiceSpec("shipper2"));

		spec.addServiceSpec(getServiceSpec("supermarket1"));
		spec.addServiceSpec(getServiceSpec("supermarket2"));
		spec.addServiceSpec(getServiceSpec("supermarket3"));
		spec.addServiceSpec(getServiceSpec("supermarket4"));
		spec.addServiceSpec(getServiceSpec("supermarket5"));

		spec.addServiceSpec(getServiceSpec("supplier1"));
		spec.addServiceSpec(getServiceSpec("supplier2"));
		spec.addServiceSpec(getServiceSpec("supplier3"));

		return spec;
	}

	private DeployableServiceSpec getServiceSpec(final String name) {
		DeployableServiceSpec service = new DeployableServiceSpec();
		service.setName(name);
		service.setServiceType(ServiceType.SOAP);
		service.setPackageUri(REPO + name + ".war");
		service.setPackageType(PackageType.TOMCAT);
		service.setNumberOfInstances(1);
		service.setEndpointName(getEndpoint(name));
		service.setRoles(Arrays.asList(getRole(name)));

		addDependencies(name, service);

		return service;
	}

	private void addDependencies(final String name,
			final DeployableServiceSpec service) {
		if (!REG_NAME.equals(name)) {
			ServiceDependency dep = new ServiceDependency();
			dep.setServiceSpecName(REG_NAME);
			dep.setServiceSpecRole(REG_ROLE);
			service.addDependency(dep);

			ServiceDependency regDep = new ServiceDependency();
			regDep.setServiceSpecName(name);
			regDep.setServiceSpecRole(service.getRoles().get(0));
			registrySpec.addDependency(regDep);
		}
	}

	private String getRole(final String name) {
		final AbstractWSInfo info = new WSInfo();
		info.setName(name);
		return info.getRole().toString();
	}

	private String getEndpoint(final String name) {
		String endpoint;

		if (REG_NAME.equals(name)) {
			endpoint = "endpoint";
		} else {
			endpoint = "choreography";
		}

		return endpoint;
	}
}
