package com.cx.android.weather.data.repository;

import com.cx.android.weather.data.model.Weather;

import java.util.List;

/**
 * Created by 陈雪 on 2016/3/22.
 */
public interface WeatherDataRepository {
    /**
     * 从数据库查询已选择城市列表
     * @return
     */
    List<String> querySelectdCitys();

    /**
     * 从数据库删除已选城市
     * @param cityName
     */
    void deleteCity(String cityName);

    /**
     * 新增城市到数据库
     * @param cityName
     */
    void addCity(String cityName);

    /**
     * 从SharedPfreferences获取当前指定城市
     * @return
     */
    String getCurrentCity();

    /**
     * 保存指定城市到SharedPreferences
     * @param cityName
     */
    void saveCurrentCity(String cityName);

    /**
     * 从缓存或网络获取指定城市天气数据
     * @param cityName
     * @return
     */
    Weather getWeatherDataFromCache(String cityName);

    Weather getWeatherDataFromNet(String cityName);

    boolean isSelectedCity(String cityName);
}
