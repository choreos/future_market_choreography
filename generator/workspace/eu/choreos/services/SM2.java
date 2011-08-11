package eu.choreos.services;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2011-08-11T16:07:16.269-03:00
 * Generated source version: 2.4.1
 * 
 */
@WebServiceClient(name = "SM2", 
                  wsdlLocation = "file:SM2.wsdl",
                  targetNamespace = "http://services.choreos.eu/") 
public class SM2 extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://services.choreos.eu/", "SM2");
    public final static QName SM2Port = new QName("http://services.choreos.eu/", "SM2Port");
    static {
        URL url = null;
        try {
            url = new URL("file:SM2.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SM2.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:SM2.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SM2(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SM2(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SM2() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SM2(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SM2(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SM2(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns SM2PortType
     */
    @WebEndpoint(name = "SM2Port")
    public SM2PortType getSM2Port() {
        return super.getPort(SM2Port, SM2PortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SM2PortType
     */
    @WebEndpoint(name = "SM2Port")
    public SM2PortType getSM2Port(WebServiceFeature... features) {
        return super.getPort(SM2Port, SM2PortType.class, features);
    }

}