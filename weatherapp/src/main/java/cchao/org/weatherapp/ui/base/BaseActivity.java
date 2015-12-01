package cchao.org.weatherapp.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private String mYear;
    private String mMonth;
    private String mDay;
    private String mWay;

    @Override
    protected void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(getContentView());
        getWindow().setBackgroundDrawable(null);
        mWeatherMsg = WeatherApplication.getInstance().getWeatherMsg();

        bindView();
        initData();
        bindEvent();
    }

    /**
     * 获取当前日期
     * @return
     */
    public String getWhatDay() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return mMonth + "月" + mDay+"日"+"/星期"+mWay;
    }

    /**
     * 判断是否为数字
     * @param str
     * @return
     */
    public boolean isDigital(String str) {
        Pattern mPattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = mPattern.matcher(str);
        return matcher.matches();
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
