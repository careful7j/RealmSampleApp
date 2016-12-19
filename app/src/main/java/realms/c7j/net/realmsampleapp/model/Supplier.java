package realms.c7j.net.realmsampleapp.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ivan.Zh on Q1 2016.
 */
public class Supplier extends RealmObject {

    @PrimaryKey
    private String id;

    private String name;
    private String country;
    private RealmList<Holding> holding;


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

    public RealmList<Holding> getHolding() {
        return holding;
    }

    public void setHolding(RealmList<Holding> holding) {
        this.holding = holding;
    }
}
