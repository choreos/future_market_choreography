
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package eu.choreos.services;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2011-08-11T16:07:17.961-03:00
 * Generated source version: 2.4.1
 * 
 */

@javax.jws.WebService(
                      serviceName = "SM3",
                      portName = "SM3Port",
                      targetNamespace = "http://services.choreos.eu/",
                      wsdlLocation = "file:SM3.wsdl",
                      endpointInterface = "eu.choreos.services.SM3PortType")
                      
public class SM3PortTypeImpl implements SM3PortType {

    private static final Logger LOG = Logger.getLogger(SM3PortTypeImpl.class.getName());

    /* (non-Javadoc)
     * @see eu.choreos.services.SM3PortType#getPrice(java.lang.String  arg0 )*
     */
    public double getPrice(java.lang.String arg0) { 
        LOG.info("Executing operation getPrice");
        System.out.println(arg0);
        try {
            double _return = SM3Data.getPrice(arg0);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
