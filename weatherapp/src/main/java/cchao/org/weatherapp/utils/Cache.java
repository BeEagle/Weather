package cchao.org.weatherapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 缓存数据类
 * Created by chenchao on 15/11/16.
 */
public class Cache {

    private final String CACHE = "cache";

    private Context context;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    public Cache(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(CACHE, Activity.MODE_PRIVATE);
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
