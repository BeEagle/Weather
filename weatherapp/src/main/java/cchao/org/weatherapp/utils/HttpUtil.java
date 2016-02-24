package cchao.org.weatherapp.utils;

import cchao.org.weatherapp.vo.ApiResultVO;
import cchao.org.weatherapp.network.WeatherMsgService;
import rx.Observable;

/**
 * 网络请求类
 * Created by chenchao on 15/11/13.
 */
public class HttpUtil {

    private static WeatherMsgService weatherMsgService;

    /**
     * retrofit post异步请求
     * @param cityid
     * @param key
     */
    public static Observable<ApiResultVO> retrofitPost(String cityid, String key) {
        weatherMsgService = RetrofitUtils.create(WeatherMsgService.class);
        return weatherMsgService.getWeatherMsg(cityid, key);
    }
}
