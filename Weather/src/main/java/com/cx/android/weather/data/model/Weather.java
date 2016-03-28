package com.cx.android.weather.data.model;

import java.util.List;

/**
 * Created by chenxue on 2015/5/8.
 */
public class Weather {
    private String currentCity;
    private String pm25;
    private String date;
    private List<WeatherIndex> indexList;
    private List<Temperature> temperatureList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public List<WeatherIndex> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<WeatherIndex> indexList) {
        this.indexList = indexList;
    }

    public List<Temperature> getTemperatureList() {
        return temperatureList;
    }

    public void setTemperatureList(List<Temperature> temperatureList) {
        this.temperatureList = temperatureList;
    }
}
