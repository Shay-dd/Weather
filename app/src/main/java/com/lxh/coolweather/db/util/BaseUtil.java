package com.lxh.coolweather.db.util;

import android.database.Cursor;

import com.lxh.coolweather.db.DaoManager;
import com.lxh.coolweather.db.gen.DaoSession;
import com.lxh.coolweather.util.LogUtil;

public class BaseUtil {

    //插入
    public void addEntity(Object entity){
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        daoSession.insert(entity);
    }

    //删除
    public void deleteEntity(Object entity){
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        daoSession.delete(entity);
    }

    //更新
    public void updateEntity(Object entity){
        DaoSession daoSession = DaoManager.getInstance().getDaoSession();
        daoSession.update(entity);
    }

    //通过SQL查找
    public Cursor selectBySql(String sql,String[] selectionArgs){
        Cursor cursor = null;
        try {
            DaoSession daoSession = DaoManager.getInstance().getDaoSession();
            cursor = daoSession.getDatabase().rawQuery(sql,selectionArgs);
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.e("SELECT","selectBySql ERROR!!");
            return null;
        }
        return cursor;
    }

}
