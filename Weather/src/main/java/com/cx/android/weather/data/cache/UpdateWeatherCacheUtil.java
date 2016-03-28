package com.cx.android.weather.data.cache;

import android.support.v4.util.LruCache;
import android.util.Log;

import com.cx.android.weather.data.model.Weather;
import com.cx.android.weather.data.model.ParseWeatherJSON;

import java.io.IOException;


/**
 *更新天气缓存工具类
 * Created by 陈雪 on 2015/11/24.
 */
public class UpdateWeatherCacheUtil {
    private LruCache<String,String> mLruCache;
    private DiskLruCacheUtil mDiskLruCacheUtil;

    public UpdateWeatherCacheUtil(LruCache<String, String> mLruCache, DiskLruCacheUtil diskLruCacheUtil) {
        this.mLruCache = mLruCache;
        mDiskLruCacheUtil = diskLruCacheUtil;
    }

    public Weather getFromCache(String cityName){
        String weatherJson = "";
        if(mLruCache.get(cityName) != null){
            weatherJson = mLruCache.get(cityName);
            System.out.println("weatherJson-lrucache:" + cityName);
            System.out.println("weatherJson-lrucache:"+weatherJson);
        }else if(mDiskLruCacheUtil.getStringDiskCache(cityName) != null){
            weatherJson = mDiskLruCacheUtil.getStringDiskCache(cityName);
            System.out.println("weatherJson-diskcache:" + cityName);
            System.out.println("weatherJson-diskcache:"+weatherJson);
        }

        return ParseWeatherJSON.praseJson(weatherJson);
    }

    public void setCache(String cityName,String weatherJson){
        try {
            mLruCache.put(cityName,weatherJson);
            mDiskLruCacheUtil.writeDiskCache(cityName, weatherJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
