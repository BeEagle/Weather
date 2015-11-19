package cchao.org.weatherapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cchao.org.weatherapp.R;
import cchao.org.weatherapp.utils.BaseUri;
import cchao.org.weatherapp.utils.Cache;
import cchao.org.weatherapp.utils.Constant;
import cchao.org.weatherapp.utils.HttpUtil;
import cchao.org.weatherapp.utils.SaveData;

/**
 * Created by chenchao on 15/11/13.
 */
public class MainActivity extends AppCompatActivity{

    private Toolbar mToolbar;

    private FloatingActionButton mFloatingActionButton;

    private Cache cache;

    @Override
    protected void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle("Weather");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        cache = new Cache(this);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cache.get(Constant.CITY_ID).isEmpty() || cache.get(Constant.CITY_ID).equals("")) {
                    Toast.makeText(MainActivity.this, R.string.main_snackbar_ID_isEmpty, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                } else {
                    getWeather();
                }
            }
        });
    }

    private void getWeather() {
        try{
            HttpUtil.doPostAsyn(BaseUri.getWeatherUri()
                    , "cityid=CN" + cache.get(Constant.CITY_ID) + "&key=" + Constant.KEY
                    , new HttpUtil.CallBack() {
                @Override
                public void onRequestComplete(String result) {
                    Log.i("weather", result);
                    SaveData.saveResponse(result);
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
            startActivity(new Intent(MainActivity.this, SettingActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
