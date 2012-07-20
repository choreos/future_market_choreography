package br.usp.ime.futuremarket;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface Registry {

    @WebMethod
    List<String> getServices(String role);

    @WebMethod
    String getServiceByRole(String role);
    
    @WebMethod
    String getServiceByName(String name);
    
    @WebMethod
    String getServiceRoundRobin(String role);

    @WebMethod
    String addService(String role, String name, String endpoint);

    @WebMethod
    String removeService(String role, String name);
}