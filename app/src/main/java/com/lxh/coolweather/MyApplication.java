package com.lxh.coolweather;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lxh.coolweather.db.gen.DaoMaster;
import com.lxh.coolweather.db.gen.DaoSession;

public class MyApplication extends Application {
    private static Context context;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initGreenDao();
    }

    //初始化数据库
    private void initGreenDao(){
        DaoMaster.DevOpenHelper devOpenHelper= new DaoMaster.DevOpenHelper(this,"Weather.db");
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    //获取DaoSession
    public DaoSession getDaoSession(){
        return daoSession;
    }

    //获取Context
    public static Context getContext(){
        return context;
    }

    //获取Application
    public Application getApplication(){
        return this;
    }
}
