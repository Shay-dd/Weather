package com.lxh.coolweather.db.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lxh.coolweather.db.entity.City;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CITY".
*/
public class CityDao extends AbstractDao<City, Long> {

    public static final String TABLENAME = "CITY";

    /**
     * Properties of entity City.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CityName = new Property(1, String.class, "cityName", false, "CITY_NAME");
        public final static Property CityCode = new Property(2, int.class, "cityCode", false, "CITY_CODE");
        public final static Property ProvinceId = new Property(3, Long.class, "provinceId", false, "PROVINCE_ID");
    }


    public CityDao(DaoConfig config) {
        super(config);
    }
    
    public CityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"CITY_NAME\" TEXT," + // 1: cityName
                "\"CITY_CODE\" INTEGER NOT NULL ," + // 2: cityCode
                "\"PROVINCE_ID\" INTEGER);"); // 3: provinceId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, City entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String cityName = entity.getCityName();
        if (cityName != null) {
            stmt.bindString(2, cityName);
        }
        stmt.bindLong(3, entity.getCityCode());
 
        Long provinceId = entity.getProvinceId();
        if (provinceId != null) {
            stmt.bindLong(4, provinceId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, City entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String cityName = entity.getCityName();
        if (cityName != null) {
            stmt.bindString(2, cityName);
        }
        stmt.bindLong(3, entity.getCityCode());
 
        Long provinceId = entity.getProvinceId();
        if (provinceId != null) {
            stmt.bindLong(4, provinceId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public City readEntity(Cursor cursor, int offset) {
        City entity = new City( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // cityName
            cursor.getInt(offset + 2), // cityCode
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // provinceId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, City entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCityName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCityCode(cursor.getInt(offset + 2));
        entity.setProvinceId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(City entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(City entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(City entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
