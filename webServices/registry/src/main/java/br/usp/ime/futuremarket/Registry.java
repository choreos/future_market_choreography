package br.usp.ime.futuremarket;

import java.util.HashMap;
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
	
    @WebMethod
    HashMap<String, List<String>> getServices(String role);

    @WebMethod
    List<String> getInstances(String service);

    @WebMethod
    String removeInstance(String endpoint);
}