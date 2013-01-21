package br.usp.ime.futuremarket;

import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;

public class Main {

    public static void main(final String[] args) throws EnactmentException, ChoreographyNotFoundException {
        final Enacter enacter = new Enacter();
        enacter.enact();
    }

}
