package cchao.org.weatherapp;

import android.app.Application;

import com.squareup.okhttp.OkHttpClient;

import cchao.org.weatherapp.utils.SharedPreferencesUtil;

/**
 * Created by chenchao on 15/11/13.
 */
public class WeatherApplication extends Application{

    private static WeatherApplication instance;
    private SharedPreferencesUtil weatherMsg;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        weatherMsg = new SharedPreferencesUtil(this);
    }

    public static WeatherApplication getInstance(){
        return instance;
    }

    public SharedPreferencesUtil getWeatherMsg() {
        return weatherMsg;
    }
}
