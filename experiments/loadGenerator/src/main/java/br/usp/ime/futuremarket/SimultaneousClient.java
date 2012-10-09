package br.usp.ime.futuremarket;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Set;

public class SimultaneousClient extends AbstractClient implements Runnable {

    public SimultaneousClient(final int threadNumber) throws MalformedURLException {
        super(threadNumber);
    }

    @Override
    public void run() {
        latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException e1) {
            logError("CountDownLatch await error", e1);
        }

        try {
            simulate();
        } catch (IOException e) {
            logError("simulate()", e);
        }
    }

    @Override
    protected void simulate() throws IOException {
        final long start = Calendar.getInstance().getTimeInMillis();

        final ShopList list = getLowestPriceList();
        final Set<Purchase> purchases = purchase(list);
        requestDeliveries(purchases);

        final long end = Calendar.getInstance().getTimeInMillis();
        GRAPH.info(end - start);
    }
}