package br.usp.ime.futuremarket;

import java.io.IOException;

public final class Main {
	private static AbstractLoadGenerator loadGen = null;

	private Main() {
	}

	public static void main(final String[] args) throws IOException {
		if (args.length < 2 || !readArgs(args)) {
			printHelp();
		} else {
			runLoadGenerator(loadGen, args);
		}
	}

	private static boolean readArgs(final String[] args) {
		setRegistry(args[0]);
		return setLoadGenerator(args[1]);
	}

	private static void setRegistry(final String endpoint) {
		Configuration.getInstance().setRegistryWsdl(endpoint + "?wsdl");
		System.out.println("Using registry wsdl " + Configuration.getInstance().getRegistryWsdl());
	}

	private static boolean setLoadGenerator(final String type) {
		if ("simultaneous".equals(type)) {
			loadGen = new Simultaneous();
		} else if ("frequency".equals(type)) {
			loadGen = new Frequency();
		}

		return (loadGen != null);
	}

	private static void runLoadGenerator(
			final AbstractLoadGenerator loadGenerator, final String[] args)
			throws IOException {
		loadGenerator.generateLoad(args, 2);
	}

	@SuppressWarnings("PMD.SystemPrintln")
	private static void printHelp() {
		System.out.println("Parameters: registryEndpoint {simultaneous,frequency}");
		System.out.println("Extra parameters for simultaneous client mode:");
		System.out.println("\t" + Simultaneous.getHelpMessage());
		System.out.println("Extra parameters for frequency mode:");
		System.out.println("\t" + Frequency.getHelpMessage());
	}
}