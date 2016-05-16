package com.cx.android.weather.data.repository.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.LruCache;

import com.cx.android.weather.data.cache.DiskLruCacheUtil;
import com.cx.android.weather.data.cache.LruCacheUtil;
import com.cx.android.weather.data.cache.UpdateWeatherCacheUtil;
import com.cx.android.weather.data.db.SharedPreferencesUtil;
import com.cx.android.weather.data.db.WeatherDao;
import com.cx.android.weather.data.model.Weather;
import com.cx.android.weather.data.net.GetWeatherDataUtil;
import com.cx.android.weather.data.repository.WeatherDataRepository;
import com.cx.android.weather.data.model.ParseWeatherJSON;
import com.cx.android.weather.util.UpdateWeatherTask;
import com.cx.android.weather.util.WeatherConstant;

import java.util.List;

/**
 * Created by 陈雪 on 2016/3/22.
 */
public class WeatherDataRepositoryImpl implements WeatherDataRepository{
    private Context mContext;
    private WeatherDao weatherDao = null;
    private DiskLruCacheUtil diskLruCacheUtil;
    private LruCache mLruCache;

    public WeatherDataRepositoryImpl(Context mContext) {
        this.mContext = mContext;
        diskLruCacheUtil = new DiskLruCacheUtil(mContext, WeatherConstant.DISKCACHE_FORDER);
        mLruCache = LruCacheUtil.getInstance();
    }

    @Override
    public List<String> querySelectdCitys() {
        weatherDao = WeatherDao.getInstance(mContext);
        return weatherDao.querySelectCitys();
    }

    @Override
    public void deleteCity(String cityName) {
        weatherDao = WeatherDao.getInstance(mContext);
        weatherDao.deleteSelectCity(cityName);
    }

    @Override
    public void addCity(String cityName) {
        weatherDao = WeatherDao.getInstance(mContext);
        weatherDao.addSelectCity("",cityName);
    }

    @Override
    public String getCurrentCity() {
        //查询当前选中城市，如果没有则默认选中已选城市列表第一个
        SharedPreferences sharedPreferences = SharedPreferencesUtil.getInstance(mContext);
        return sharedPreferences.getString(WeatherConstant.TAG_SHARE_CITY, "");
    }

    @Override
    public void saveCurrentCity(String cityName) {
        SharedPreferences sharedPreferences = SharedPreferencesUtil.getInstance(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WeatherConstant.TAG_SHARE_CITY,cityName);
        editor.apply();
    }

    @Override
    public boolean isSelectedCity(String cityName) {
        weatherDao = WeatherDao.getInstance(mContext);
        return weatherDao.isSelectCity(cityName);
    }

    @Override
    public Weather getWeatherDataFromCache(String cityName) {
        UpdateWeatherCacheUtil cacheUtil = new UpdateWeatherCacheUtil(mLruCache,diskLruCacheUtil);
        return cacheUtil.getFromCache(cityName);
    }

    @Override
    public Weather getWeatherDataFromNet(String cityName) {
        String weatherJson = GetWeatherDataUtil.getWeatherData(cityName);
        UpdateWeatherCacheUtil cacheUtil = new UpdateWeatherCacheUtil(mLruCache,diskLruCacheUtil);
        cacheUtil.setCache(cityName,weatherJson);

        return ParseWeatherJSON.praseJson(weatherJson);
    }
}
