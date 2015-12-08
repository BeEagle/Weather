package cchao.org.weatherapp.utils;

import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import cchao.org.weatherapp.api.Key;

/**
 * 网络请求类
 * Created by chenchao on 15/11/13.
 */
public class HttpUtil {

    private static OkHttpClient okHttpClient;
    private static Request request;
    private static RequestBody requestBody;
    private static Response response;

    public interface CallBack {
        void onSuccess(String result);
        void onError();
    }

    public static void doPostAsyn(final String urlStr, final String param,
                                  final CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = post(urlStr, param);
                    callBack.onSuccess(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    public static String post(String uri, String cityCode) throws IOException{
        okHttpClient = new OkHttpClient();
        requestBody = new FormEncodingBuilder()
                .add("cityid", cityCode)
                .add("key", Key.KEY)
                .build();
        request = new Request.Builder()
                .url(uri)
                .post(requestBody)
                .build();
        response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            //response.body().string()不能用两次，否则报异常
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
