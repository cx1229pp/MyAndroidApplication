package com.cx.android.weather.presentation.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cx.android.weather.R;
import com.cx.android.weather.domain.executor.Executor;
import com.cx.android.weather.domain.executor.MainThread;
import com.cx.android.weather.domain.executor.impl.ThreadExecutor;
import com.cx.android.weather.presentation.MainThreadImpl;
import com.cx.android.weather.presentation.ui.layout.IndexLayout;
import com.cx.android.weather.presentation.ui.layout.WeatherLaytout;
import com.cx.android.weather.data.model.Temperature;
import com.cx.android.weather.data.model.Weather;
import com.cx.android.weather.presentation.presenters.WeatherFragmentPresenter;
import com.cx.android.weather.util.WeatherConstant;
import com.cx.android.weather.presentation.IWeatherFragmentView;

/**
 * 城市天气fragment
 * Created by 陈雪 on 2015/11/2.
 */
public class WeatherFragment extends Fragment implements IWeatherFragmentView{
    private CallBack mCallBack;
    private WeatherLaytout mWeatherLaytout;
    private IndexLayout mIndexLayout;
    private String mCityName;
    private WeatherFragmentPresenter weatherFragmentPresenter;

    /**
     * 实例化方法
     * @param cityName 城市名称
     * @return
     */
    public static WeatherFragment newInstance(String cityName){
        Bundle args = new Bundle();
        args.putString(WeatherConstant.TAG_SHARE_CITY,cityName);

        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置保留实例
        setRetainInstance(true);

        Executor executor = ThreadExecutor.getInstance();
        MainThread mainThread = MainThreadImpl.getInstance();
        weatherFragmentPresenter = new WeatherFragmentPresenter(executor,mainThread
                ,getActivity().getApplicationContext(),this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        mWeatherLaytout = (WeatherLaytout) view.findViewById(R.id.weather_layout);
        mIndexLayout = (IndexLayout) view.findViewById(R.id.index_layout);
        mCityName = getArguments().getString(WeatherConstant.TAG_SHARE_CITY);
        weatherFragmentPresenter.updateWeather(mCityName);
        return view;
    }

    /**
     * 设置天气数据
     * @param weather 天气
     */
    public void setWeatherData(Weather weather){
        mWeatherLaytout.setData(weather);
        mIndexLayout.setData(weather);

        Temperature temp = weather.getTemperatureList().get(0);
        int backgroundImageResource = WeatherConstant.getWeatherBG(temp.getWeather());
        mCallBack.setHomeBackground(mCityName,backgroundImageResource);
        mCallBack.setUpdateTime(weather.getDate());
    }

    /**
     * 回调函数
     */
    public interface CallBack{
        /**
         * 设置天气背景图片
         * @param cityName
         * @param backgroudImageResource
         */
        void setHomeBackground(String cityName,int backgroudImageResource);

        /**
         * 设置天气更新时间
         * @param updateTime
         */
        void setUpdateTime(String updateTime);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBack = (CallBack) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBack = null;
    }
}
