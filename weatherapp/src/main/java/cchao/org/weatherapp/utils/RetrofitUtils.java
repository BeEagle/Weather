package cchao.org.weatherapp.utils;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import cchao.org.weatherapp.api.Api;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by chenchao on 16/2/24.
 */
public class RetrofitUtils {

    private static final Retrofit RETROFIT;

    static {

        OkHttpClient client = new OkHttpClient();

        client.setReadTimeout(20, TimeUnit.SECONDS);
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setWriteTimeout(20, TimeUnit.SECONDS);

        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                BufferedSource source = response.body().source();
                source.request(Long.MAX_VALUE);
                Log.i(request.method() + "-->" + request.url());
                Log.i(source.buffer().clone().readUtf8());
                return response;
            }
        });

        RETROFIT = new Retrofit.Builder()
                .client(client)
                .baseUrl(Api.getWeatherUri())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private RetrofitUtils() {
        throw new AssertionError("No instances");
    }

    public static <T> T create(Class<T> service) {
        return RETROFIT.create(service);
    }
}
