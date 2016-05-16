package com.cx.android.weather.presentation;

import com.cx.android.weather.data.model.Weather;

import java.util.List;

/**
 * Created by 陈雪 on 2016/3/25.
 */
public interface IMyCityFragmentView {
    void initGridView(List<String> list);

    void setWeatherData(Weather weather);

    void showProgressBar(String cityName);

    void hideProgressBar();
}
