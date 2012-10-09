package br.usp.ime.futuremarket;

import java.io.IOException;

public final class Main {

    private Main() {
    }

    public static void main(final String[] args) throws IOException {
        if (args.length == 0) {
            printHelp();
        } else if (args[0].equals("frequency")) {
            runLoadGenerator(new Frequency(), args);
        } else if (args[0].equals("simultaneous")) {
            runLoadGenerator(new Simultaneous(), args);
        } else {
            printHelp();
        }
    }

    private static void runLoadGenerator(final AbstractLoadGenerator loadGenerator,
            final String[] args) throws IOException {
        loadGenerator.generateLoad(args, 1);
    }

    @SuppressWarnings("PMD.SystemPrintln")
    private static void printHelp() {
        System.out.println("Parameters: {simultaneous,frequency}");
        System.out.println("Extra parameters for simultaneous client mode:");
        System.out.println("\t" + Simultaneous.getHelpMessage());
        System.out.println("Extra parameters for frequency mode:");
        System.out.println("\t" + Frequency.getHelpMessage());
    }
}