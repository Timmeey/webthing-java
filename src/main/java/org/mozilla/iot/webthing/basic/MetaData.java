package org.mozilla.iot.webthing.basic;

/**
 * MetaData.
 * @author Tim Hinkes (timmeey@timmeey.de)
 * @version $Id:\$
 * @since
 */
public class MetaData<T> {

    private final String key;

    private final T value;

    public MetaData(final String key, final T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public T getValue() {
        return this.value;
    }
}
