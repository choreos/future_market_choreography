package br.usp.ime.futuremarket.tests.integration.choreography;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.xmlbeans.XmlException;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.futuremarket.choreography.FutureMarket;
import br.usp.ime.futuremarket.choreography.WSInfo;
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
        return "choreography";
    }

    @Override
    protected WSInfo getWSInfo() {
        return new WSInfo();
    }

    @Test
    public void shouldNotContactPortal() throws WSDLException, MockDeploymentException,
            XmlException, IOException {
        intercept("portal");

        buy("product7", QT_INITIAL);
        List<Item> messages = interceptor.getMessages();
        assertEquals(0, messages.size());
    }
}