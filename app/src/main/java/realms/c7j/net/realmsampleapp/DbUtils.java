package realms.c7j.net.realmsampleapp;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.UUID;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.RealmSchema;
import realms.c7j.net.realmsampleapp.model.CustomerToOrder;
import realms.c7j.net.realmsampleapp.model.Customer;
import realms.c7j.net.realmsampleapp.model.Holding;
import realms.c7j.net.realmsampleapp.model.Order;
import realms.c7j.net.realmsampleapp.model.OrderItem;
import realms.c7j.net.realmsampleapp.model.Product;
import realms.c7j.net.realmsampleapp.model.ShareHolder;
import realms.c7j.net.realmsampleapp.model.Supplier;

/**
 * Created by Ivan.Zh on Q1 2016.
 */
public class DbUtils {

    public static volatile long transactionId = 0;

    static RealmMigration migration = new RealmMigration() {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            // DynamicRealm exposes an editable schema
            RealmSchema schema = realm.getSchema();

            if (oldVersion == 0) {
                schema.create("ShareHolder")
                        .addField("name", String.class)
                        .addRealmObjectField("holding", schema.get("Holding"));
                oldVersion++;
            }

            if (oldVersion == 1) {
                schema.get("ShareHolder")
                        .addField("id", String.class, FieldAttribute.PRIMARY_KEY)
                        .addField("citizenship", String.class);
//                        .addRealmListField("dogs", schema.get("Dog"));  //just an example (unused)
                oldVersion++;
            }
        }
    };

    public static void enableRealmBrowserForWindows7(Context context) {
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(context).build())
                        .build());

