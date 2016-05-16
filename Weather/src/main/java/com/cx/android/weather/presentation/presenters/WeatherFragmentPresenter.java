package com.cx.android.weather.presentation.presenters;

import android.content.Context;

import com.cx.android.weather.data.model.Weather;
import com.cx.android.weather.data.repository.WeatherDataRepository;
import com.cx.android.weather.data.repository.impl.WeatherDataRepositoryImpl;
import com.cx.android.weather.domain.executor.Executor;
import com.cx.android.weather.domain.executor.MainThread;
import com.cx.android.weather.domain.interactors.WeatherDataInteractor;
import com.cx.android.weather.domain.interactors.impl.WeatherDataInteractorImpl;
import com.cx.android.weather.presentation.IWeatherFragmentView;
import com.cx.android.weather.presentation.presenters.base.AbstractPresenter;
import com.cx.android.weather.presentation.presenters.base.BasePresenter;

/**
 * 天气首页fragment presenter
 * Created by 陈雪 on 2016/3/15.
 */
public class WeatherFragmentPresenter extends AbstractPresenter
        implements BasePresenter,WeatherDataInteractor.Callback{
    private IWeatherFragmentView weatherFragment;
    private Executor mExecutor;
    private MainThread mMainThread;
    private Context mContext;

    public WeatherFragmentPresenter(Executor executor, MainThread mainThread,Context context,IWeatherFragmentView view) {
        super(executor, mainThread);
        mExecutor = executor;
        mMainThread = mainThread;
        mContext = context;
        weatherFragment = view;
    }

    /**
     * 先从缓存中获取数据，如果获取不到则从网络获取
     * @param share_city 当前城市名称
     */
    public void updateWeather(String share_city,boolean isUpdateFromNetWork) {
        WeatherDataRepository repository = new WeatherDataRepositoryImpl(mContext);
        WeatherDataInteractor interactor = new WeatherDataInteractorImpl(mExecutor,mMainThread,this,repository,share_city,isUpdateFromNetWork);
        interactor.execute();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onMessageRetrieved(Weather weather) {
        weatherFragment.setWeatherData(weather);
    }

    @Override
    public void onRetrievalFailed(String error) {

    }
}
