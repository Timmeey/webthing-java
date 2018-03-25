package org.mozilla.iot.webthing.basic;

/**
 * Type.
 * @author Tim Hinkes (timmeey@timmeey.de)
 */
public enum PropertyType {
    BOOLEAN("boolean"),NUMBER("number"),STRING("string"),OBJECT("object");

    public String getRepresentation() {
        return this.representation;
    }

    private final String representation;
    PropertyType(final String representation) {
        this.representation=representation;
    }
}
