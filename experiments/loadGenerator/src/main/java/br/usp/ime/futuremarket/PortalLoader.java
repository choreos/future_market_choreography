package br.usp.ime.futuremarket;

import java.io.IOException;
import java.util.List;

import br.usp.ime.futuremarket.choreography.Portal;

public interface PortalLoader {
    List<Portal> getPortals() throws IOException;
}
