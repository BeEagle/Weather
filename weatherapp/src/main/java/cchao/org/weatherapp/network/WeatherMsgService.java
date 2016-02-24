package cchao.org.weatherapp.network;

import cchao.org.weatherapp.vo.ApiResultVO;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;
import rx.Observer;

/**
 * Created by chenchao on 15/12/9.
 */
public interface WeatherMsgService {

    @FormUrlEncoded
    @POST("x3/weather")
    Observable<ApiResultVO> getWeatherMsg(@Field("cityid") String cityid, @Field("key") String key);

}
