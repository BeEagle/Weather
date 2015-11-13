package cchao.org.weatherapp.utils;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import cchao.org.weatherapp.WeatherApplication;

/**
 * Created by chenchao on 15/11/13.
 */
public class HttpUtil {

    private final String KEY = "20fe7c4fb5e240c6a973d6de3a321907";

    private String cityId;

    private Activity activity;

    public HttpUtil(Activity activity,String cityId){
        this.activity = activity;
        this.cityId = cityId;
    }

    public void getWeather(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUri.getWeatherUri(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("weather", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("weatherapp", error.getMessage());
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("cityid", cityId);
                map.put("key", KEY);
                return map;
            }
        };
        WeatherApplication.getInstance().requestQueue.add(stringRequest);
    }
}
