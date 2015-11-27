package cchao.org.weatherapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 操作SharedPreferences
 * Created by chenchao on 15/11/27.
 */
public class SharedPreferencesUtil {

    private final String WEATHER_MESSAGE = "weatherMsg";

    private Context context;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    public SharedPreferencesUtil(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(WEATHER_MESSAGE, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * 保存
     * @param mark  标识
     * @param data  保存数据
     */
    public void save(String mark, String data) {
        editor.putString(mark, data);
        editor.commit();
    }

    /**
     * 获取
     * @param mark  标识
     * @return
     */
    public String get(String mark) {
        return sharedPreferences.getString(mark, "");
    }
}
