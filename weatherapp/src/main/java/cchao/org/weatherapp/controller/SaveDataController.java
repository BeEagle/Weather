package cchao.org.weatherapp.controller;

import java.util.ArrayList;
import java.util.List;

import cchao.org.weatherapp.Constant;
import cchao.org.weatherapp.App;
import cchao.org.weatherapp.vo.ApiResultVO;
import cchao.org.weatherapp.vo.ApiResultVO.HeWeather;
import cchao.org.weatherapp.vo.ApiResultVO.HeWeather.Aqi;
import cchao.org.weatherapp.vo.ApiResultVO.HeWeather.Basic;
import cchao.org.weatherapp.vo.ApiResultVO.HeWeather.DailyForecast;
import cchao.org.weatherapp.vo.ApiResultVO.HeWeather.HourlyForecast;
import cchao.org.weatherapp.vo.ApiResultVO.HeWeather.Now;
import cchao.org.weatherapp.vo.ApiResultVO.HeWeather.Suggestion;
import cchao.org.weatherapp.utils.SPUtil;

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
    public Boolean saveResponse(ApiResultVO data) {
        resolveJson(data);
        if (!status.equals("ok")) {
            return false;
        } else {
            SPUtil spUtil = App.getInstance().getWeatherMsg();

            if (aqi != null) {
                spUtil.save(Constant.AQI, aqi.getCity().getAqi());
                spUtil.save(Constant.PM25, aqi.getCity().getPm25());
                spUtil.save(Constant.QLTY, aqi.getCity().getQlty());
            }

            spUtil.save(Constant.NOW_TMP, now.getTmp());
            spUtil.save(Constant.NOW_CODE, now.getCond().getCode());
            spUtil.save(Constant.NOW_COND, now.getCond().getTxt());
            spUtil.save(Constant.NOW_WIND_DIR, now.getWind().getDir());
            spUtil.save(Constant.NOW_WIND_SC, now.getWind().getSc());

            for (int i = 1; i < 8; i++) {
                dailyForecast = dailyForecastList.get(i - 1);
                String temp = String.valueOf(i);
                spUtil.save(Constant.DAILY_TIME + temp, dailyForecast.getDate());
                spUtil.save(Constant.DAILY_TMP_MAX + temp, dailyForecast.getTmp().getMax());
                spUtil.save(Constant.DAILY_TMP_MIN + temp, dailyForecast.getTmp().getMin());
                spUtil.save(Constant.DAILY_COND_d + temp, dailyForecast.getCond().getTxtD());
                spUtil.save(Constant.DAILY_CODE_d + temp, dailyForecast.getCond().getCodeD());
                spUtil.save(Constant.DAILY_COND_n + temp, dailyForecast.getCond().getTxtN());
                spUtil.save(Constant.DAILY_CODE_n + temp, dailyForecast.getCond().getCodeN());
                spUtil.save(Constant.DAILY_WIND_DIR + temp, dailyForecast.getWind().getDir());
                spUtil.save(Constant.DAILY_WIND_SC + temp, dailyForecast.getWind().getSc());
            }
            spUtil.save(Constant.SUGGESTION_DRSG, suggestion.getDrsg().getTxt());

            return true;
        }
    }

    /**
     * 解析json数据为bean对象
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
