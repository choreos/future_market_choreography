package br.usp.ime.futuremarket.choreography;

import java.io.IOException;

import javax.jws.WebService;

import br.usp.ime.futuremarket.AbstractPortalImpl;

@WebService(targetNamespace = "http://futuremarket.ime.usp.br/choreography/portal",
        endpointInterface = "br.usp.ime.futuremarket.choreography.Portal")
public class PortalImpl extends AbstractPortalImpl {

    public PortalImpl() throws IOException {
        super();
    }
}