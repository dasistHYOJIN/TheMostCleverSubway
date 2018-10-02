package com.example.hyo_jin.themostcleversubway.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Hyo-Jin on 2018-09-24.
 */

public class UserInfo {
    private String userid;
    private String osVersion;
    private String model;
    private String display;
    private String manufacturer;
    private String created_at;
    private String updated_at;

    public UserInfo(String userid, String osVersion, String model, String display, String manufacturer, String created_at, String updated_at) {
        this.userid = userid;
        this.osVersion = osVersion;
        this.model = model;
        this.display = display;
        this.manufacturer = manufacturer;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public UserInfo(String userid, String osVersion, String model, String display, String manufacturer) {
        this.userid = userid;
        this.osVersion = osVersion;
        this.model = model;
        this.display = display;
        this.manufacturer = manufacturer;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date c_date) {
        TimeZone tz;
        Date date = c_date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        tz = TimeZone.getTimeZone("Asia/Seoul");
        simpleDateFormat.setTimeZone(tz);

        this.updated_at = simpleDateFormat.format(date);
    }
}