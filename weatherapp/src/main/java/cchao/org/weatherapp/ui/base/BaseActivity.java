package cchao.org.weatherapp.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rey.material.app.Dialog;

import cchao.org.weatherapp.Constant;
import cchao.org.weatherapp.WeatherApplication;
import cchao.org.weatherapp.utils.SharedPreferencesUtil;

/**
 * Created by chenchao on 15/11/27.
 */
public abstract class BaseActivity extends AppCompatActivity{

    //设置界面跳转回主界面setResult值
    public static final int UPDATE_ACTIVITY_RESULT = 100;
    //存储城市代码的数据库
    public static final String DB_NAME = "city.db";

    public SharedPreferencesUtil mWeatherMsg;

    @Override
    protected void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(getContentView());

        mWeatherMsg = WeatherApplication.getInstance().getWeatherMsg();

        bindView();
        initData();
        bindEvent();
    }

    /**
     * 判断是否存储城市id
     * @return
     */
    public boolean cityIsEmpty() {
        if (mWeatherMsg.get(Constant.CITY_ID).equals(""))
            return true;
        return false;
    }

    abstract protected int getContentView();

    abstract protected void bindView();

    abstract protected void initData();

    abstract protected void bindEvent();

}
