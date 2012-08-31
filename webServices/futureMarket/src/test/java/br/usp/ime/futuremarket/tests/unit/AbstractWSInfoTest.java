package br.usp.ime.futuremarket.tests.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.usp.ime.futuremarket.AbstractWSInfo;
import br.usp.ime.futuremarket.Role;

public abstract class AbstractWSInfoTest {
    protected final AbstractWSInfo info = getWSInfo();
    private static final String SMSERVICENAME = "SupermarketImplService";
    
    protected abstract AbstractWSInfo getWSInfo();

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
        info.setBaseAddress("http://example.com:8080/supermarket42");
        assertEquals(Role.SUPERMARKET, info.getRole());
    }

    @Test
    public void testRoleFromServiceName() {
        info.setServiceName("SupermarketImplService");
        assertEquals(Role.SUPERMARKET, info.getRole());
    }
}