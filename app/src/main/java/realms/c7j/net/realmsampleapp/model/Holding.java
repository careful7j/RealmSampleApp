package realms.c7j.net.realmsampleapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Ivan.Zh on Q1 2016.
 */
public class Holding extends RealmObject {

    @PrimaryKey
    private String id;

    @Required
    private String name;


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
}
