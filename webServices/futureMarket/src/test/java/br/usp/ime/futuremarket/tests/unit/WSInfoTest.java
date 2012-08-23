package br.usp.ime.futuremarket.tests.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.usp.ime.futuremarket.Role;
import br.usp.ime.futuremarket.choreography.WSInfo;

public class WSInfoTest {
    private final WSInfo info = new WSInfo();
    private static final String SMSERVICENAME = "SupermarketImplService";

    @Test
    public void testRoles() {
        info.setName(Role.BANK.toString());
        assertEquals(Role.BANK, info.getRole());

        info.setName(Role.MANUFACTURER.toString());
        assertEquals(Role.MANUFACTURER, info.getRole());

        info.setName(Role.SHIPPER.toString());
        assertEquals(Role.SHIPPER, info.getRole());
        info.setName(Role.SHIPPER.toString() + 42);
        assertEquals(Role.SHIPPER, info.getRole());

        info.setName(Role.SUPERMARKET.toString());
        assertEquals(Role.SUPERMARKET, info.getRole());
        info.setName(Role.SUPERMARKET.toString() + 42);
        assertEquals(Role.SUPERMARKET, info.getRole());
    }

    @Test
    public void testNamespaces() {
        info.setRole(Role.BANK);
        assertEquals("http://futuremarket.ime.usp.br/bank", info.getNamespace());

        info.setRole(Role.SUPERMARKET);
        assertEquals("http://futuremarket.ime.usp.br/choreography/supermarket", info.getNamespace());
    }

    @Test
    public void testServiceNames() {
        info.setName("registry");
        assertEquals("RegistryImplService", info.getServiceName());

        info.setRole(Role.SUPERMARKET);
        assertEquals(SMSERVICENAME, info.getServiceName());

        info.setRole(Role.SUPPLIER);
        assertEquals(SMSERVICENAME, info.getServiceName());

        info.setRole(Role.MANUFACTURER);
        assertEquals(SMSERVICENAME, info.getServiceName());
    }

    @Test
    public void testRoleFromBaseAddress() {
        info.setBaseAddress("http://127.0.0.1:8080/supermarket42");
        assertEquals(Role.SUPERMARKET, info.getRole());
    }

    @Test
    public void testRoleFromServiceName() {
        info.setServiceName("SupermarketImplService");
        assertEquals(Role.SUPERMARKET, info.getRole());
    }
}