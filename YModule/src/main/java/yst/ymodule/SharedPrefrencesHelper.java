package yst.ymodule;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrencesHelper {

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("appPrefs", Context.MODE_PRIVATE);

    }

    public static String getStringPref(Context context, String Key) {

        return getPrefs(context).getString(Key, "");
    }

    public static String getStringPref(Context context, String Key, String DefaultValue) {

        return getPrefs(context).getString(Key, DefaultValue);
    }

    public static int getIntPref(Context context, String Key) {

        return getPrefs(context).getInt(Key, 0);
    }

    public static int getIntPref(Context context, String Key, int DefaultValue) {

        return getPrefs(context).getInt(Key, DefaultValue);
    }

    public static boolean getBooleanPref(Context context, String Key) {

        return getPrefs(context).getBoolean(Key, false);
    }

    public static boolean getBooleanPref(Context context, String Key, boolean DefaultValue) {

        return getPrefs(context).getBoolean(Key, DefaultValue);
    }

    public static void setStringPref(Context context, String Key, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(Key, value).commit();
    }

    public static void setIntPref(Context context, String Key, int value) {
        // perform validation etc..
        getPrefs(context).edit().putInt(Key, value).commit();
    }

    public static void setBooleanPref(Context context, String Key, boolean value) {
        // perform validation etc..
        getPrefs(context).edit().putBoolean(Key, value).commit();
    }

    public static void ClearAll(Context context) {
        getPrefs(context).edit().clear().commit();
    }
}
