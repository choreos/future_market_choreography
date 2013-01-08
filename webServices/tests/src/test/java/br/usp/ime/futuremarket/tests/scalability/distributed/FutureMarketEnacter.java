package br.usp.ime.futuremarket.tests.scalability.distributed;

import org.ow2.choreos.chors.datamodel.ChorServiceSpec;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.ServiceDependency;
import org.ow2.choreos.deployment.services.datamodel.ArtifactType;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;

import br.usp.ime.futuremarket.AbstractWSInfo;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.choreography.WSInfo;
import eu.choreos.vv.enactment.EEEnacter;


public class FutureMarketEnacter extends EEEnacter {

    private static final String EE_HOST = "http://localhost:9102/enactmentengine";
//    private static final String REPO = "http://www.linux.ime.usp.br/~cadu/futuremarket/";
    private static final String REPO = "file://home/paulo/bin/apache-tomcat-7.0.25/webapps";
    public static final String REG_NAME = "registry";
    private static final String REG_ROLE = Role.REGISTRY.toString();
    
    public FutureMarketEnacter() {
    	super(EE_HOST);
    }
    
    @Override
    protected ChorSpec enactmentSpec() {
        final ChorSpec spec = new ChorSpec();

        spec.addServiceSpec(getServiceSpec("bank"));

        spec.addServiceSpec(getServiceSpec("manufacturer"));

        spec.addServiceSpec(getServiceSpec(REG_NAME));

        spec.addServiceSpec(getServiceSpec("shipper1"));
        spec.addServiceSpec(getServiceSpec("shipper2"));

        spec.addServiceSpec(getServiceSpec("supermarket1"));
        spec.addServiceSpec(getServiceSpec("supermarket2"));
        spec.addServiceSpec(getServiceSpec("supermarket3"));

        spec.addServiceSpec(getServiceSpec("supplier1"));
        spec.addServiceSpec(getServiceSpec("supplier2"));

        return spec;
    }
    
    protected ChorSpec scaleSpec(int index) {
    	final ChorSpec spec = new ChorSpec();
    	
    	spec.addServiceSpec(getPortalSpec("portal"+index));
    	spec.addServiceSpec(getLegacyRegistrySpec(REG_NAME));
    	
    	return spec;
    }

    private ChorServiceSpec getLegacyRegistrySpec(String name) {
    	final ChorServiceSpec service = new ChorServiceSpec();
        service.setName(name);
        service.setEndpointName(getServiceUri(REG_NAME));
        service.setPort(8080);
        service.setType(ServiceType.SOAP);
        service.setArtifactType(ArtifactType.LEGACY);
        service.getRoles().add(getRole(name));
        
        return service;
	}

	private ChorServiceSpec getPortalSpec(String name) {
		final ChorServiceSpec service = new ChorServiceSpec();
        service.setName(name);
        service.setCodeUri(REPO + name + ".war");
        service.setEndpointName(getEndpoint(name));
        service.setPort(8080);
        service.setType(ServiceType.SOAP);
        service.setArtifactType(ArtifactType.TOMCAT);
        service.getRoles().add(getRole(name));

        addDependencies(name, service);

        return service;
	}


	private ChorServiceSpec getServiceSpec(final String name) {
        final ChorServiceSpec service = new ChorServiceSpec();
        service.setName(name);
        service.setCodeUri(REPO + name + ".war");
        service.setEndpointName(getEndpoint(name));
        service.setPort(8080);
        service.setType(ServiceType.SOAP);
        service.setArtifactType(ArtifactType.TOMCAT);
        service.getRoles().add(getRole(name));

        addDependencies(name, service);

        return service;
    }

    private void addDependencies(final String name, final ChorServiceSpec service) {
        if (!REG_NAME.equals(name)) {
            final ServiceDependency dep = new ServiceDependency();
            dep.setServiceName(REG_NAME);
            dep.setServiceRole(REG_ROLE);
            service.getDependencies().add(dep);
        }
    }

    private String getRole(final String name) {
        final AbstractWSInfo info = new WSInfo();
        info.setName(name);
        return info.getRole().toString();
    }

    private String getEndpoint(final String name) {
        String endpoint = name;

        if (REG_NAME.equals(name)) {
            endpoint += "/endpoint";
        } else {
            endpoint += "/choreography";
        }

		return endpoint;
	}
}
