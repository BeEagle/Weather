package cchao.org.weatherapp.utils;

import cchao.org.weatherapp.App;

/**
 * Created by chenchao on 16/2/24.
 */
public class Log {

    private static final boolean DEBUG = false;
    private static final String TAG = App.getInstance().getPackageName();

    public static void i(String content) {
        if (DEBUG) {
            android.util.Log.i(TAG, content);
        }
    }

    public static void e(String content) {
        if (DEBUG) {
            android.util.Log.d(TAG, content);
        }
    }

    public static void d(String conteng) {
        if (DEBUG) {
            android.util.Log.d(TAG, conteng);
        }
    }

}
