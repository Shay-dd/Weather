package com.lxh.coolweather.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class County {
    @Id
    private Long id;
    private String countyName;
    private String weatherId;
    private Long cityId;
    @Generated(hash = 1429542598)
    public County(Long id, String countyName, String weatherId, Long cityId) {
        this.id = id;
        this.countyName = countyName;
        this.weatherId = weatherId;
        this.cityId = cityId;
    }
    @Generated(hash = 1991272252)
    public County() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCountyName() {
        return this.countyName;
    }
    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
    public String getWeatherId() {
        return this.weatherId;
    }
    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
    public Long getCityId() {
        return this.cityId;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }


}
