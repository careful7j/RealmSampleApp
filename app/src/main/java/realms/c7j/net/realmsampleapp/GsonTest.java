package realms.c7j.net.realmsampleapp;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import realms.c7j.net.realmsampleapp.gsontest.CustomerGson;
import realms.c7j.net.realmsampleapp.model.Customer;
import realms.c7j.net.realmsampleapp.model.Supplier;
import realms.c7j.net.realmsampleapp.serializers.CustomerSerializer;
import realms.c7j.net.realmsampleapp.serializers.HoldingSerializer;
import realms.c7j.net.realmsampleapp.serializers.SupplierSerializer;

/**
 * Created by Ivan.Zh on Q1 2016.
 */
public class GsonTest {

    public static void testPlaneJson() {
        String json = " {\"country\":\"China\",\"headers\":[{\"name\":\"header1\",\"number\":1},{\"name\":\"header2\",\"number\":2},{\"name\":\"header3\",\"number\":3}],\"id\":\"ecfb84b1-b2fb-4775-ab23-382c359b0379-1454922006872\",\"name\":\"Woo\"}";
        Gson gson = new Gson();
        CustomerGson customer = gson.fromJson(json, CustomerGson.class);
        L.t("From Json to Object: " + customer.toString());
        L.t("From Object to Json: " + gson.toJson(customer));
    }



    public static void testRealmGson(Context context, Realm realm) {

        testPlaneJson();

        try {
            Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(Class.forName("io.realm.CustomerRealmProxy"), new CustomerSerializer())
                .registerTypeAdapter(Class.forName("io.realm.SupplierRealmProxy"), new SupplierSerializer())
                .registerTypeAdapter(Class.forName("io.realm.HoldingRealmProxy"), new HoldingSerializer())
                .create();

            String json1 = gson.toJson(realm.where(Customer.class).findFirst());
            L.t(json1);
            String json2 = gson.toJson(realm.where(Supplier.class).findFirst());
            L.t(json2);
            String json3 = "{\"id\":\"21538681-38f3-42d1-9e08-18149bb8d8e7-1454922006875\",\"name\":\"Karl Zeiss\",\"country\":\"Germany\",\"holding\":[{\"id\":\"19186fd2-7e1a-48b4-8d98-d9655a633405-1454922006878\",\"name\":\"HCBC Holdings\"},{\"id\":\"6b617490-067f-4879-8a6d-12760895b7ce-1454922006879\",\"name\":\"Rise Holdings\"}]}";
            L.t(json3);
            Supplier s = gson.fromJson(json3, Supplier.class);
            L.t(SupplierSerializer.toString(s));

        } catch ( ClassNotFoundException e ) {
            L.t("Error: bad class path");
        }

    }
}
