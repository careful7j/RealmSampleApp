package realms.c7j.net.realmsampleapp;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Ivan.Zh on Q1 2016.
 */
public class RealmApp extends Application {

    public static RealmConfiguration realmConfig;
    /** Our database is encrypted with this key. Try to change just one digit and it will not open*/
    private String key = "e662a9fc93c445549ca42a8d199dde35e662a9fc93c445549ca42a8d199dde35";

    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();

        Realm.init(context);
        realmConfig = new RealmConfiguration.Builder()
                .name("myrealm2.realm")
                .schemaVersion(2)                   //don't forget to bump it up on change
//                .encryptionKey(key.getBytes())      //enable database encryption
                .migration(DbUtils.migration)
                .build();

        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getInstance(realmConfig);
        DbUtils.transactionId = System.currentTimeMillis();

        //Type in your Google Chrome browser: chrome://inspect/#devices and then Tap "Inspect" There > WebSQL > defaul.realm
        DbUtils.enableRealmBrowserForWindows7(getApplicationContext());
        DbUtils.rebuildDatabase(realm, getApplicationContext());
        GsonTest.testRealmGson(context, realm);
    }
}
