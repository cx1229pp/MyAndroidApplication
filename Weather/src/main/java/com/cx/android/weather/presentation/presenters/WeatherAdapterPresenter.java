package com.cx.android.weather.presentation.presenters;

import android.content.Context;

import com.cx.android.weather.data.model.Weather;
import com.cx.android.weather.data.repository.WeatherDataRepository;
import com.cx.android.weather.data.repository.impl.WeatherDataRepositoryImpl;
import com.cx.android.weather.domain.executor.Executor;
import com.cx.android.weather.domain.executor.MainThread;
import com.cx.android.weather.domain.interactors.WeatherDataInteractor;
import com.cx.android.weather.domain.interactors.impl.WeatherDataInteractorImpl;
import com.cx.android.weather.presentation.IMyCityFragmentView;
import com.cx.android.weather.presentation.presenters.base.AbstractPresenter;
import com.cx.android.weather.presentation.presenters.base.BasePresenter;

/**
 * Created by 陈雪 on 2016/3/27.
 */
public class WeatherAdapterPresenter extends AbstractPresenter
        implements BasePresenter,WeatherDataInteractor.Callback {

    private Executor mExecutor;
    private MainThread mMainThread;
    private Context mContext;
    private IMyCityFragmentView mMyCityFragmentView;
    private WeatherDataRepository repository;

    public WeatherAdapterPresenter(Executor executor, MainThread mainThread
                                   ,Context context, IMyCityFragmentView myCityFragmentView) {
        super(executor, mainThread);
        mExecutor = executor;
        mMainThread = mainThread;
        mContext = context;
        mMyCityFragmentView = myCityFragmentView;
        repository = new WeatherDataRepositoryImpl(mContext);
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
        mMyCityFragmentView.setWeatherData(weather);
    }

    @Override
    public void onRetrievalFailed(String error) {

    }

    public void updateAdapterWeatherData(String cityName,boolean isUpdateFromNetwork){
        WeatherDataInteractor interactor = new WeatherDataInteractorImpl(mExecutor,mMainThread,this
                ,repository,cityName,isUpdateFromNetwork);
        interactor.execute();
    }
}
