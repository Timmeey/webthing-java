package org.mozilla.iot.webthing.basic;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Value.
 * @author Tim Hinkes (timmeey@timmeey.de)
 * @version $Id:\$
 * @since
 */
public final class SettableValue<T> extends ReadableValue<T> {
    private final Consumer<T> newValue;

    public SettableValue(final Consumer<T> newValue, final Supplier<T> currentValue) {
        super(currentValue);
        this.newValue = newValue;
    }

    public final void set(T value){
        newValue.accept(value);
        this.notifyOfExternalUpdate(value);
    }

}
