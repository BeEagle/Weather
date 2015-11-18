package cchao.org.weatherapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cchao.org.weatherapp.WeatherApplication;

/**
 * 保存天气数据
 * Created by chenchao on 15/11/18.
 */
public class SaveData {

    public static void saveResponse(String data) {
        Cache cache = new Cache(WeatherApplication.getInstance());
        try {
            JSONObject weatherObj = new JSONObject(data);
            JSONArray weatherArray = weatherObj.getJSONArray("HeWeather data service 3.0");
            JSONObject jsonObject = weatherArray.getJSONObject(0);
            //空气质量指数
            JSONObject aqi_obj = jsonObject.getJSONObject("aqi");
            //城市基本信息
            JSONObject basic_obj = jsonObject.getJSONObject("basic");
            //实况天气
            JSONObject now_obj = jsonObject.getJSONObject("now");
            //生活指数
            JSONObject suggestion_obj = jsonObject.getJSONObject("suggestion");
            //天气预报
            JSONArray daily_forecast_array = jsonObject.getJSONArray("daily_forecast");
            //每小时天气预报
            JSONArray hourly_forecast_array = jsonObject.getJSONArray("hourly_forecast");

            cache.save(Constant.AQI, aqi_obj.getJSONObject("city").getString("aqi"));
            cache.save(Constant.PM25, aqi_obj.getJSONObject("city").getString("pm25"));
            cache.save(Constant.QLTY, aqi_obj.getJSONObject("city").getString("qlty"));

            cache.save(Constant.NOW_TMP, now_obj.getString("tmp"));
            cache.save(Constant.NOW_CODE, now_obj.getJSONObject("cond").getString("code"));
            cache.save(Constant.NOW_COND, now_obj.getJSONObject("cond").getString("txt"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
