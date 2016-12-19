package realms.c7j.net.realmsampleapp.gsontest;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Ivan.Zh on Q1 2016.
 */
public class CustomerGson {

    private String id;
    private String name;
    private String country;
    private ArrayList<SomeItem> headers = new ArrayList<SomeItem>();


    public CustomerGson() {
        headers.add(new SomeItem("header1", 1));
        headers.add(new SomeItem("header2", 2));
        headers.add(new SomeItem("header3", 3));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<SomeItem> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<SomeItem> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        String out = "id: " + id + ", name: " + name + ", country: " + country + ", headers: ";
        for (SomeItem header : headers) {
            out += "{ ";
            out += header.toString();
            out += " }, ";
        }
//        out += headers.toString();
        return out;
    }
}
