package com.cx.android.weather.data.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by 陈雪 on 2016/3/8.
 */
public class SharedPreferencesUtil {
    private static SharedPreferences sp;
    private SharedPreferencesUtil(){}

    public synchronized static SharedPreferences getInstance(Context context){
        if(sp == null){
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return sp;
    }
}
