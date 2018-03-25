package org.mozilla.iot.webthing.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.JSONObject;
import org.mozilla.iot.webthing.DimmableColorLight;
import org.mozilla.iot.webthing.basic.Action;
import org.mozilla.iot.webthing.basic.Event;
import org.mozilla.iot.webthing.basic.Thing;
import org.mozilla.iot.webthing.basic.SettableValue;
import org.mozilla.iot.webthing.basic.WebThingServer;

public class TestServer {
   /* public static Thing makeThing() {
        Thing thing = new Thing("My Lamp", "thing", "A web connected lamp");

        Map<String, Object> onDescription = new HashMap<>();
        onDescription.put("type", "boolean");
        onDescription.put("description", "Whether the lamp is turned on");
        thing.addProperty(new Property(thing, "on", onDescription, true));

        Map<String, Object> levelDescription = new HashMap<>();
        levelDescription.put("type", "number");
        levelDescription.put("description", "The level of light from 0-100");
        levelDescription.put("minimum", 0);
        levelDescription.put("maximum", 100);
        thing.addProperty(new Property(thing, "level", levelDescription, 50));

        Map<String, Object> fadeMetadata = new HashMap<>();
        Map<String, Object> fadeInput = new HashMap<>();
        Map<String, Object> fadeProperties = new HashMap<>();
        Map<String, Object> fadeLevel = new HashMap<>();
        Map<String, Object> fadeDuration = new HashMap<>();
        fadeMetadata.put("description", "Fade the lamp to a given level");
        fadeInput.put("type", "object");
        fadeLevel.put("type", "number");
        fadeLevel.put("minimum", 0);
        fadeLevel.put("maximum", 100);
        fadeDuration.put("type", "number");
        fadeDuration.put("unit", "milliseconds");
        fadeProperties.put("level", fadeLevel);
        fadeProperties.put("duration", fadeDuration);
        fadeInput.put("properties", fadeProperties);
        fadeMetadata.put("input", fadeInput);
        thing.addAvailableAction("fade", fadeMetadata, FadeAction.class);

        Map<String, Object> overheatedMetadata = new HashMap<>();
        overheatedMetadata.put("description",
                               "The lamp has exceeded its safe operating temperature");
        overheatedMetadata.put("type", "number");
        overheatedMetadata.put("unit", "celcius");
        thing.addAvailableEvent("overheated", overheatedMetadata);

        return thing;
    }*/

    public static DimmableColorLight smartThing(){
        LedStrip light = new LedStrip();

        return new DimmableColorLight(
            new SettableValue<>((c)-> light.setColor(c),() -> light.color),
            new SettableValue<>((b)-> light.setBrightness(b),() -> light.brightness),
            new SettableValue<>((o)-> light.setOn(o),() -> light.on),
            "LogLight"
            );
    }

    public static void main(String[] args) {
        //Thing thing = makeThing();
        WebThingServer server;

        try {
            List<Thing> things = new ArrayList<>();
            things.add(smartThing().thing());

            // If adding more than one thing here, be sure to set the second
            // parameter to some string, which will be broadcast via mDNS.
            // In the single thing case, the thing's name will be broadcast.
            server = new WebThingServer(things, null, 8888);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    server.stop();
                }
            });

            server.start(false);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public static class OverheatedEvent extends Event {
        public OverheatedEvent(Thing thing, int data) {
            super(thing, "overheated", data);
        }
    }

    public static class FadeAction extends Action {
        public FadeAction(Thing thing, JSONObject input) {
            super(UUID.randomUUID().toString(), thing, "fade", input);
        }

        @Override
        public void performAction() {
            Thing thing = this.getThing();
            JSONObject input = this.getInput();
            try {
                Thread.sleep(input.getInt("duration"));
            } catch (InterruptedException e) {
            }

            thing.setProperty("level", input.getInt("level"));
            thing.addEvent(new OverheatedEvent(thing, 102));
        }
    }

    private static class LedStrip{
        public void setColor(final String color) {
            this.color = color;
            System.out.println("Color is now: "+color);
        }

        public void setBrightness(final double brightness) {
            this.brightness = brightness;
            System.out.println("Brightness is now: "+brightness);

        }

        public void setOn(final boolean on) {
            this.on = on;
            System.out.println("On state is now: "+on);

        }

        String color;
        double brightness;
        boolean on;
    }
}
