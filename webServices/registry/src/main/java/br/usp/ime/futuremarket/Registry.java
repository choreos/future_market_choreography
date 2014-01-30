package br.usp.ime.futuremarket;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public interface Registry {

	@WebMethod
	String setInvocationAddress(String role, String service, List<String> endpoints); 
	
	/*
	 * Due to Map does not is supported by jaxb, we return a list. The list contains
	 * a Map abstraction in that way: [servicename,instance1,instance2,service2name,instance1,...].
	 */
    @WebMethod
    List<String> getServices(String role);

    @WebMethod
    List<String> getInstances(String service);

    @WebMethod
    String removeInstance(String endpoint);
}