package cchao.org.weatherapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cchao.org.weatherapp.R;
import cchao.org.weatherapp.utils.Cache;
import cchao.org.weatherapp.utils.CityCodeDB;
import cchao.org.weatherapp.utils.Constant;

/**
 * 设置地点
 * Created by chenchao on 15/11/13.
 */
public class SettingActivity extends AppCompatActivity{

    private Toolbar mToolbar;

    private Spinner provinceSpinner;

    private Spinner citySpinner;

    private Spinner countrySpinner;

    private Button button;

    private CityCodeDB cityCodeDB;
    private SQLiteDatabase db = null;

    //省市区列表
    private List<String> provinceid, provincename;
    private List<String> cityid, cityname;
    private List<String> areaid, areaname;

    //选中城市id
    private String citycode = null;

    //选中城市
    private String citycode_name = null;

    //缓存数据
    private Cache cache;

    private int clickItemNum;

    @Override
    protected void onCreate(Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(R.layout.activity_setting);

        mToolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        provinceSpinner = (Spinner) findViewById(R.id.setting_spinner_province);
        citySpinner = (Spinner) findViewById(R.id.setting_spinner_city);
        countrySpinner = (Spinner) findViewById(R.id.setting_spinner_county);
        button = (Button) findViewById(R.id.button);

        mToolbar.setTitle("Setting");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_home_white_36dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, MainActivity.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citycode_name = areaname.get(clickItemNum).toString();
                citycode = cityCodeDB.getCityCode(db, areaid.get(clickItemNum)
                        .toString());
                cache.save(Constant.CITY_ID, citycode);
                cache.save(Constant.CITY_NAME, citycode_name);
                startActivity(new Intent(SettingActivity.this, MainActivity.class));
            }
        });

        init();
    }

    private void init(){

        cache = new Cache(SettingActivity.this);

        cityCodeDB = new CityCodeDB(SettingActivity.this);
        db = cityCodeDB.getDatabase("city.db");

        provinceid = new ArrayList<>();
        provincename = new ArrayList<>();
        cityid = new ArrayList<>();
        cityname = new ArrayList<>();
        areaid = new ArrayList<>();
        areaname = new ArrayList<>();

        clickItemNum = 0;

        //初始化城市选择Spinner
        String tempCode = cityCodeDB.getCityId(db, cache.get(Constant.CITY_ID));
        if (tempCode != "" && tempCode != null) {
            initProvinceSpinner(db, tempCode.substring(0, 2));
            initCitySpinner(db, tempCode.substring(0, 2), tempCode.substring(2, 4));
            initAreaSpinner(db, tempCode.substring(0, 4), tempCode.substring(4, 6));
        } else {
            initProvinceSpinner(db, "01");
            initCitySpinner(db, "01", "01");
            initAreaSpinner(db, "0101", "01");
        }
    }

    /**
     * 初始化省/直辖市Spinner
     * @param database  数据库
     * @param initProvinceId    初始化的省id
     */
    private void initProvinceSpinner(SQLiteDatabase database, String initProvinceId) {
        Cursor provincecursor = cityCodeDB.getAllProvince(database);

        if (provincecursor != null) {
            provinceid.clear();
            provincename.clear();
            if (provincecursor.moveToFirst()) {
                do {
                    String province_id = provincecursor
                            .getString(provincecursor.getColumnIndex("id"));
                    String province_name = provincecursor
                            .getString(provincecursor.getColumnIndex("name"));
                    provinceid.add(province_id);
                    provincename.add(province_name);
                } while (provincecursor.moveToNext());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, provincename);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        provinceSpinner.setAdapter(adapter);
        provinceSpinner.setSelection(Integer.valueOf(initProvinceId) - 1);
        provinceSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                initCitySpinner(db, provinceid.get(position).toString(), "01");
                initAreaSpinner(db, provinceid.get(position).toString() + "01", "01");
            }
        });
    }

    /**
     * 初始化市Spinner
     * @param database  数据库
     * @param provinceid    选择的省id
     * @param initCityId    初始化的市id
     */
    private void initCitySpinner(SQLiteDatabase database, String provinceid, String initCityId) {
        Cursor citycursor = cityCodeDB.getCity(database, provinceid);
        if (citycursor != null) {
            cityid.clear();
            cityname.clear();
            if (citycursor.moveToFirst()) {
                do {
                    String city_id = citycursor.getString(citycursor
                            .getColumnIndex("id"));
                    String city_name = citycursor.getString(citycursor
                            .getColumnIndex("name"));
                    String province = citycursor.getString(citycursor
                            .getColumnIndex("p_id"));
                    cityid.add(city_id);
                    cityname.add(city_name);
                } while (citycursor.moveToNext());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, cityname);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        citySpinner.setAdapter(adapter);
        citySpinner.setSelection(Integer.valueOf(initCityId) - 1);
        citySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                initAreaSpinner(db, cityid.get(position).toString(), "01");
            }
        });
    }

    /**
     * 初始化地区Spinner,同时获取城市码
     * @param database  数据库
     * @param cityid    选择的市id
     * @param initAreaId    初始化的地区id
     */
    private void initAreaSpinner(SQLiteDatabase database, String cityid, String initAreaId) {
        Cursor areacursor = cityCodeDB.getArea(database, cityid);
        if (areacursor != null) {
            areaid.clear();
            areaname.clear();
            if (areacursor.moveToFirst()) {
                do {
                    String area_id = areacursor.getString(areacursor
                            .getColumnIndex("id"));
                    String area_name = areacursor.getString(areacursor
                            .getColumnIndex("name"));
                    String city = areacursor.getString(areacursor
                            .getColumnIndex("c_id"));
                    areaid.add(area_id);
                    areaname.add(area_name);
                } while (areacursor.moveToNext());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, areaname);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        countrySpinner.setAdapter(adapter);
        countrySpinner.setSelection(Integer.valueOf(initAreaId) - 1);
        countrySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                clickItemNum = position;
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
