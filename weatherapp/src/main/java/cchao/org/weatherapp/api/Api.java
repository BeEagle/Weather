package cchao.org.weatherapp.api;

/**
 * Created by chenchao on 15/11/27.
 */
public class Api {

    private final static String WEATHER_URI = "https://api.heweather.com/";

    public static String getWeatherUri(){
        return WEATHER_URI;
    }
}
