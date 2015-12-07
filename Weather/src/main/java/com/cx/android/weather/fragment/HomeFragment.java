package com.cx.android.weather.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * Created by 陈雪 on 2015/11/2.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    public static final String TAG_SHARE_CITY = "tag_share_city";
    private CallBack mCallBack;
    private TextView mCityName;
    private TextView mUpdateTime;
    private WeatherLaytout mWeatherLaytout;
    private IndexLayout mIndexLayout;
    private View view;
    private LruCache<String,String> mLruCache;
    private int backgroundImageResource;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        view = inflater.inflate(R.layout.fragment_weather,container,false);
        mCityName = (TextView) view.findViewById(R.id.tv_city);
        mCityName.setOnClickListener(this);
        mUpdateTime = (TextView) view.findViewById(R.id.tv_updateTime);
        mWeatherLaytout = (WeatherLaytout) view.findViewById(R.id.weather_layout);
        mIndexLayout = (IndexLayout) view.findViewById(R.id.index_layout);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String share_city = sharedPreferences.getString(TAG_SHARE_CITY, "");
        if(!"".equals(share_city)) {
            mCityName.setText(share_city);
            updateWeather(share_city);
        }else{
            mCallBack.chooseCity();
        }
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
        mUpdateTime.setText(weather.getDate());
        mWeatherLaytout.setData(weather);
        mIndexLayout.setData(weather);

        Temperature temp = weather.getTemperatureList().get(0);
        backgroundImageResource = WeatherConstant.getWeatherBG(temp.getWeather());
        view.setBackgroundResource(backgroundImageResource);
    }

    private void disableView(){
        mUpdateTime.setVisibility(View.GONE);
        mWeatherLaytout.setVisibility(View.GONE);
        mIndexLayout.setVisibility(View.GONE);
    }

    private void enableView(){
        mUpdateTime.setVisibility(View.VISIBLE);
        mWeatherLaytout.setVisibility(View.VISIBLE);
        mIndexLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_city :
                mCallBack.myCity(backgroundImageResource);
                break;
        }
    }

    public interface CallBack{
        void chooseCity();
        void myCity(int backgroudImageResource);
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
