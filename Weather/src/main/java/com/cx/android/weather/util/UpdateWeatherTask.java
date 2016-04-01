package com.cx.android.weather.util;

import android.os.AsyncTask;
import android.util.Log;

import com.cx.android.weather.data.net.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 根据城市名称异步查询天气类
 * Created by 陈雪 on 2015/11/17.
 */
public class UpdateWeatherTask extends AsyncTask<String,Integer,String> {
    private OnWeatherPostLisenter mOnWeatherPostLisenter;

    @Override
    protected String doInBackground(String... params) {
        String cityName = params[0];
        String weatherJson;

        String url = "";
        try {
            url = WeatherConstant.getWeatherUrl(URLEncoder.encode(cityName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        weatherJson = HttpUtil.requestString(url);
        Log.i("weatherJson", "weatherJson-network:" + cityName);
        return weatherJson;
    }

    @Override
    protected void onPostExecute(String weatherJson) {
        if(mOnWeatherPostLisenter != null){
            mOnWeatherPostLisenter.onPost(weatherJson);
        }
    }

    public interface OnWeatherPostLisenter{
        void onPost(String weatherJson);
    }

    public void setmOnWeatherPostLisenter(OnWeatherPostLisenter mOnWeatherPostLisenter) {
        this.mOnWeatherPostLisenter = mOnWeatherPostLisenter;
    }
}
