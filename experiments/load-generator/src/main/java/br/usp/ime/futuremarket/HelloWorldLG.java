package br.usp.ime.futuremarket;

import java.io.IOException;
import java.util.Calendar;

public class HelloWorldLG {
    private HelloWorld helloWorld;
    private static final String NAME = "World";
    private static final String EXPECTED = "Hello, World!";

    // Requests per minute
    private static int INITIAL_SPEED = 1;
    private static int ENDING_SPEED = 5000;

    public static void main(String args[]) throws IOException, InterruptedException {
        HelloWorldLG helloWorldLG = new HelloWorldLG();
        helloWorldLG.simulate();
    }

    public HelloWorldLG() throws IOException {
        helloWorld = HelloWorldImpl.getWSClient();
    }

    public void simulate() throws InterruptedException {
        int speed;

        for (speed = INITIAL_SPEED; speed <= ENDING_SPEED; speed++) {
            System.out.println("# " + speed + " req/min");
            simulate(speed);
        }
    }

    private void simulate(final int speed) throws InterruptedException {
        final long gap = 60 * 1000 / speed;
        final long start = Calendar.getInstance().getTimeInMillis();
        final long end = start + 60 * 1000;
        long nextRequest = start + gap;
        String answer;

        while (nextRequest <= end) {
            Thread.sleep(nextRequest - Calendar.getInstance().getTimeInMillis());

            System.out.print(Calendar.getInstance().getTimeInMillis());
            answer = helloWorld.sayHello(NAME);
            System.out.println(" " + Calendar.getInstance().getTimeInMillis());

            checkAnswer(answer);
            nextRequest += gap;
        }
    }

    private void checkAnswer(final String answer) {
        if (!answer.equals(EXPECTED)) {
            System.out.println("ERROR: received '" + answer + "', expected '" + EXPECTED + "'.");
        }
    }
}
