package br.usp.ime.futuremarket.tests.unit.orchestration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.usp.ime.futuremarket.AbstractWSInfo;
import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.orchestration.WSInfo;
import br.usp.ime.futuremarket.tests.unit.AbstractWSInfoTest;

public class WSInfoTest extends AbstractWSInfoTest {

    @Override
    protected AbstractWSInfo getWSInfo() {
        return new WSInfo();
    }

    @Test
    public void testNamespace() {
        info.setRole(Role.SUPERMARKET);
        assertEquals("http://futuremarket.ime.usp.br/orchestration/supermarket",
                info.getNamespace());
    }

}