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
}
