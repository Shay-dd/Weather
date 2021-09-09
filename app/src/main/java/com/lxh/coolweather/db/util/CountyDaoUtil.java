package com.lxh.coolweather.db.util;

import android.database.Cursor;

import com.lxh.coolweather.db.entity.City;
import com.lxh.coolweather.db.entity.County;

import java.util.ArrayList;
import java.util.List;

public class CountyDaoUtil extends BaseUtil {

    public List<County> findAllCounties(String sql, String[] selectionArgs) {
        List<County> countiesList = new ArrayList<>();
        Cursor cursor = selectBySql(sql, selectionArgs);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                County county = new County();
                county.setId(cursor.getLong(0));
                county.setCountyName(cursor.getString(1));
                county.setWeatherId(cursor.getString(2));
                county.setCityId(cursor.getLong(3));
                countiesList.add(county);
            }
        }
        return countiesList;
    }
}
