package br.usp.ime.futuremarket;

import org.ow2.choreos.enactment.ChoreographyNotFoundException;
import org.ow2.choreos.enactment.EnactmentException;

public class Main {

    public static void main(final String[] args) throws EnactmentException, ChoreographyNotFoundException {
        final Enacter enacter = new Enacter();
        enacter.enact();
    }

}
