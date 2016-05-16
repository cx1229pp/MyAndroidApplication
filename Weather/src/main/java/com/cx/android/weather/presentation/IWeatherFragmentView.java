package com.cx.android.weather.presentation;

import com.cx.android.weather.data.model.Weather;

/**
 * 天气预报首页fragment view
 * Created by 陈雪 on 2016/3/15.
 */
public interface IWeatherFragmentView {
    void setWeatherData(Weather weather);
}
