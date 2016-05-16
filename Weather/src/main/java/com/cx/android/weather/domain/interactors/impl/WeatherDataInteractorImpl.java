package com.cx.android.weather.domain.interactors.impl;

import com.cx.android.weather.data.model.Weather;
import com.cx.android.weather.data.repository.WeatherDataRepository;
import com.cx.android.weather.domain.executor.Executor;
import com.cx.android.weather.domain.executor.MainThread;
import com.cx.android.weather.domain.interactors.WeatherDataInteractor;
import com.cx.android.weather.domain.interactors.base.AbstractInteractor;

/**
 * Created by 陈雪 on 2016/3/23.
 */
public class WeatherDataInteractorImpl extends AbstractInteractor implements WeatherDataInteractor{
    private WeatherDataInteractor.Callback mCallback;
    private WeatherDataRepository mWeatherDataRepository;
    private String mCityName;
    private boolean mIsUpdateFromNetwork;

    public WeatherDataInteractorImpl(Executor threadExecutor, MainThread mainThread
    ,WeatherDataInteractor.Callback callback,WeatherDataRepository weatherDataRepository
    ,String cityName,boolean isUpdateFromNetwork) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mWeatherDataRepository = weatherDataRepository;
        mCityName = cityName;
        mIsUpdateFromNetwork = isUpdateFromNetwork;
    }

    private void notifyMessage(final Weather weather){
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onMessageRetrieved(weather);
            }
        });
    }

    private void notifyError(final String error){
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalFailed(error);
            }
        });
    }

    @Override
    public void run() {
        Weather weather;
        if(mIsUpdateFromNetwork){
            weather = mWeatherDataRepository.getWeatherDataFromNet(mCityName);
        }else{
            weather = mWeatherDataRepository.getWeatherDataFromCache(mCityName);
            if(weather == null){
                weather = mWeatherDataRepository.getWeatherDataFromNet(mCityName);
            }
        }

        notifyMessage(weather);
    }
}
