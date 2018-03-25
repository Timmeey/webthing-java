package org.mozilla.iot.webthing.basic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;

/**
 * Property.
 * @author Tim Hinkes (timmeey@timmeey.de)
 * @version $Id:\$
 * @since
 */
public class Property<T> {
    private final Thing thing;
    private final String name;
    private String hrefPrefix;
    private final String href;
    private final Map<String, Object> metadata;
    private final Optional<String> propertyDescription;
    private final SettableValue<T> underlyingValue;
    private final PropertyType type;

    public Property(Thing thing,
        String name,
        PropertyType type,
        String propertyDescription,
        SettableValue<T> underlyingValue,
        MetaData<Object>... metadata) {
        this.thing = thing;
        this.underlyingValue = underlyingValue;
        this.metadata = new HashMap<>(metadata.length);
        Arrays.stream(metadata).forEach(md ->this.metadata.put(md.getKey(),md.getValue()));
        this.hrefPrefix = "";
        this.name = name;
        this.href = String.format("/properties/%s", this.name);
        this.propertyDescription = Optional.ofNullable(propertyDescription);
        this.type = type;
        //we dont care about the content of the change event, just the event
        this.underlyingValue.addObserver((a,b)->thing.propertyNotify(this));
    }

    /**
     * Set the prefix of any hrefs associated with this property.
     * @param prefix The prefix
     */
    public void setHrefPrefix(String prefix) {
        this.hrefPrefix = prefix;
    }

    /**
     * Get the property description.
     * @return Description of the property as an object.
     */
    public JSONObject asPropertyDescription() {
        JSONObject description = new JSONObject(this.metadata);
        this.propertyDescription.ifPresent(d ->
            description.put("description", d));
        Optional.ofNullable(type).ifPresent(t->
            description.put("type",t.getRepresentation()));
        description.put("href", this.hrefPrefix + this.href);
        return description;
    }

    /**
     * Get the current property value.
     * @return The current value.
     */
    public T getValue() {
        return this.underlyingValue.get();
    }

    /**
     * Get the name of this property.
     * @return The proeprty name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the thing associated with this property.
     * @return The thing.
     */
    public Thing getThing() {
        return this.thing;
    }

    /**
     * Set the current value of the property.
     * @param value The value to set
     */
    public void setValue(T value) {
        this.underlyingValue.set(value);
    }
}
