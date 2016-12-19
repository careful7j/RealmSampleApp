package realms.c7j.net.realmsampleapp.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import realms.c7j.net.realmsampleapp.model.Holding;
import realms.c7j.net.realmsampleapp.model.Supplier;

/**
 * THIS SERIALIZERS ARE FOR GSON LIBRARY TO MAKE JSONS FROM OBJECTS
 * Created by Ivan.Zh on Q1 2016.
 */
public class SupplierSerializer implements JsonSerializer<Supplier> {

    @Override
    public JsonElement serialize(Supplier src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("country", src.getCountry());
        jsonObject.add("holding", context.serialize(src.getHolding().toArray(), Holding[].class));
        return jsonObject;
    }

    /** For debug purpose only (and since we are not allowed to override Supplier's toString method */
    public static String toString(Supplier supplier) {
        String out = "";
        out += "id: ";
        out += supplier.getId();
        out += ", name: ";
        out += supplier.getName();
        out += ", country: ";
        out += supplier.getCountry();
        out += ", holding {";
        for (Holding holding : supplier.getHolding()) {
            out += " id: ";
            out += holding.getId();
            out += ", name: ";
            out += holding.getName();
            out += "; ";
        }
        out += "}";
        return out;
    }
}
