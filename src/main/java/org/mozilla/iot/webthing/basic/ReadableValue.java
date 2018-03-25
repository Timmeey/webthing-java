package org.mozilla.iot.webthing.basic;

import java.util.Observable;
import java.util.function.Supplier;

/**
 * ReadableValue.
 * @author Tim Hinkes (timmeey@timmeey.de)
 * @version $Id:\$
 * @since
 */
public class ReadableValue<T> extends Observable {
    protected final Supplier<T> currentValue;

    public ReadableValue(final Supplier<T> currentValue) {
        this.currentValue = currentValue;
    }

    public final T get(){
        return this.currentValue.get();
    }

    public final void notifyOfExternalUpdate(T value){
        super.notifyObservers(value);
    }
}
