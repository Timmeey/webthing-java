package org.mozilla.iot.webthing;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.mozilla.iot.webthing.basic.MetaData;
import org.mozilla.iot.webthing.basic.Property;
import org.mozilla.iot.webthing.basic.PropertyType;
import org.mozilla.iot.webthing.basic.SettableValue;
import org.mozilla.iot.webthing.basic.Thing;

/**
 * DimmableRGBLight.
 * @author Tim Hinkes (timmeey@timmeey.de)
 */
public final class DimmableColorLight {

    private final SettableValue<String> color;
    private final SettableValue<Double> brightness;
    private final SettableValue<Boolean> on;
    private final String name;
    private final Optional<String> description;

    private DimmableColorLight(final SettableValue<String> color,
        final SettableValue<Double> brightness,
        final SettableValue<Boolean> on,
        final String name,
        final Optional<String> description) {

        this.color = color;
        this.brightness = brightness;
        this.on = on;
        this.name = name;
        this.description = description;
    }
    public DimmableColorLight(final SettableValue<String> color,
        final SettableValue<Double> brightness,
        final SettableValue<Boolean> on,
        final String name,
        String description) {
        this(color,brightness,on,name,Optional.of(description));
    }
    public DimmableColorLight(final SettableValue<String> color,
        final SettableValue<Double> brightness,
        final SettableValue<Boolean> on,
        final String name) {
        this(color,brightness,on,name,Optional.empty());
    }

    public final String getName() {
        return this.name;
    }

    public final Optional<String> getDescription() {
        return this.description;
    }

    public final Thing thing() {
        Thing thing;
        if(description.isPresent()){
            thing = new Thing(this.name,"dimmableColorLight",this.description.get());
        }else{
            thing = new Thing(this.name,"dimmableColorLight");
        }

        thing.addProperty(new Property(thing,"on", PropertyType.BOOLEAN, null, this.on));



        thing.addProperty(new Property(thing,
            "level",
            PropertyType.BOOLEAN,
            null,
            this.brightness,
            new MetaData("unit","percent"),
            new MetaData("min",0),
            new MetaData("max",100)
        ));


        Map<String, Object> colorDescription = new HashMap<>();
        thing.addProperty(new Property(thing,
            "color",
            PropertyType.STRING,
            null,
            this.color
        ));

        return thing;
    }
}
