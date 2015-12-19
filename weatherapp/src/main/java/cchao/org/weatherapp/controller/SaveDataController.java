package cchao.org.weatherapp.controller;

import java.util.ArrayList;
import java.util.List;

import cchao.org.weatherapp.Constant;
import cchao.org.weatherapp.WeatherApplication;
import cchao.org.weatherapp.bean.ApiResultVO;
import cchao.org.weatherapp.bean.ApiResultVO.HeWeather;
import cchao.org.weatherapp.bean.ApiResultVO.HeWeather.Aqi;
import cchao.org.weatherapp.bean.ApiResultVO.HeWeather.Basic;
import cchao.org.weatherapp.bean.ApiResultVO.HeWeather.DailyForecast;
import cchao.org.weatherapp.bean.ApiResultVO.HeWeather.HourlyForecast;
import cchao.org.weatherapp.bean.ApiResultVO.HeWeather.Now;
import cchao.org.weatherapp.bean.ApiResultVO.HeWeather.Suggestion;
import cchao.org.weatherapp.event.UpdateEvent;
import cchao.org.weatherapp.utils.BusUtil;
import cchao.org.weatherapp.utils.SharedPreferencesUtil;

/**
 * Created by chenchao on 15/11/27.
 */
public class SaveDataController {

    private static SaveDataController saveDataController;
    private HeWeather weatherMsg;
    private Aqi aqi;
    private Basic basic;
    private List<DailyForecast> dailyForecastList = new ArrayList<DailyForecast>();
    private DailyForecast dailyForecast;
    private List<HourlyForecast> hourlyForecastList = new ArrayList<HourlyForecast>();
    private Now now;
    private Suggestion suggestion;
    private String status;

    public static SaveDataController getSaveDataController() {
        if (saveDataController == null) {
            saveDataController = new SaveDataController();
        }
        return saveDataController;
    }

    /**
     * 保存天气信息到本地
     * @param data
     */
    public void saveResponse(ApiResultVO data) {
        resolveJson(data);
        if (!status.equals("ok")) {
            BusUtil.getBus().post(new UpdateEvent(Constant.UPDATE_ERROR));
        } else {
            SharedPreferencesUtil weatherShareUtil = WeatherApplication.getInstance().getWeatherMsg();

            if (aqi != null) {
                weatherShareUtil.save(Constant.AQI, aqi.getCity().getAqi());
                weatherShareUtil.save(Constant.PM25, aqi.getCity().getPm25());
                weatherShareUtil.save(Constant.QLTY, aqi.getCity().getQlty());
            }

            weatherShareUtil.save(Constant.NOW_TMP, now.getTmp());
            weatherShareUtil.save(Constant.NOW_CODE, now.getCond().getCode());
            weatherShareUtil.save(Constant.NOW_COND, now.getCond().getTxt());
            weatherShareUtil.save(Constant.NOW_WIND_DIR, now.getWind().getDir());
            weatherShareUtil.save(Constant.NOW_WIND_SC, now.getWind().getSc());

            for (int i = 1; i < 8; i++) {
                dailyForecast = dailyForecastList.get(i - 1);
                String temp = String.valueOf(i);
                weatherShareUtil.save(Constant.DAILY_TIME + temp, dailyForecast.getDate());
                weatherShareUtil.save(Constant.DAILY_TMP_MAX + temp, dailyForecast.getTmp().getMax());
                weatherShareUtil.save(Constant.DAILY_TMP_MIN + temp, dailyForecast.getTmp().getMin());
                weatherShareUtil.save(Constant.DAILY_COND_d + temp, dailyForecast.getCond().getTxtD());
                weatherShareUtil.save(Constant.DAILY_CODE_d + temp, dailyForecast.getCond().getCodeD());
                weatherShareUtil.save(Constant.DAILY_COND_n + temp, dailyForecast.getCond().getTxtN());
                weatherShareUtil.save(Constant.DAILY_CODE_n + temp, dailyForecast.getCond().getCodeN());
                weatherShareUtil.save(Constant.DAILY_WIND_DIR + temp, dailyForecast.getWind().getDir());
                weatherShareUtil.save(Constant.DAILY_WIND_SC + temp, dailyForecast.getWind().getSc());
            }
            weatherShareUtil.save(Constant.SUGGESTION_DRSG, suggestion.getDrsg().getTxt());

            BusUtil.getBus().post(new UpdateEvent(Constant.UPDATE_SUCCESS));
        }
    }

    /**
     * 解析json数据为bean对象，接口很傻逼草
     * @param data
     */
    private void resolveJson(ApiResultVO data) {
        init();
        weatherMsg = data.getHeWeather().get(0);
        aqi = weatherMsg.getAqi();
        basic = weatherMsg.getBasic();
        dailyForecastList = weatherMsg.getDailyForecast();
        hourlyForecastList = weatherMsg.getHourlyForecast();
        now = weatherMsg.getNow();
        suggestion = weatherMsg.getSuggestion();
        status = weatherMsg.getStatus();
    }

    /**
     * 初始化
     */
    private void init() {
        if (!dailyForecastList.isEmpty()) {
            dailyForecastList.clear();
        }
        if (!hourlyForecastList.isEmpty()) {
            hourlyForecastList.clear();
        }

    }
}
