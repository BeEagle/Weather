package cchao.org.weather.utils;

/**
 * Created by chenchao on 15/11/13.
 */
public class BaseUri {

    private final static String WEATHER_URI = "https://api.heweather.com/x3/weather";

    public static String getWeatherUri(){
        return WEATHER_URI;
    }

}
