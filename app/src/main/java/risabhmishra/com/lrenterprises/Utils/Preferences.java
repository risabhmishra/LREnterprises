package risabhmishra.com.lrenterprises.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    final static String PREFS_NAME = "LREnterprises";

    public static void setUserGst(Context context, String data) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("UserGst", data);
        editor.commit();
    }

    public static String getUserGst(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String isVisited = pref.getString("UserGst", "");
        return isVisited;
    }

    public static void setUserName(Context context, String data) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("UserName", data);
        editor.commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String isVisited = pref.getString("UserName", "");
        return isVisited;
    }

    public static void setUserAddress(Context context, String data) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("UserAddr", data);
        editor.commit();
    }

    public static String getUserAddress(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String isVisited = pref.getString("UserAddr", "");
        return isVisited;
    }



}
