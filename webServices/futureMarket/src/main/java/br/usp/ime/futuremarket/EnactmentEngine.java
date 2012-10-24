package br.usp.ime.futuremarket;

import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding
public interface EnactmentEngine {

    @WebMethod
    String setInvocationAddress(final String role, final String registryEndpoint)
            throws IOException;
}