//        if required some custom database access:
//        RealmInspectorModulesProvider.builder(this)
//                .withFolder(getCacheDir())
//                .withEncryptionKey("encrypted.realm", key)
//                .withMetaTables()
//                .withDescendingOrder()
//                .withLimit(1000)
//                .databaseNamePattern(Pattern.compile(".+\\.realm"))
//                .build()
    }


    public static String nextId() {     //Unfortunately realm v.0.87.4 has no PrimaryKey autoIncrement
        if (transactionId == 0) {
            transactionId = System.currentTimeMillis();
        }
        transactionId++;
        return UUID.randomUUID().toString() + "-" + transactionId;
    }


    public static void dropDatabase(Realm realm) {
        realm.beginTransaction();
        realm.delete(Customer.class);
        realm.delete(Order.class);
        realm.delete(OrderItem.class);
        realm.delete(Product.class);
        realm.delete(Supplier.class);
        realm.delete(Holding.class);
        realm.delete(CustomerToOrder.class);
        realm.commitTransaction();
    }


    public static void rebuildDatabase(final Realm realm, final Context context) {

        dropDatabase(realm);

        realm.executeTransaction(new Realm.Transaction() {
             @Override
             public void execute(Realm realm) {

                 Customer customer1 = realm.createObject(Customer.class, DbUtils.nextId());
                 customer1.setName("Woo");
                 customer1.setCountry("China");

                 Customer customer2 = realm.createObject(Customer.class, DbUtils.nextId());
                 customer2.setName("John");
                 customer2.setCountry("USA");

                 Customer customer3 = realm.createObject(Customer.class, DbUtils.nextId());
                 customer3.setName("Kati");
                 customer3.setCountry("Finland");

                 Supplier supplier1 = realm.createObject(Supplier.class, DbUtils.nextId());
                 supplier1.setName("Karl Zeiss");
                 supplier1.setCountry("Germany");

                 Supplier supplier2 = realm.createObject(Supplier.class, DbUtils.nextId());
                 supplier2.setName("Mithuna Foods Company");
                 supplier2.setCountry("India");

                 Supplier supplier3 = realm.createObject(Supplier.class, DbUtils.nextId());
                 supplier3.setName("Sri Ramakrishna Agencies");
                 supplier3.setCountry("India");

                 Holding holding1 = realm.createObject(Holding.class, DbUtils.nextId());
                 holding1.setName("HCBC Holdings");

                 Holding holding2 = realm.createObject(Holding.class, DbUtils.nextId());
                 holding2.setName("Rise Holdings");

                 RealmList<Holding> holdingsList1 = new RealmList<Holding>();
                 holdingsList1.add(holding1);
                 holdingsList1.add(holding2);

                 RealmList<Holding> holdingsList2 = new RealmList<Holding>();
                 holdingsList2.add(holding2);

                 supplier1.setHolding(holdingsList1);
                 supplier2.setHolding(holdingsList2);
             }
         }
//                , new Realm.Transaction.Callback() {    // <- Uncomment to make it Async and run not on Main thread
//            @Override
//            public void onSuccess() {
//                L.t("Insert OK");
//            }
//
//            @Override
//            public void onError(Exception e) {
//                L.t("Failed to insert");
//            }
//        }
        );

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Supplier supplier1 = realm
                        .where(Supplier.class)
                        .equalTo("name", "Karl Zeiss")
                        .findFirst();

                Supplier supplier2 = realm
                        .where(Supplier.class)
                        .equalTo("name", "Mithuna Foods Company")
                        .findFirst();

                RealmResults<Supplier> list = realm
                        .where(Supplier.class)
                        .equalTo("name", "Sri Ramakrishna Agencies")
                        .findAll();
                Supplier supplier3 = list.first();


                Product product1 = realm.createObject(Product.class, DbUtils.nextId());
                product1.setName("Nokia N82 Glass");
                product1.setSupplier(supplier1);

                Product product2 = realm.createObject(Product.class, DbUtils.nextId());
                product2.setName("Sony Xperia Z5 Glass");
                product2.setSupplier(supplier1);

                Product product3 = realm.createObject(Product.class, DbUtils.nextId());
                product3.setName("Sweet Potato");
                product3.setSupplier(supplier2);

                Product product4 = realm.createObject(Product.class, DbUtils.nextId());
                product4.setName("Sweet Potato");
                product4.setSupplier(supplier3);

                Customer customer1 = realm
                        .where(Customer.class)
                        .equalTo("name", "Woo")
                        .findFirst();

                Customer customer2 = realm
                        .where(Customer.class)
                        .equalTo("name", "John")
                        .findFirst();

                Customer customer3 = realm
                        .where(Customer.class)
                        .equalTo("name", "Kati")
                        .findFirst();

                OrderItem orderItem1 = realm.createObject(OrderItem.class, DbUtils.nextId());
                orderItem1.setProduct(product3);
                orderItem1.setAmount(1);

                OrderItem orderItem2 = realm.createObject(OrderItem.class, DbUtils.nextId());
                orderItem2.setProduct(product4);
                orderItem2.setAmount(1);

                Order order1 = realm.createObject(Order.class, DbUtils.nextId());
                order1.setCustomer(customer1);
                RealmList<OrderItem> orderItemList = new RealmList<>();
                orderItemList.add(orderItem1);
                orderItemList.add(orderItem2);
                order1.setOrderItem(orderItemList);


                OrderItem orderItem3 = realm.createObject(OrderItem.class, DbUtils.nextId());
                orderItem3.setProduct(product1);
                orderItem3.setAmount(2);

                OrderItem orderItem4 = realm.createObject(OrderItem.class, DbUtils.nextId());
                orderItem4.setProduct(product2);
                orderItem4.setAmount(3);

                Order order2 = realm.createObject(Order.class, DbUtils.nextId());
                order2.setCustomer(customer2);
                RealmList<OrderItem> orderItemList2 = new RealmList<>();
                orderItemList2.add(orderItem3);
                orderItemList2.add(orderItem4);
                order2.setOrderItem(orderItemList2);


                Order order3 = realm.createObject(Order.class, DbUtils.nextId());
                order3.setCustomer(customer3);
                RealmList<OrderItem> orderItemList3 = new RealmList<>();
                orderItemList3.add(orderItem1);
                orderItemList3.add(orderItem2);
                order3.setOrderItem(orderItemList3);

                Order order4 = realm.createObject(Order.class, DbUtils.nextId());
                order4.setCustomer(customer1);


                //Customer1 has order1
                CustomerToOrder c1o1 = realm.createObject(CustomerToOrder.class, DbUtils.nextId());
                c1o1.setCustomer(customer1);
                c1o1.setOrder(order1);

                //Customer1 has order2
                CustomerToOrder c1o2 = realm.createObject(CustomerToOrder.class, DbUtils.nextId());
                c1o2.setCustomer(customer1);
                c1o2.setOrder(order2);

                //Customer2 has order1
                CustomerToOrder c2o1 = realm.createObject(CustomerToOrder.class, DbUtils.nextId());
                c2o1.setCustomer(customer2);
                c2o1.setOrder(order1);

                //Customer3 has order3
                CustomerToOrder c3o3 = realm.createObject(CustomerToOrder.class, DbUtils.nextId());
                c3o3.setCustomer(customer3);
                c3o3.setOrder(order3);

                //List for Customer1
                RealmList<CustomerToOrder> listC1 = new RealmList<>();
                listC1.add(c1o1);
                listC1.add(c1o2);

                //List for Customer2
                RealmList<CustomerToOrder> listC2 = new RealmList<>();
                listC2.add(c2o1);

                //List for Customer3
                RealmList<CustomerToOrder> listC3 = new RealmList<>();
                listC3.add(c3o3);

                //List for Order1
                RealmList<CustomerToOrder> listO1 = new RealmList<>();
                listO1.add(c1o1);
                listO1.add(c2o1);

                //List for Order2
                RealmList<CustomerToOrder> listO2 = new RealmList<>();
                listO2.add(c1o2);

                //List for Order2
                RealmList<CustomerToOrder> listO3 = new RealmList<>();
                listO3.add(c3o3);

                customer1.setCustomerToOrder(listC1);
                customer2.setCustomerToOrder(listC2);
                customer3.setCustomerToOrder(listC3);
                order1.setCustomerToOrder(listO1);
                order2.setCustomerToOrder(listO2);
                order3.setCustomerToOrder(listO3);

                ShareHolder shareHolder = realm.createObject(ShareHolder.class, DbUtils.nextId());
                shareHolder.setName("Major Investments");
                shareHolder.setCitizenship("PRC");
                shareHolder.setHolding(realm.where(Holding.class).findFirst());
            }
        });

        //QUERY HERE:
        RealmResults<Customer> customers = realm.where(Customer.class)
                .equalTo("customerToOrder.order.orderItem.product.supplier.country", "India")
                .findAll();

        //OUTPUT:
        L.t("Customers: ");
        for ( Customer customer : customers ) {
            L.t(customer.toString());
            RealmList<CustomerToOrder> customerToOrders = customer.getCustomerToOrder();
            for ( CustomerToOrder customerToOrder : customerToOrders ) {
                Order order = customerToOrder.getOrder();
                L.t(order.toString());
                RealmList<OrderItem> mOrderItems = order.getOrderItem();
                L.t("OrderItemsList: ");
                int counter = 0;
                for ( OrderItem mOrderItem : mOrderItems ) {
                    L.t("OrderItem "+ counter +": " + order.toString());
                    counter++;

                    Product mProduct = mOrderItem.getProduct();
                    L.t(mProduct.toString());

                    Supplier mSupplier = mProduct.getSupplier();
                    L.t(mSupplier.toString());

                    L.t("HoldingsList: ");
                    RealmList<Holding> mHoldings = mSupplier.getHolding();
                    if (mHoldings.size() == 0) {
                        L.t("<No info about Holdings>");
                    }
                    for ( Holding mHolding : mHoldings ) {
                        L.t("Holding "+ mHolding.toString());
                    }
                    L.t(" ");
                }
            }
        }


//        RealmResults<Order> orders = realm.where(Order.class)
//                .equalTo("orderItem.product.supplier.country", "India")
//                .findAll();
//
//        L.t("OrdersList: ");
//        for ( Order mOrder : orders ) {
//            L.t("Order: " + mOrder.toString());
//            RealmList<OrderItem> mOrderItems = mOrder.getOrderItem();
//            L.t("OrderItemsList: ");
//            int counter = 0;
//            for ( OrderItem mOrderItem : mOrderItems ) {
//                L.t("OrderItem "+ counter +": " + mOrder.toString());
//                counter++;
//
//                Product mProduct = mOrderItem.getProduct();
//                L.t(mProduct.toString());
//
//                Supplier mSupplier = mProduct.getSupplier();
//                L.t(mSupplier.toString());
//
//                L.t("HoldingsList: ");
//                RealmList<Holding> mHoldings = mSupplier.getHolding();
//                for ( Holding mHolding : mHoldings ) {
//                    L.t("Holding "+ mHoldings.toString());
//                }
//            }
//        }
//
//
//        L.t("Size =" + orders.size());
    }
}
