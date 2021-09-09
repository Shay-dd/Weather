package com.lxh.coolweather.db.util;

import android.database.Cursor;

import com.lxh.coolweather.db.entity.City;
import com.lxh.coolweather.db.entity.Province;
import com.lxh.coolweather.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class CityDaoUtil extends BaseUtil {

    public List<City> findAllCities(String sql, String[] selectionArgs) {
        List<City> citiesList = new ArrayList<>();
        Cursor cursor = selectBySql(sql, selectionArgs);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                City city = new City();
                city.setId(cursor.getLong(0));
                city.setCityName(cursor.getString(1));
                city.setCityCode(cursor.getInt(2));
                city.setProvinceId(cursor.getLong(3));
                citiesList.add(city);
            }
        }
        return citiesList;
    }
}
