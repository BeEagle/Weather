package cchao.org.weatherapp.network;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by chenchao on 15/12/9.
 */
public interface WeatherMsgService {

    @FormUrlEncoded
    @POST("x3/weather")
    Call<String> getWeatherMsg(@Field("cityid") String cityid, @Field("key") String key);

}
