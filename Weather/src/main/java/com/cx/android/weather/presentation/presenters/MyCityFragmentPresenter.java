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
import com.cx.android.weather.presentation.IWeatherAdapterView;
import com.cx.android.weather.presentation.presenters.base.AbstractPresenter;
import com.cx.android.weather.presentation.presenters.base.BasePresenter;
import com.cx.android.weather.presentation.ui.adapter.WeatherAdapter;
import com.cx.android.weather.presentation.ui.fragment.MyCityFragment;
import com.cx.android.weather.util.WeatherConstant;

import java.util.List;

/**
 * Created by 陈雪 on 2016/3/24.
 */
public class MyCityFragmentPresenter extends AbstractPresenter
        implements BasePresenter,WeatherDataInteractor.Callback {

    private Executor mExecutor;
    private MainThread mMainThread;
    private Context mContext;
    private IMyCityFragmentView mMyCityFragmentView;
    private WeatherDataRepository repository;
    private List<String> list;

    public MyCityFragmentPresenter(Executor executor, MainThread mainThread,Context context
    ,IMyCityFragmentView myCityFragmentView) {
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

    }

    @Override
    public void onRetrievalFailed(String error) {

    }

    public void setGridViewAdapter(){
        list = repository.querySelectdCitys();
        if(list != null && list.size() > 0){
            mMyCityFragmentView.initGridView(list);
        }
    }

    public void setOnAdapterItemClick(MyCityFragment.MyCityCallBack callBack
            ,WeatherAdapter adapter,int position){
        String cityName = list.get(position);
        if(WeatherConstant.MY_CITY_FRAGMENT_ADDIMAGE.equals(cityName)){
            callBack.addCity();
        }else if(adapter.getDeleteImageVisible()){
            repository.deleteCity(cityName);
            setGridViewAdapter();
        }else{
            callBack.setResult(cityName);
        }
    }
}
