package com.cx.android.weather.data.net;

import android.util.Log;

import com.cx.android.weather.util.WeatherConstant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by 陈雪 on 2016/3/22.
 */
public class GetWeatherDataUtil {

    public static String getWeatherData(String cityName){
        String url = "";
        try {
            url = WeatherConstant.getWeatherUrl(URLEncoder.encode(cityName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("weatherJson", "weatherJson-network:" + cityName);
        System.out.println("weatherJson-network:" + cityName);
        return HttpUtil.requestString(url);
    }
}
