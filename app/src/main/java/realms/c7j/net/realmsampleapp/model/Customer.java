package realms.c7j.net.realmsampleapp.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Ivan.Zh on Q1 2016.
 */
public class Customer extends RealmObject {

    @PrimaryKey
    private String id;

    @Required
    private String name;
    private String country;
    private RealmList<CustomerToOrder> customerToOrder;

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

    public RealmList<CustomerToOrder> getCustomerToOrder() {
        return customerToOrder;
    }

    public void setCustomerToOrder(RealmList<CustomerToOrder> customerToOrder) {
        this.customerToOrder = customerToOrder;
    }

}
