package cchao.org.weatherapp.utils;

import cchao.org.weatherapp.api.Api;
import cchao.org.weatherapp.network.MyConverter;
import cchao.org.weatherapp.network.WeatherMsgService;
import retrofit.Call;
import retrofit.Retrofit;

/**
 * 网络请求类
 * Created by chenchao on 15/11/13.
 */
public class HttpUtil {

    private static MyConverter myConverter;

    /**
     * retrofit post异步请求
     * @param cityid
     * @param key
     * @param callback 回调
     */
    public static void retrofitPost(String cityid, String key, retrofit.Callback<String> callback) {
        myConverter = new MyConverter();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.getWeatherUri())
                .addConverterFactory(myConverter)
                .build();
        WeatherMsgService weatherMsgService = retrofit.create(WeatherMsgService.class);
        Call<String> result = weatherMsgService.getWeatherMsg(cityid, key);
        result.enqueue(callback);
    }
}
