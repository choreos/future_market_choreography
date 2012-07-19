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
    List<String> getServicesForRole(String role);

    @WebMethod
    String getServiceForRole(String role);
    
    @WebMethod
    String getServiceForName(String name);
    
    @WebMethod
    String getServiceByIndex(final String role, final int index);

    @WebMethod
    String addService(String role, String name, String endpoint);

    @WebMethod
    String removeService(String role, String name);
}