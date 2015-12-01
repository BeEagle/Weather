package cchao.org.weatherapp.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cchao.org.weatherapp.Constant;
import cchao.org.weatherapp.WeatherApplication;
import cchao.org.weatherapp.event.UpdateEvent;
import cchao.org.weatherapp.utils.BusUtil;
import cchao.org.weatherapp.utils.SharedPreferencesUtil;

/**
 * Created by chenchao on 15/11/27.
 */
public class SaveDataController {

    /**
     * 保存天气信息到本地
     * @param data
     */
    public static void saveResponse(String data) {
        SharedPreferencesUtil weatherMsg = WeatherApplication.getInstance().getWeatherMsg();
        try {
            JSONObject weatherObj = new JSONObject(data);
            JSONArray weatherArray = weatherObj.getJSONArray("HeWeather data service 3.0");
            JSONObject jsonObject = weatherArray.getJSONObject(0);
            if (jsonObject.getString("status").equals("ok")) {
                //空气质量指数
                //JSONObject aqi_obj = jsonObject.getJSONObject("aqi");
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

                //weatherMsg.save(Constant.AQI, aqi_obj.getJSONObject("city").getString("aqi"));
                //weatherMsg.save(Constant.PM25, aqi_obj.getJSONObject("city").getString("pm25"));
                //weatherMsg.save(Constant.QLTY, aqi_obj.getJSONObject("city").getString("qlty"));

                weatherMsg.save(Constant.NOW_TMP, now_obj.getString("tmp"));
                weatherMsg.save(Constant.NOW_CODE, now_obj.getJSONObject("cond").getString("code"));
                weatherMsg.save(Constant.NOW_COND, now_obj.getJSONObject("cond").getString("txt"));
                weatherMsg.save(Constant.NOW_WIND_DIR, now_obj.getJSONObject("wind").getString("dir"));
                weatherMsg.save(Constant.NOW_WIND_SC, now_obj.getJSONObject("wind").getString("sc"));

                for (int i = 1; i < 8; i++) {
                    JSONObject dailyObj = daily_forecast_array.getJSONObject(i - 1);
                    String temp = String.valueOf(i);
                    weatherMsg.save(Constant.DAILY_TIME + temp, dailyObj.getString("date"));
                    weatherMsg.save(Constant.DAILY_TMP_MAX + temp, dailyObj.getJSONObject("tmp").getString("max"));
                    weatherMsg.save(Constant.DAILY_TMP_MIN + temp, dailyObj.getJSONObject("tmp").getString("min"));
                    weatherMsg.save(Constant.DAILY_COND_d + temp, dailyObj.getJSONObject("cond").getString("txt_d"));
                    weatherMsg.save(Constant.DAILY_CODE_d + temp, dailyObj.getJSONObject("cond").getString("code_d"));
                    weatherMsg.save(Constant.DAILY_COND_n + temp, dailyObj.getJSONObject("cond").getString("txt_n"));
                    weatherMsg.save(Constant.DAILY_CODE_n + temp, dailyObj.getJSONObject("cond").getString("code_n"));
                    weatherMsg.save(Constant.DAILY_WIND_DIR + temp, dailyObj.getJSONObject("wind").getString("dir"));
                    weatherMsg.save(Constant.DAILY_WIND_SC + String.valueOf(i + 1), dailyObj.getJSONObject("wind").getString("sc"));
                }

                weatherMsg.save(Constant.SUGGESTION_DRSG, suggestion_obj.getJSONObject("drsg").getString("txt"));
                BusUtil.getBus().post(new UpdateEvent(Constant.UPDATE_SUCCESS));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
