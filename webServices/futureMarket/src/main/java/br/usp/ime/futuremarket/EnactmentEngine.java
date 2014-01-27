package br.usp.ime.futuremarket;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding
public interface EnactmentEngine {

    @WebMethod
    String setInvocationAddress(final String role, final String name, final List<String> endpoints);
}