package realms.c7j.net.realmsampleapp;

import android.util.Log;

/**
 * Created by Ivan.Zh on Q1 2016.
 */
public class L {

    public static boolean enabled = true;

    public static void t(Object message) {
        if (!enabled) return;
        Log.e("#TEST", "" + message);
    }

    public static void n(Object message) {
        if (!enabled) return;
        Log.e("#NET", "" + message);
    }

    public static void d(Object message) {
        if (!enabled) return;
        Log.e("#DB", "" + message);
    }

    public static void t(String logHeader, Object message) {
        if (!enabled) return;
        Log.e("#" + logHeader, "" + message);
    }

}
