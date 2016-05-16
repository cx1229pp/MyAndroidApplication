package com.cx.android.weather.data.cache;

import android.support.v4.util.LruCache;

/**
 * Created by 陈雪 on 2016/3/22.
 */
public class LruCacheUtil {
    private static LruCache<Object,Object> lruCache;

    private LruCacheUtil(){}

    public synchronized static LruCache getInstance(){
        if(lruCache == null){
            int cacheSize = 4 * 1024 * 1024; // 4MiB
            lruCache = new LruCache<Object,Object>(cacheSize){
                @Override
                public void resize(int maxSize) {
                    super.resize(maxSize);
                }
            };
        }

        return lruCache;
    }
}
