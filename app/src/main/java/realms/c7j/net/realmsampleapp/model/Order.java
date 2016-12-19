package realms.c7j.net.realmsampleapp.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ivan.Zh on Q1 2016.
 */
public class Order extends RealmObject {

    @PrimaryKey
    private String id;

    private Customer customer;
    private RealmList<OrderItem> orderItem;
    private RealmList<CustomerToOrder> customerToOrder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public RealmList<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(RealmList<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public RealmList<CustomerToOrder> getCustomerToOrder() {
        return customerToOrder;
    }

    public void setCustomerToOrder(RealmList<CustomerToOrder> customerToOrder) {
        this.customerToOrder = customerToOrder;
    }
}
