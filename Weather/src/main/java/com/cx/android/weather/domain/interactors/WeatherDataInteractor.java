package com.cx.android.weather.domain.interactors;

import com.cx.android.weather.data.model.Weather;
import com.cx.android.weather.domain.interactors.base.Interactor;

/**
 * Created by 陈雪 on 2016/3/23.
 */
public interface WeatherDataInteractor extends Interactor{
    interface Callback{
        void onMessageRetrieved(Weather weather);
        void onRetrievalFailed(String error);
    }
}
