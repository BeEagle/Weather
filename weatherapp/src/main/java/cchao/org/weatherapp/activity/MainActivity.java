package cchao.org.weatherapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cchao.org.weatherapp.R;
import cchao.org.weatherapp.utils.BaseUri;
import cchao.org.weatherapp.utils.Cache;
import cchao.org.weatherapp.utils.Constant;
import cchao.org.weatherapp.utils.HttpUtil;

/**
 * Created by chenchao on 15/11/13.
 */
public class MainActivity extends AppCompatActivity implements Handler.Callback{

    private static final int MSG_UPDATE = 1002;
    private static final long START_DELAY = 2000;
    private static final int STARTACTIVITYRESULT = 100;

    private Toolbar mToolbar;

    private FloatingActionButton mFloatingActionButton;

    //现在温度
    private TextView nowTmp;
    //现在天气状况
    private TextView nowCond;
    //现在天气状况图片
    private ImageView nowCondImage;
    //风向
    private TextView windDir;
    //风力
    private TextView windSc;
    //穿衣提示
    private TextView suggestionTxt;

    private ProgressView progressView;

    private Cache cache;

    private Handler myHandle;

    @Override
    protected void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(R.layout.activity_main);

        cache = new Cache(this);
        myHandle = new Handler(this);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (cache.get(Constant.CITY_NAME) != "") {
            mToolbar.setTitle(cache.get(Constant.CITY_NAME));
        } else {
            mToolbar.setTitle("Weather");
        }
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        nowTmp = (TextView) findViewById(R.id.textview_now_tmp);
        nowCond = (TextView) findViewById(R.id.textview_now_cond);
        nowCondImage = (ImageView) findViewById(R.id.imageview_now_cond);
        windDir = (TextView) findViewById(R.id.textview_dir);
        windSc = (TextView) findViewById(R.id.textview_sc);
        suggestionTxt = (TextView) findViewById(R.id.textview_suggestion_txt);
        progressView = (ProgressView) findViewById(R.id.progress);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cache.get(Constant.CITY_ID).isEmpty() || cache.get(Constant.CITY_ID).equals("")) {
                    Toast.makeText(MainActivity.this, R.string.main_snackbar_ID_isEmpty, Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(MainActivity.this, SettingActivity.class), STARTACTIVITYRESULT);
                    //startActivity(new Intent(MainActivity.this, SettingActivity.class));
                } else {
                    getWeather();
                }
            }
        });

        updateWeather();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_UPDATE:
                updateWeather();
                break;
            default:
                break;
        }
        return false;
    }

    private void updateWeather() {
        nowTmp.setText(cache.get(Constant.NOW_TMP));
        nowCond.setText(cache.get(Constant.NOW_COND));
        nowCondImage.setImageDrawable(getImage(cache.get(Constant.NOW_CODE)));
        windDir.setText(cache.get(Constant.NOW_WIND_DIR));
        windSc.setText(cache.get(Constant.NOW_WIND_SC));
        suggestionTxt.setText(cache.get(Constant.SUGGESTION_DRSG));
        progressView.stop();
    }

    private void getWeather() {
        progressView.start();
        try{
            HttpUtil.doPostAsyn(BaseUri.getWeatherUri()
                    , "cityid=CN" + cache.get(Constant.CITY_ID) + "&key=" + Constant.KEY
                    , new HttpUtil.CallBack() {
                @Override
                public void onRequestComplete(String result) {
                    Log.i("weather", result);
                    saveResponse(result);
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveResponse(String data) {
        try {
            JSONObject weatherObj = new JSONObject(data);
            JSONArray weatherArray = weatherObj.getJSONArray("HeWeather data service 3.0");
            JSONObject jsonObject = weatherArray.getJSONObject(0);
            if (jsonObject.getString("status").equals("ok")) {
                //空气质量指数
                //JSONObject aqi_obj = jsonObject.getJSONObject("aqi");
                //城市基本信息
                JSONObject basic_obj = jsonObject.getJSONObject("basic");
                //实况天气
                JSONObject now_obj = jsonObject.getJSONObject("now");
                //生活指数
                JSONObject suggestion_obj = jsonObject.getJSONObject("suggestion");
                //天气预报
                JSONArray daily_forecast_array = jsonObject.getJSONArray("daily_forecast");
                //每小时天气预报
                JSONArray hourly_forecast_array = jsonObject.getJSONArray("hourly_forecast");

                //cache.save(Constant.AQI, aqi_obj.getJSONObject("city").getString("aqi"));
                //cache.save(Constant.PM25, aqi_obj.getJSONObject("city").getString("pm25"));
                //cache.save(Constant.QLTY, aqi_obj.getJSONObject("city").getString("qlty"));

                cache.save(Constant.NOW_TMP, now_obj.getString("tmp"));
                cache.save(Constant.NOW_CODE, now_obj.getJSONObject("cond").getString("code"));
                cache.save(Constant.NOW_COND, now_obj.getJSONObject("cond").getString("txt"));
                cache.save(Constant.NOW_WIND_DIR, now_obj.getJSONObject("wind").getString("dir"));
                cache.save(Constant.NOW_WIND_SC, now_obj.getJSONObject("wind").getString("sc"));

                for (int i = 1; i < 8; i++) {
                    JSONObject dailyObj = daily_forecast_array.getJSONObject(i - 1);
                    String temp = String.valueOf(i);
                    cache.save(Constant.DAILY_TIME + temp, dailyObj.getString("date"));
                    cache.save(Constant.DAILY_TMP_MAX + temp, dailyObj.getJSONObject("tmp").getString("max"));
                    cache.save(Constant.DAILY_TMP_MIN + temp, dailyObj.getJSONObject("tmp").getString("min"));
                    cache.save(Constant.DAILY_COND_d + temp, dailyObj.getJSONObject("cond").getString("txt_d"));
                    cache.save(Constant.DAILY_CODE_d + temp, dailyObj.getJSONObject("cond").getString("code_d"));
                    cache.save(Constant.DAILY_COND_n + temp, dailyObj.getJSONObject("cond").getString("txt_n"));
                    cache.save(Constant.DAILY_CODE_n + temp, dailyObj.getJSONObject("cond").getString("code_n"));
                    cache.save(Constant.DAILY_WIND_DIR + temp, dailyObj.getJSONObject("wind").getString("dir"));
                    cache.save(Constant.DAILY_WIND_SC + String.valueOf(i + 1), dailyObj.getJSONObject("wind").getString("sc"));
                }

                cache.save(Constant.SUGGESTION_DRSG, suggestion_obj.getJSONObject("drsg").getString("txt"));

                myHandle.sendEmptyMessageAtTime(MSG_UPDATE, START_DELAY);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取天气图标
     * @param imageName 天气名称(数字)
     * @return
     */
    private Drawable getImage(String imageName) {
        int id = getResources().getIdentifier("w" + imageName, "drawable", "cchao.org.weatherapp");
        if(id != 0){
            return getResources().getDrawable(id);
        }
        return getResources().getDrawable(getResources().getIdentifier("w999", "drawable", "cchao.org.weatherapp"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_main_location) {
            startActivityForResult(new Intent(MainActivity.this, SettingActivity.class), STARTACTIVITYRESULT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == STARTACTIVITYRESULT) {
            getWeather();
        }
    }
}
