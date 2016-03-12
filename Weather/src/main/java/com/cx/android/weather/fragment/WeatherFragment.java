package com.cx.android.weather.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cx.android.weather.R;
import com.cx.android.weather.layout.IndexLayout;
import com.cx.android.weather.layout.WeatherLaytout;
import com.cx.android.weather.model.Temperature;
import com.cx.android.weather.model.Weather;
import com.cx.android.weather.util.DiskLruCacheUtil;
import com.cx.android.weather.util.ParseWeatherJSON;
import com.cx.android.weather.util.UpdateWeatherCacheUtil;
import com.cx.android.weather.util.UpdateWeatherTask;
import com.cx.android.weather.util.WeatherConstant;

/**
 * 城市天气fragment
 * Created by 陈雪 on 2015/11/2.
 */
public class WeatherFragment extends Fragment{
    private CallBack mCallBack;
    private WeatherLaytout mWeatherLaytout;
    private IndexLayout mIndexLayout;
    private LruCache<String,String> mLruCache;
    private String mCityName;

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

        int cacheSize = 4 * 1024 * 1024; // 4MiB
        mLruCache = new LruCache<String,String>(cacheSize){
            @Override
            public void resize(int maxSize) {
                super.resize(maxSize);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        mWeatherLaytout = (WeatherLaytout) view.findViewById(R.id.weather_layout);
        mIndexLayout = (IndexLayout) view.findViewById(R.id.index_layout);
        mCityName = getArguments().getString(WeatherConstant.TAG_SHARE_CITY);
        updateWeather(mCityName);
        return view;
    }

    /**
     * 先从缓存中获取数据，如果获取不到则从网络获取
     * @param share_city 当前城市名称
     */
    private void updateWeather(final String share_city) {
        DiskLruCacheUtil diskLruCacheUtil = new DiskLruCacheUtil(getActivity().getApplicationContext(),WeatherConstant.DISKCACHE_FORDER);
        final UpdateWeatherCacheUtil cacheUtil = new UpdateWeatherCacheUtil(mLruCache,diskLruCacheUtil);
        Weather mWeather = cacheUtil.getFromCache(share_city);

        if(mWeather == null){
            UpdateWeatherTask updateWeatherTask = new UpdateWeatherTask();
            updateWeatherTask.execute(share_city);
            updateWeatherTask.setmOnWeatherPostLisenter(new UpdateWeatherTask.OnWeatherPostLisenter() {
                @Override
                public void onPost(String weatherJson) {
                    if (weatherJson != null  && !"".equals(weatherJson)) {
                        cacheUtil.setCache(share_city,weatherJson);
                        setWeatherData(ParseWeatherJSON.praseJson(weatherJson));
                    }
                }
            });
        }else{
            setWeatherData(mWeather);
        }
    }

    /**
     * 设置天气数据
     * @param weather 天气
     */
    private void setWeatherData(Weather weather){
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
