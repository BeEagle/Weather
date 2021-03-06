package cchao.org.weatherapp.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cchao.org.weatherapp.manager.AssetsDatabaseManager;

/**
 * Created by chenchao on 15/11/27.
 */
public class CityCodeDbController {
    public static final String TABLE_PROVINCE = "province";
    public static final String TABLE_CITY = "city";
    public static final String TABLE_AREA = "area";
    public static final String TABLE_CITY_CODE = "city_code";

    private Context context;

    public CityCodeDbController(Context context) {
        this.context = context;
    }

    public SQLiteDatabase getDatabase(String dbname) {
        AssetsDatabaseManager.initManager(context);
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase(dbname);
        return db;
    }

    /**
     * 查询province表,返回省份信息cursor
     * @param db
     * @return
     */
    public Cursor getAllProvince(SQLiteDatabase db) {
        if (db != null) {
            Cursor cur = db
                    .query(TABLE_PROVINCE, new String[] { "id", "name" }, null,
                            null, null, null, null);
            return cur;
        } else {
            return null;
        }
    }

    /**
     * 查询city表,返回指定省份的所有城市信息cursor
     * @param db
     * @param provinceid    省份id
     * @return
     */
    public Cursor getCity(SQLiteDatabase db, String provinceid) {
        if (db != null) {
            Cursor cur = db.query(TABLE_CITY, new String[]{"id", "p_id",
                            "name"}, "p_id = ?", new String[]{provinceid}, null,
                    null, null);
            return cur;
        } else {
            return null;
        }
    }

    /**
     * 查询area表,返回指定城市的所有地区信息cursor
     * @param db
     * @param cityid    城市id
     * @return
     */
    public Cursor getArea(SQLiteDatabase db, String cityid) {
        if (db != null) {
            Cursor cur = db.query(TABLE_AREA, new String[]{"id", "c_id",
                            "name"}, "c_id = ?", new String[]{cityid}, null, null,
                    null);
            return cur;
        } else {
            return null;
        }
    }

    /**
     * 查询city_code表,通过areaid获取城市码code
     * @param db
     * @param areaid    地区id
     * @return
     */
    public String getCityCode(SQLiteDatabase db, String areaid) {
        if (db != null) {
            Cursor cur = db.query(TABLE_CITY_CODE, new String[] { "id", "code",
                            "name" }, "id = ?", new String[] { areaid }, null, null,
                    null);
            String citycode = null;
            if (cur.moveToFirst()) {
                citycode = cur.getString(cur.getColumnIndex("code"));
            }
            return citycode;
        } else {
            return null;
        }
    }

    /**
     * 查询city_code表，通过code获取城市id
     * @param db
     * @param code  地区码
     * @return
     */
    public String getCityId(SQLiteDatabase db, String code) {
        if(db != null) {
            Cursor cursor = db.query(TABLE_CITY_CODE, new String[] { "id", "code",
                    "name"}, "code = ?", new String[] {code}, null, null, null);
            String cityId = null;
            if(cursor.moveToFirst()) {
                cityId = cursor.getString(cursor.getColumnIndex("id"));
            }
            return cityId;
        } else {
            return null;
        }
    }

    /**
     * 查询city_code表,通过areaname获取城市码code
     * @param db
     * @param areaname  地区名字
     * @return
     */
    public String getCityCodeByName(SQLiteDatabase db, String areaname) {
        if (db != null) {
            Cursor cur = db.query(TABLE_CITY_CODE, new String[] { "id", "code",
                            "name" }, "name = ?", new String[] { areaname }, null,
                    null, null);
            String citycode = null;
            if (cur.moveToFirst()) {
                citycode = cur.getString(cur.getColumnIndex("code"));
            }
            return citycode;
        } else {
            return null;
        }
    }
}
