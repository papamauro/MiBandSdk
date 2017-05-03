package pvsys.mauro.sdk;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class AppClass extends Application {

    public static final String BONDED_DEVICE_ADDRESS = "BONDED_DEVICE_ADDRESS";
    public static final String BONDED_DEVICE_TYPE = "BONDED_DEVICE_TYPE";

    private final static Logger LOG = new Logger(AppClass.class.getSimpleName());

    private static Context context;
    private static SharedPreferences pref;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        pref = PreferenceManager.getDefaultSharedPreferences(this.context);
    }

    public static Context getContext() {
        return context;
    }


    public static void setPreferredDevice(String address, String type) {
        pref.edit().putString(BONDED_DEVICE_ADDRESS, address).apply();
        pref.edit().putString(BONDED_DEVICE_TYPE, type).apply();
    }

    public static String getPreferredDeviceAddress() {
        return pref.getString(BONDED_DEVICE_ADDRESS, null);
    }

    public static String getPreferredDeviceType() {
        return pref.getString(BONDED_DEVICE_TYPE, null);
    }

}
