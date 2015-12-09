package cchao.org.weatherapp.utils;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.Map;

import cchao.org.weatherapp.WeatherApplication;

/**
 * 网络请求类
 * Created by chenchao on 15/11/13.
 */
public class HttpUtil {

    private static Request mRequest;
    private static FormEncodingBuilder mFormEncodingBuilder;

    /**
     * post请求
     * @param uri   请求地址
     * @param params    参数
     * @param callback  回调
     * @throws IOException
     */
    public static void post(String uri, Map<String, String> params, Callback callback) throws IOException{
        mFormEncodingBuilder = new FormEncodingBuilder();
        for (String key : params.keySet()) {
            mFormEncodingBuilder.add(key, params.get(key));
        }
        mRequest = new Request.Builder()
                .url(uri)
                .post(mFormEncodingBuilder.build())
                .build();
        WeatherApplication.getInstance().getClient().newCall(mRequest).enqueue(callback);
    }
}
