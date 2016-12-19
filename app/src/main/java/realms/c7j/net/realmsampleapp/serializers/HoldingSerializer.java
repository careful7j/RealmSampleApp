package realms.c7j.net.realmsampleapp.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import realms.c7j.net.realmsampleapp.model.Holding;

/**
 * THIS SERIALIZERS ARE FOR GSON LIBRARY TO MAKE JSONS FROM OBJECTS
 * Created by Ivan.Zh on Q1 2016.
 */
public class HoldingSerializer implements JsonSerializer<Holding> {

    @Override
    public JsonElement serialize(Holding src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("name", src.getName());
        return jsonObject;
    }
}
