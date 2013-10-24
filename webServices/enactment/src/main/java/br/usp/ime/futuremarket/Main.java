package br.usp.ime.futuremarket;

import java.net.MalformedURLException;

import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.datamodel.Choreography;

public class Main {

    public static void main(final String[] args) throws EnactmentException,
            ChoreographyNotFoundException, MalformedURLException {
        final Enacter enacter = new Enacter();
        final Choreography chor = enacter.enact();
        enacter.registerServices(chor);
    }

}
