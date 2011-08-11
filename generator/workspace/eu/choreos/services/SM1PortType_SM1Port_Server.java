
package eu.choreos.services;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.4.1
 * 2011-08-11T16:07:14.481-03:00
 * Generated source version: 2.4.1
 * 
 */
 
public class SM1PortType_SM1Port_Server{

    protected SM1PortType_SM1Port_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new SM1PortTypeImpl();
        String address = "http://localhost:4321/WS/SM1";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new SM1PortType_SM1Port_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
