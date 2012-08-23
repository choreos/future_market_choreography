package br.usp.ime.futuremarket;

import java.util.EnumSet;

public enum Role {
    BANK, MANUFACTURER, PORTAL, REGISTRY, SHIPPER, SUPERMARKET, SUPPLIER;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static Role getByValue(final String value) {
        Role role = null;

        for (final Role element : EnumSet.allOf(Role.class)) {
            if (value.equals(element.toString())) {
                role = element;
            }
        }

        return role;
    }
}