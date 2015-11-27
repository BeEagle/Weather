package cchao.org.weatherapp.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cchao.org.weatherapp.Constant;
import cchao.org.weatherapp.WeatherApplication;
import cchao.org.weatherapp.utils.SharedPreferencesUtil;

/**
 * Created by chenchao on 15/11/27.
 */
public abstract class BaseActivity extends AppCompatActivity{

    public SharedPreferencesUtil weatherMsg;

    @Override
    protected void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(getContentView());

        weatherMsg = WeatherApplication.getInstance().getWeatherMsg();

        bindView();
        initData();
        bindEvent();
    }

    /**
     * 判断是否存储城市id
     * @return
     */
    public boolean cityIsEmpty() {
        if (weatherMsg.get(Constant.CITY_ID).equals(""))
            return true;
        return false;
    }

    abstract protected int getContentView();

    abstract protected void bindView();

    abstract protected void initData();

    abstract protected void bindEvent();

}
