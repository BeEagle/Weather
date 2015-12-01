package cchao.org.weatherapp.utils;

import android.graphics.drawable.Drawable;

import cchao.org.weatherapp.WeatherApplication;

/**
 * Created by chenchao on 15/12/1.
 */
public class WeatherIconUtil {

    /**
     * 获取天气图标
     * @param imageName 天气名称(数字)
     * @return 天气图标drawable对象
     */
    public static Drawable getWeatherIcon(String imageName) {
        int id = WeatherApplication.getInstance().getResources().getIdentifier("w" + imageName, "drawable", "cchao.org.weatherapp");
        if(id != 0){
            return WeatherApplication.getInstance().getResources().getDrawable(id);
        }
        return WeatherApplication.getInstance().getResources().getDrawable(WeatherApplication.getInstance().getResources().getIdentifier("w999", "drawable", "cchao.org.weatherapp"));
    }

}
