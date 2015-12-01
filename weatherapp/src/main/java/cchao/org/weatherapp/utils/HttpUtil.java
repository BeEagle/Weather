package cchao.org.weatherapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * 网络请求类
 * Created by chenchao on 15/11/13.
 */
public class HttpUtil {
    private static final int TIMEOUT_IN_MILLIONS = 5000;

    public interface CallBack {
        void onSuccess(String result);
        void onError();
    }

    /**
     * 异步的Post请求
     * @param urlStr
     * @param params 参数，形如cityid=475847&key=23423
     * @param callBack
     * @throws Exception
     */
    public static void doPostAsyn(final String urlStr, final String params,
                                  final CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(urlStr, params);
                    if (result == null) {
                        callBack.onError();
                    } else if (callBack != null){
                        callBack.onSuccess(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    /**
     * 异步Post请求
     * @param urlStr
     * @param map   map型参数
     * @param callBack
     * @throws Exception
     */
    public static void doPostAsyn(final String urlStr, final Map<String, String> map,
                                  final CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    StringBuffer params = new StringBuffer();
                    for (Map.Entry<String, String> mapParam : map.entrySet()) {
                        params.append(mapParam.getKey() + "=" + mapParam.getValue() + "&");
                    }
                    params.deleteCharAt(params.length() - 1);
                    String result = doPost(urlStr, params.toString());
                    if (result == null) {
                        callBack.onError();
                    } else if (callBack != null){
                        callBack.onSuccess(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    private static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
