package ru.saoworld.sm.swortmasters.bin.bean;


public class City {
    private String cityName;
    private int cityId;

    public void setCityName(String name) {
        this.cityName = name;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCityId() {
        return this.cityId;
    }
}