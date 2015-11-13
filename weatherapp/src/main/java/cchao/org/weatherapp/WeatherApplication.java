package cchao.org.weatherapp;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by chenchao on 15/11/13.
 */
public class WeatherApplication extends Application{
    private static WeatherApplication instance;

    public RequestQueue requestQueue;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        requestQueue = Volley.newRequestQueue(this);
    }

    public static WeatherApplication getInstance(){
        return instance;
    }
}
