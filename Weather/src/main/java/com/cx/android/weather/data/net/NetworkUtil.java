package com.cx.android.weather.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络链接工具类
 * Created by chenxue on 2015/7/29.
 */
public class NetworkUtil {
    /**
     * 判断当前网络是否可用
     * @param context
     * @return
     */
    public static boolean networkIsConnect(Context context){
        NetworkInfo networkInfo = getNetworkInfo(context);
        if(networkInfo != null){
            return networkInfo.isAvailable();
        }

        return false;
    }

    /**
     * 判断当前网络是否是WiFi
     * @param context
     * @return
     */
    public static boolean isWiFi(Context context){
        NetworkInfo networkInfo = getNetworkInfo(context);
        if(networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            return true;
        }

        return false;
    }

    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}
