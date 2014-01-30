package br.usp.ime.futuremarket;

import java.util.EnumSet;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractWSInfo {
    private final static String NSPREFIX = "http://futuremarket.ime.usp.br";
    private static final Pattern NAMERX = Pattern.compile("\\D+");

    private Role role;
    private final EnumSet<Role> smAliases;
    private final EnumSet<Role> twoVersionRoles;

    /**
     * 
     * @param name
     *            Name or Role (e.g. shipper, shipper2)
     */
    public AbstractWSInfo() {
        smAliases = EnumSet.of(Role.SUPERMARKET, Role.MANUFACTURER, Role.SUPPLIER);
        twoVersionRoles = EnumSet.of(Role.SUPERMARKET, Role.PORTAL);
    }

    abstract protected String getArchType();

    public void setName(final String name) {
        final Matcher matcher = NAMERX.matcher(name);

        String roleValue;
        if (matcher.find()) {
            roleValue = matcher.group();
        } else {
            roleValue = "";
        }

        role = Role.getByValue(roleValue);
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    /**
     * 
     * @param address http://ipOrHostName[:port]/name/
     */
    public void setEndpoint(final String address) {
        final String noEndSlash = address.substring(0, address.length() - 1) ;
        final int lastSlash = noEndSlash.lastIndexOf('/');
        final String name = noEndSlash.substring(lastSlash + 1);
        setName(name);
    }

    public Role getRole() {
        return role;
    }

    public String getServiceName() {
        final Role realRole = getRealRole();
        return ucFirst(realRole.toString()) + "ImplService";
    }

    public String getNamespace() {
        final Role realRole = getRealRole();
        String namespace;

        if (twoVersionRoles.contains(realRole)) {
            namespace = NSPREFIX + "/" + getArchType() + "/" + realRole;
        } else {
            namespace = NSPREFIX + "/" + realRole;
        }

        return namespace;
    }

    private String ucFirst(final String word) {
        final String firstLetter = word.substring(0, 1);
        return firstLetter.toUpperCase(Locale.US) + word.substring(1);
    }

    private Role getRealRole() {
        return smAliases.contains(role) ? Role.SUPERMARKET : role;
    }
}