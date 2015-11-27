package cchao.org.weatherapp.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

import cchao.org.weatherapp.Constant;
import cchao.org.weatherapp.R;
import cchao.org.weatherapp.api.Api;
import cchao.org.weatherapp.controller.SaveDataController;
import cchao.org.weatherapp.event.UpdateEvent;
import cchao.org.weatherapp.ui.base.BaseActivity;
import cchao.org.weatherapp.utils.BusUtil;
import cchao.org.weatherapp.utils.HttpUtil;

/**
 * Created by chenchao on 15/11/13.
 */
public class MainActivity extends BaseActivity {

    private static final int UPDATE_ACTIVITY_RESULT = 100;
    private Toolbar mToolbar;
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
    private ProgressDialog progressDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        nowTmp = (TextView) findViewById(R.id.textview_now_tmp);
        nowCond = (TextView) findViewById(R.id.textview_now_cond);
        nowCondImage = (ImageView) findViewById(R.id.imageview_now_cond);
        windDir = (TextView) findViewById(R.id.textview_dir);
        windSc = (TextView) findViewById(R.id.textview_sc);
        suggestionTxt = (TextView) findViewById(R.id.textview_suggestion_txt);
    }

    @Override
    protected void initData() {
        BusUtil.getBus().register(this);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        progressDialog = ProgressDialog.show(this, "请稍等", "刷新中...");
        updateWeather();
    }

    @Override
    protected void bindEvent() {}


    @Subscribe
    public void subscribeUpdate(UpdateEvent event) {
        if (event.getMsg().equals(Constant.UPDATE_MSG)) {
            updateWeather();
        }
    }

    /**
     * 更新界面
     */
    private void updateWeather() {
        if (!cityIsEmpty()) {
            getSupportActionBar().setTitle(weatherMsg.get(Constant.CITY_NAME));
            nowTmp.setText(weatherMsg.get(Constant.NOW_TMP) + "°");
            nowCond.setText(weatherMsg.get(Constant.NOW_COND));
            nowCondImage.setImageDrawable(getImage(weatherMsg.get(Constant.NOW_CODE)));
            windDir.setText(weatherMsg.get(Constant.NOW_WIND_DIR));
            windSc.setText(weatherMsg.get(Constant.NOW_WIND_SC));
            suggestionTxt.setText(weatherMsg.get(Constant.SUGGESTION_DRSG));
        } else {
            getSupportActionBar().setTitle("Weather");
        }
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 从服务器获取天气信息
     */
    private void getWeather() {
        progressDialog.show();
        Map<String, String> param = new HashMap<String, String>();
        param.put("cityid", "CN" + weatherMsg.get(Constant.CITY_ID));
        param.put("key", Constant.KEY);
        try{
            HttpUtil.doPostAsyn(Api.getWeatherUri()
                    , param
                    , new HttpUtil.CallBack() {
                @Override
                public void onRequestComplete(String result) {
                    Log.i("weather", result);
                    SaveDataController.saveResponse(result);
                }
            });
        }catch (Exception e) {
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
            startActivityForResult(new Intent(MainActivity.this, SettingActivity.class), UPDATE_ACTIVITY_RESULT);
            return true;
        } else if(id == R.id.menu_main_refresh) {
            if (cityIsEmpty()) {
                Toast.makeText(MainActivity.this, R.string.main_snackbar_ID_isEmpty, Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(MainActivity.this, SettingActivity.class), UPDATE_ACTIVITY_RESULT);
            } else {
                getWeather();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == UPDATE_ACTIVITY_RESULT) {
            getWeather();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusUtil.getBus().unregister(this);
    }
}
