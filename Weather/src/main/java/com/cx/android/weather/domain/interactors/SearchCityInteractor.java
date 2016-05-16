package com.cx.android.weather.domain.interactors;

import com.cx.android.weather.data.model.City;

import java.util.List;

/**
 * Created by 陈雪 on 2016/3/30.
 */
public interface SearchCityInteractor {
    interface Callback{
        void onResponse(List<City> list);
        void onErrorResponse(String errorResponse);
    }

    void execute(String cityName);
}
