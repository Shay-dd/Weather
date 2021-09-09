package com.lxh.coolweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.lxh.coolweather.db.entity.City;
import com.lxh.coolweather.db.entity.County;
import com.lxh.coolweather.db.entity.Province;
import com.lxh.coolweather.db.util.BaseUtil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    private static BaseUtil baseUtil = new BaseUtil();
    //处理省级返回数据
    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0;i<allProvinces.length();i++){
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    Log.e("====",province.getProvinceName());
                    //插入数据库
                    baseUtil.addEntity(province);
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //处理市级返回数据
    public static boolean handleCityResponse(String response,long provinceId){
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allCities = new JSONArray(response);
                for (int i = 0;i<allCities.length();i++){
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    //插入数据库
                    baseUtil.addEntity(city);
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //处理县级返回数据
    public static boolean handleCountyResponse(String response,long cityId){
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0;i<allCounties.length();i++){
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    //插入数据库
                    baseUtil.addEntity(county);
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
