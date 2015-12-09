package cchao.org.weatherapp.network;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * 自定义Converter，返回字符串不需要解析
 * Created by chenchao on 15/12/9.
 */
public class MyConverter extends Converter.Factory{

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new Converter<ResponseBody, String>() {
            @Override
            public String convert(ResponseBody value) throws IOException {
                return value.string();
            }
        };
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return new Converter<String, RequestBody>() {
            @Override
            public RequestBody convert(String value) throws IOException {
                return RequestBody.create(MediaType.parse("text/plain"), value);
            }
        };
    }
}
