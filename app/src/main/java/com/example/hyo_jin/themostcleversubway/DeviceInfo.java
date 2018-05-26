package com.example.hyo_jin.themostcleversubway;

/**
 * Created by Hyo-Jin on 2018-05-21.
 */

public class DeviceInfo {

    String mobile;
    String osVersion;
    String model;
    String display;
    String manufacturer;
    String macAddress;

    public DeviceInfo(String mobile, String osVersion, String model, String display, String manufacturer, String macAddress) {
        this.mobile = mobile;
        this.osVersion = osVersion;
        this.model = model;
        this.display = display;
        this.manufacturer = manufacturer;
        this.macAddress = macAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getManufacture() {
        return manufacturer;
    }

    public void setManufacture(String manufacture) {
        this.manufacturer = manufacture;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
