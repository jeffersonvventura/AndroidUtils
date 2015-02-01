package livroandroid.lib.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * Created by Ricardo Lecheta on 01/02/2015.
 */
public class PrefsSDCard {
    public static final String PREF_ID = "prefs";
    private static final String TAG = "PrefsSDCard";
    public static void setBoolean(Context context, String flag, boolean on) {
        setString(context, flag, on ? "1" : "0");
    }
    public static boolean getBoolean(Context context, String flag) {
        String s = getString(context, flag);
        boolean on = "1".equals(s);
        return on;
    }
    public static void setInteger(Context context, String flag, int valor) {
        setString(context,flag,String.valueOf(valor));
    }
    public static int getInteger(Context context, String flag) {
        String s = getString(context,flag);
        if(s != null) {
            return Integer.parseInt(s);
        }
        return 0;
    }
    public static void setString(Context context, String flag, String valor) {
        File f = SDCardUtils.getPublicFile("prefs", flag + ".txt");
        IOUtils.writeString(f, valor);
    }
    public static String getString(Context context, String flag)  {
        File f = SDCardUtils.getPublicFile("prefs",flag+".txt");
        Log.d("livroandroid", "getString: " + f);
        return IOUtils.readString(f);
    }
}
