package cchao.org.weatherapp;

import android.app.Application;

/**
 * Created by chenchao on 15/11/13.
 */
public class WeatherApplication extends Application{

    private static WeatherApplication instance;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }

    public static WeatherApplication getInstance(){
        return instance;
    }
}
