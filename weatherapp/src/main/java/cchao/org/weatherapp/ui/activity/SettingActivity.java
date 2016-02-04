package cchao.org.weatherapp.ui.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cchao.org.weatherapp.Constant;
import cchao.org.weatherapp.R;
import cchao.org.weatherapp.controller.CityCodeDbController;
import cchao.org.weatherapp.ui.base.BaseActivity;

/**
 * 设置地点
 * Created by chenchao on 15/11/13.
 */
public class SettingActivity extends BaseActivity {

    private Toolbar mToolbar;

    private Spinner mProvinceSpinner;

    private Spinner mCitySpinner;

    private Spinner mCountrySpinner;

    private Button mOkButton;

    private CityCodeDbController mCityCodeDB;
    private SQLiteDatabase db = null;

    //省市区列表
    private List<String> mProvinceId, mProvinceName;
    private List<String> mCityId, mCityName;
    private List<String> mAreaId, mAreaName;

    //选中城市id
    private String mCityCode = null;

    //选中城市
    private String mCityCodeName = null;

    private int mClickItemNum;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void bindView() {
        mToolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        mProvinceSpinner = (Spinner) findViewById(R.id.setting_spinner_province);
        mCitySpinner = (Spinner) findViewById(R.id.setting_spinner_city);
        mCountrySpinner = (Spinner) findViewById(R.id.setting_spinner_county);
        mOkButton = (Button) findViewById(R.id.button);
    }

    @Override
    protected void initData() {
        mToolbar.setTitle(R.string.setting);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
//        mToolbar.setNavigationIcon(R.drawable.ic_home);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        initSpinner();
    }

    @Override
    protected void bindEvent() {
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.Builder builder;
                builder = new SimpleDialog.Builder(R.style.SimpleDialogLight) {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        mCityCodeName = mAreaName.get(mClickItemNum).toString();
                        mCityCode = mCityCodeDB.getCityCode(db, mAreaId.get(mClickItemNum)
                                .toString());
                        mWeatherMsg.save(Constant.CITY_ID, mCityCode);
                        mWeatherMsg.save(Constant.CITY_NAME, mCityCodeName);
                        setResult(UPDATE_ACTIVITY_RESULT);
                        finish();
                        super.onPositiveActionClicked(fragment);
                    }
                };
                builder.title("确定显示地区为" + mCountrySpinner.getSelectedItem().toString() + "吗?")
                        .positiveAction("Ok")
                        .negativeAction("Cancel");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), null);
            }
        });
    }

    private void initSpinner(){
        mCityCodeDB = new CityCodeDbController(SettingActivity.this);
        db = mCityCodeDB.getDatabase(DB_NAME);

        mProvinceId = new ArrayList<>();
        mProvinceName = new ArrayList<>();
        mCityId = new ArrayList<>();
        mCityName = new ArrayList<>();
        mAreaId = new ArrayList<>();
        mAreaName = new ArrayList<>();

        mClickItemNum = 0;

        //初始化城市选择Spinner
        String tempCode = mCityCodeDB.getCityId(db, mWeatherMsg.get(Constant.CITY_ID));
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
        Cursor provincecursor = mCityCodeDB.getAllProvince(database);

        if (provincecursor != null) {
            mProvinceId.clear();
            mProvinceName.clear();
            if (provincecursor.moveToFirst()) {
                do {
                    String province_id = provincecursor
                            .getString(provincecursor.getColumnIndex("id"));
                    String province_name = provincecursor
                            .getString(provincecursor.getColumnIndex("name"));
                    mProvinceId.add(province_id);
                    mProvinceName.add(province_name);
                } while (provincecursor.moveToNext());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, mProvinceName);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        mProvinceSpinner.setAdapter(adapter);
        mProvinceSpinner.setSelection(Integer.valueOf(initProvinceId) - 1);
        mProvinceSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                initCitySpinner(db, mProvinceId.get(position).toString(), "01");
                initAreaSpinner(db, mProvinceId.get(position).toString() + "01", "01");
            }
        });
    }

    /**
     * 初始化市Spinner
     * @param database  数据库
     * @param mProvinceId    选择的省id
     * @param initCityId    初始化的市id
     */
    private void initCitySpinner(SQLiteDatabase database, String mProvinceId, String initCityId) {
        Cursor citycursor = mCityCodeDB.getCity(database, mProvinceId);
        if (citycursor != null) {
            mCityId.clear();
            mCityName.clear();
            if (citycursor.moveToFirst()) {
                do {
                    String city_id = citycursor.getString(citycursor
                            .getColumnIndex("id"));
                    String city_name = citycursor.getString(citycursor
                            .getColumnIndex("name"));
                    String province = citycursor.getString(citycursor
                            .getColumnIndex("p_id"));
                    mCityId.add(city_id);
                    mCityName.add(city_name);
                } while (citycursor.moveToNext());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, mCityName);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        mCitySpinner.setAdapter(adapter);
        mCitySpinner.setSelection(Integer.valueOf(initCityId) - 1);
        mCitySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                initAreaSpinner(db, mCityId.get(position).toString(), "01");
            }
        });
    }

    /**
     * 初始化地区Spinner,同时获取城市码
     * @param database  数据库
     * @param mCityId    选择的市id
     * @param initAreaId    初始化的地区id
     */
    private void initAreaSpinner(SQLiteDatabase database, String mCityId, String initAreaId) {
        Cursor areacursor = mCityCodeDB.getArea(database, mCityId);
        if (areacursor != null) {
            mAreaId.clear();
            mAreaName.clear();
            if (areacursor.moveToFirst()) {
                do {
                    String area_id = areacursor.getString(areacursor
                            .getColumnIndex("id"));
                    String area_name = areacursor.getString(areacursor
                            .getColumnIndex("name"));
                    String city = areacursor.getString(areacursor
                            .getColumnIndex("c_id"));
                    mAreaId.add(area_id);
                    mAreaName.add(area_name);
                } while (areacursor.moveToNext());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, mAreaName);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        mCountrySpinner.setAdapter(adapter);
        mCountrySpinner.setSelection(Integer.valueOf(initAreaId) - 1);
        mCountrySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                mClickItemNum = position;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_setting_home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
