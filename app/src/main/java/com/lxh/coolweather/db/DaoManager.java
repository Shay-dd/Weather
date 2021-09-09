package com.lxh.coolweather.db;


import android.database.sqlite.SQLiteDatabase;

import com.lxh.coolweather.BuildConfig;
import com.lxh.coolweather.MyApplication;
import com.lxh.coolweather.db.gen.DaoMaster;
import com.lxh.coolweather.db.gen.DaoSession;

//创建数据库、数据表以及增删改查
public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();
    private static final String DB_NAME = "ZX_WEATHER_DB";
    private MyApplication myApplication;
    //多线程中要被共享
    private volatile static DaoManager manager = new DaoManager();
    private DaoMaster mDaoMaster;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoSession mDaoSession;

    //单例获取操作数据库的对象
    public static DaoManager getInstance(){
        return manager;
    }

    private DaoManager(){

    }
    public void init(MyApplication application){
        this.myApplication = application;
    }

    //创建数据库
    public DaoMaster getDaoMaster(){
        if (mDaoMaster == null){
            DaoMaster.DevOpenHelper devOpenHelper= new DaoMaster.DevOpenHelper(MyApplication.getContext(), DB_NAME);
            SQLiteDatabase db = devOpenHelper.getWritableDatabase();
            mDaoMaster = new DaoMaster(db);
        }
        return mDaoMaster;
    }

    //完成对数据库的增删改查
    public DaoSession getDaoSession(){
        if (mDaoSession==null){
            if (mDaoMaster==null){
                mDaoMaster = getDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    //关闭数据库操作
    public void closeConnection(){
        closeHelper();
        closeDaoSession();
    }
    public void closeHelper(){
        if (mHelper!=null){
            mHelper.close();
            mHelper=null;
        }
    }
    public void closeDaoSession(){
        if (mDaoSession!=null){
            mDaoSession.clear();
            mDaoSession=null;
        }
    }
}
