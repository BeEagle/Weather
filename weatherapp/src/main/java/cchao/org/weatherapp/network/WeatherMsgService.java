package cchao.org.weatherapp.network;

import cchao.org.weatherapp.bean.ApiResultVO;
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
    Call<ApiResultVO> getWeatherMsg(@Field("cityid") String cityid, @Field("key") String key);

}
