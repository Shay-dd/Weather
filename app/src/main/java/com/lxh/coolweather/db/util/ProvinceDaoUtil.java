package com.lxh.coolweather.db.util;

import android.database.Cursor;

import com.lxh.coolweather.db.entity.Province;
import com.lxh.coolweather.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ProvinceDaoUtil extends BaseUtil {

    public List<Province> findAllProvinces(String sql, String[] selectionArgs) {
        List<Province> provinceList = new ArrayList<>();
        Cursor cursor = selectBySql(sql, selectionArgs);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Province province = new Province();
                province.setId(cursor.getLong(0));
                province.setProvinceName(cursor.getString(1));
                province.setProvinceCode(cursor.getInt(2));
                provinceList.add(province);
            }
        }
        return provinceList;
    }

}
