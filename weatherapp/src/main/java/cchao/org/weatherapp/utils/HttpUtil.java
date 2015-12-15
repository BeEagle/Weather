package cchao.org.weatherapp.utils;

import cchao.org.weatherapp.api.Api;
import cchao.org.weatherapp.network.StringConverterFactory;
import cchao.org.weatherapp.network.WeatherMsgService;
import retrofit.Call;
import retrofit.Retrofit;

/**
 * 网络请求类
 * Created by chenchao on 15/11/13.
 */
public class HttpUtil {

    private StringConverterFactory mStringConverterFactory;
    private Retrofit retrofit;
    private WeatherMsgService weatherMsgService;
    private Call<String> call;

    /**
     * retrofit post异步请求
     * @param cityid
     * @param key
     * @param callback 回调
     */
    public void retrofitPost(String cityid, String key, retrofit.Callback<String> callback) {
        mStringConverterFactory = new StringConverterFactory();
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.getWeatherUri())
                .addConverterFactory(mStringConverterFactory)
                .build();
        weatherMsgService = retrofit.create(WeatherMsgService.class);
        call = weatherMsgService.getWeatherMsg(cityid, key);
        call.enqueue(callback);
    }

    /**
     * 取消事务
     */
    public void cancel() {
        call.cancel();
    }
}
