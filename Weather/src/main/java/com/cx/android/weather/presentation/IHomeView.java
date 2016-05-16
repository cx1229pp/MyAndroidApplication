package com.cx.android.weather.presentation;

import java.util.List;

/**
 * 天气预报首页view
 * Created by 陈雪 on 2016/3/14.
 */
public interface IHomeView {
    void chooseCity();

    void setTabCityName(String cityName);

    void tabLayoutAddTab();

    void setCurrentViewPagerItem(int i);

    void setBackgroudImageResource(String cityName);

    void initViewPager(List<String> cityList);

}
