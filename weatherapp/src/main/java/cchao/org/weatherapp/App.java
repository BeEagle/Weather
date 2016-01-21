package cchao.org.weatherapp;

import android.app.Application;

import cchao.org.weatherapp.utils.SPUtil;

/**
 * Created by chenchao on 15/11/13.
 */
public class App extends Application{

    private static App instance;
    private SPUtil weatherMsg;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        weatherMsg = new SPUtil(this);
    }

    public static App getInstance(){
        return instance;
    }

    public SPUtil getWeatherMsg() {
        return weatherMsg;
    }
}
