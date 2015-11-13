package cchao.org.weather;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by chenchao on 15/11/13.
 */
public class MainApplication extends Application {

    private static MainApplication instance;

    public RequestQueue requestQueue;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        requestQueue = Volley.newRequestQueue(this);
    }

    public static MainApplication getInstance(){
        return instance;
    }
}

