package br.usp.ime.futuremarket;

import org.ow2.choreos.enactment.ChoreographyNotFoundException;
import org.ow2.choreos.enactment.EnactmentException;
import org.ow2.choreos.enactment.client.EnactEngClient;
import org.ow2.choreos.enactment.datamodel.ChorServiceSpec;
import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.enactment.datamodel.ServiceDependence;
import org.ow2.choreos.servicedeployer.datamodel.ArtifactType;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;

import br.usp.ime.futuremarket.choreography.WSInfo;

public class Enacter {

    private static final String EE_HOST = "http://localhost:9102/enactmentengine";
//    private static final String REPO = "http://www.linux.ime.usp.br/~cadu/futuremarket/";
    private static final String REPO = "file://home/paulo/bin/apache-tomcat-7.0.25/webapps";
    private static final String REG_NAME = "registry";
    private static final String REG_ROLE = Role.REGISTRY.toString();

    public void enact() throws EnactmentException, ChoreographyNotFoundException {
        final ChorSpec chorSpec = getChorSpec();

        final EnactEngClient eeClient = new EnactEngClient(EE_HOST);
        final String chorId = eeClient.createChoreography(chorSpec);
        final Choreography chor = eeClient.enact(chorId);
        for(Service service: chor.getDeployedServices())
    		System.out.println(service.getUri());
    }

    private ChorSpec getChorSpec() {
        final ChorSpec spec = new ChorSpec();

//        spec.addServiceSpec(getServiceSpec("bank"));
//
//        spec.addServiceSpec(getServiceSpec("manufacturer"));
//        spec.addServiceSpec(getServiceSpec("portal1"));
//        spec.addServiceSpec(getServiceSpec("portal2"));
//        spec.addServiceSpec(getServiceSpec("portal3"));
//        spec.addServiceSpec(getServiceSpec("portal4"));
//
        spec.addServiceSpec(getServiceSpec("registry"));
//
//        spec.addServiceSpec(getServiceSpec("shipper1"));
//        spec.addServiceSpec(getServiceSpec("shipper2"));
//
//        spec.addServiceSpec(getServiceSpec("supermarket1"));
//        spec.addServiceSpec(getServiceSpec("supermarket2"));
//        spec.addServiceSpec(getServiceSpec("supermarket3"));
//        spec.addServiceSpec(getServiceSpec("supermarket4"));
//        spec.addServiceSpec(getServiceSpec("supermarket5"));
//
//        spec.addServiceSpec(getServiceSpec("supplier1"));
//        spec.addServiceSpec(getServiceSpec("supplier2"));
//        spec.addServiceSpec(getServiceSpec("supplier3"));

        return spec;
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
            final ServiceDependence dep = new ServiceDependence();
            dep.setServiceName(REG_NAME);
            dep.setServiceRole(REG_ROLE);
            service.getDependences().add(dep);
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
