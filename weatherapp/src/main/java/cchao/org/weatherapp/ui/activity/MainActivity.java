package cchao.org.weatherapp.ui.activity;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cchao.org.weatherapp.Constant;
import cchao.org.weatherapp.R;
import cchao.org.weatherapp.api.Api;
import cchao.org.weatherapp.controller.SaveDataController;
import cchao.org.weatherapp.event.UpdateEvent;
import cchao.org.weatherapp.ui.adapter.DailyRecyclerAdapter;
import cchao.org.weatherapp.ui.base.BaseActivity;
import cchao.org.weatherapp.utils.BusUtil;
import cchao.org.weatherapp.utils.HttpUtil;

/**
 * Created by chenchao on 15/11/13.
 */
public class MainActivity extends BaseActivity {

    //refresh动画
    private Animation mAnimation;

    private Toolbar mToolbar;
    //现在温度
    private TextView mNowTmp;
    //现在天气状况
    private TextView mNowCond;
    //现在天气状况图片
    private ImageView mNowCondImage;
    //风向
    private TextView mWindDir;
    //风力
    private TextView mWindSc;
    //穿衣提示
    private TextView mSuggestionTxt;
    private ProgressDialog mProgressDialog;

    private RecyclerView mRecyclerDaily;
    private LinearLayoutManager mLinearLayoutManager;
    private DailyRecyclerAdapter mDailyRecyclerAdapter;
    private ArrayList<String> mDataTime, mDataTmp, mDataCondText, mDataCondImage;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mNowTmp = (TextView) findViewById(R.id.textview_now_tmp);
        mNowCond = (TextView) findViewById(R.id.textview_now_cond);
        mNowCondImage = (ImageView) findViewById(R.id.imageview_now_cond);
        mWindDir = (TextView) findViewById(R.id.textview_dir);
        mWindSc = (TextView) findViewById(R.id.textview_sc);
        mSuggestionTxt = (TextView) findViewById(R.id.textview_suggestion_txt);
        mRecyclerDaily = (RecyclerView) findViewById(R.id.recycler_daily);
    }

    @Override
    protected void initData() {
        BusUtil.getBus().register(this);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mProgressDialog = ProgressDialog.show(this, "请稍等", "刷新中...");
        mAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_menuitem_refresh);
        mAnimation.setRepeatCount(Animation.INFINITE);
        updateWeather();
        initRecycler();
    }

    @Override
    protected void bindEvent() {
        mToolbar.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                final View item = mToolbar.findViewById(R.id.menu_main_refresh);
                if (item != null) {
                    mToolbar.removeOnLayoutChangeListener(this);
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            item.startAnimation(mAnimation);
                            if (cityIsEmpty()) {
                                Toast.makeText(MainActivity.this, R.string.main_snackbar_ID_isEmpty, Toast.LENGTH_SHORT).show();
                                startActivityForResult(new Intent(MainActivity.this, SettingActivity.class), UPDATE_ACTIVITY_RESULT);
                            } else {
                                getWeather();
                            }
                        }
                    });
                }
            }
        });
    }


    @Subscribe
    public void subscribeUpdate(UpdateEvent event) {
        if (event.getMsg().equals(Constant.UPDATE_MSG)) {
            updateWeather();
            updateRecyclerData();
            mDailyRecyclerAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecycler() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerDaily.setLayoutManager(mLinearLayoutManager);

        mDailyRecyclerAdapter = new DailyRecyclerAdapter();
        updateRecyclerData();
        mRecyclerDaily.setAdapter(mDailyRecyclerAdapter);
    }

    /**
     * 更新recyclerview数据
     */
    private void updateRecyclerData() {
        mDataTime = new ArrayList<String>();
        mDataTmp = new ArrayList<String>();
        mDataCondText = new ArrayList<String>();
        mDataCondImage = new ArrayList<String>();
        if (!cityIsEmpty()) {
            for (int i = 2; i < 8; i++) {
                String temp = String.valueOf(i);
                mDataTime.add((mWeatherMsg.get(Constant.DAILY_TIME + temp)).substring(5));
                mDataTmp.add(mWeatherMsg.get(Constant.DAILY_TMP_MIN + temp) + "°~" + mWeatherMsg.get(Constant.DAILY_TMP_MAX + temp) + "°");
                mDataCondText.add(mWeatherMsg.get(Constant.DAILY_COND_d + temp));
                mDataCondImage.add(mWeatherMsg.get(Constant.DAILY_CODE_d + temp));
            }
        }
        mDailyRecyclerAdapter.setmDataCondImage(mDataCondImage);
        mDailyRecyclerAdapter.setmDataCondText(mDataCondText);
        mDailyRecyclerAdapter.setmDataTime(mDataTime);
        mDailyRecyclerAdapter.setmDataTmp(mDataTmp);
    }

    /**
     * 更新界面
     */
    private void updateWeather() {
        if (!cityIsEmpty()) {
            getSupportActionBar().setTitle(mWeatherMsg.get(Constant.CITY_NAME));
            mNowTmp.setText(mWeatherMsg.get(Constant.NOW_TMP) + "°");
            mNowCond.setText(mWeatherMsg.get(Constant.NOW_COND));
            mNowCondImage.setImageDrawable(getImage(mWeatherMsg.get(Constant.NOW_CODE)));
            mWindDir.setText(mWeatherMsg.get(Constant.NOW_WIND_DIR));
            mWindSc.setText(mWeatherMsg.get(Constant.NOW_WIND_SC));
            mSuggestionTxt.setText(mWeatherMsg.get(Constant.SUGGESTION_DRSG));
        } else {
            getSupportActionBar().setTitle("Weather");
        }
        if(mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 从服务器获取天气信息
     */
    private void getWeather() {
        mProgressDialog.show();
        Map<String, String> param = new HashMap<String, String>();
        param.put("cityid", "CN" + mWeatherMsg.get(Constant.CITY_ID));
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
        }
//        else if(id == R.id.menu_main_refresh) {
//            if (cityIsEmpty()) {
//                Toast.makeText(MainActivity.this, R.string.main_snackbar_ID_isEmpty, Toast.LENGTH_SHORT).show();
//                startActivityForResult(new Intent(MainActivity.this, SettingActivity.class), UPDATE_ACTIVITY_RESULT);
//            } else {
//                getWeather();
//            }
//        }
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
