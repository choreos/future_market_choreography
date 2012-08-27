package br.usp.ime.futuremarket.tests.integration.orchestration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.xmlbeans.XmlException;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.orchestration.FutureMarket;
import br.usp.ime.futuremarket.orchestration.WSInfo;
import br.usp.ime.futuremarket.tests.integration.AbstractSupermarketTest;
import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.exceptions.MockDeploymentException;
import eu.choreos.vv.exceptions.WSDLException;

public class SupermarketTest extends AbstractSupermarketTest {
    @BeforeClass
    public static void setFutureMarket() {
        market = new FutureMarket();
    }

    @Override
    protected String getArchType() {
        return "orchestration";
    }

    @Override
    protected WSInfo getWSInfo() {
        return new WSInfo();
    }

    @Test
    public void shouldContactPortal() throws WSDLException, MockDeploymentException, XmlException,
            IOException {
        intercept("portal");

        // One message to requestPayment and another to deliver
        buy("product7", QT_INITIAL - QT_TRIGGER - 1);
        List<Item> messages = interceptor.getMessages();
        assertEquals(2, messages.size());

    }

    @Test
    public void testSupplyStockOneLevel() throws WSDLException, MockDeploymentException,
            XmlException, IOException {
        intercept("portal");

        // 4 more messages to orch: smPurchase, reqPay, deliver, getDelivery
        buy("product7", QT_INITIAL);
        List<Item> messages = interceptor.getMessages();
        assertEquals(6, messages.size());
    }

    @Test
    public void testSupplyStockTwoLevels() throws WSDLException, MockDeploymentException,
            XmlException, IOException {
        intercept("portal");

        buy("product7", QT_INITIAL);
        /*
         * 2 more (manufactures does not make smPurchase and getDelivery
         * Supplier stock params are doubled
         */
        buy("product7", 2 * (QT_INITIAL - QT_TRIGGER) - QT_INITIAL);
        List<Item> messages = interceptor.getMessages();
        assertEquals(8, messages.size());
    }
}