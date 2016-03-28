package com.cx.android.weather.presentation.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import com.cx.android.weather.R;
import com.cx.android.weather.data.model.Temperature;
import com.cx.android.weather.data.model.Weather;
import com.cx.android.weather.domain.executor.Executor;
import com.cx.android.weather.domain.executor.MainThread;
import com.cx.android.weather.domain.executor.impl.ThreadExecutor;
import com.cx.android.weather.presentation.IMyCityFragmentView;
import com.cx.android.weather.presentation.MainThreadImpl;
import com.cx.android.weather.presentation.presenters.MyCityFragmentPresenter;
import com.cx.android.weather.presentation.presenters.WeatherAdapterPresenter;
import com.cx.android.weather.presentation.ui.adapter.WeatherAdapter;
import com.cx.android.weather.presentation.ui.layout.MyCityGridViewItem;
import com.cx.android.weather.util.WeatherConstant;
import java.util.List;

/**
 * 已选城市fragment
 * Created by 陈雪 on 2015/11/12.
 */
public class MyCityFragment extends Fragment implements View.OnClickListener,IMyCityFragmentView{
    private GridView mCityGridView;
    private MyCityCallBack mMyCityCallBack;
    private WeatherAdapter weatherAdapter;
    private MyCityFragmentPresenter mPresenter;
    private WeatherAdapterPresenter mWeatherAdapterPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Executor mExecutor = ThreadExecutor.getInstance();
        MainThread mMainThread = MainThreadImpl.getInstance();
        mPresenter = new MyCityFragmentPresenter(mExecutor, mMainThread,getActivity().getApplicationContext(),
                this);
        mWeatherAdapterPresenter = new WeatherAdapterPresenter(mExecutor, mMainThread,getActivity().getApplicationContext(),
                this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_city, container, false);
        //获取天气背景图片
        int backgroundImageResource = getActivity().getIntent().getIntExtra(WeatherConstant.BACKGROUND_IMAGE_EXTRA,0);
        if(backgroundImageResource != 0){
            view.setBackgroundResource(backgroundImageResource);
        }

        mCityGridView = (GridView) view.findViewById(R.id.gv_choose_city);
        ImageView mEditImageView = (ImageView) view.findViewById(R.id.iv_choose_city_edit);
        mEditImageView.setOnClickListener(this);

        ImageView refreshImage = (ImageView) view.findViewById(R.id.iv_choose_city_refresh);
        refreshImage.setOnClickListener(this);

        ImageView backImage = (ImageView) view.findViewById(R.id.iv_choose_city_back);
        backImage.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.setGridViewAdapter();

        mCityGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.setOnAdapterItemClick(mMyCityCallBack,weatherAdapter,position);
            }
        });

        mCityGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                weatherAdapter.setDeleteImageVisible();
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_choose_city_edit :
                weatherAdapter.setDeleteImageVisible();
                break;
            case R.id.iv_choose_city_refresh :
                weatherAdapter.setIsUpdateFromNetwork(true);
                break;
            case R.id.iv_choose_city_back :
                if(NavUtils.getParentActivityIntent(getActivity()) != null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                break;
        }
    }

    @Override
    public void initGridView(List<String> list) {
        weatherAdapter = new WeatherAdapter(getActivity(),list,mWeatherAdapterPresenter);
        mCityGridView.setAdapter(weatherAdapter);
    }

    @Override
    public void setWeatherData(Weather weather) {
        Temperature temp = weather.getTemperatureList().get(0);
        String cityName = weather.getCurrentCity();
        MyCityGridViewItem item = (MyCityGridViewItem) mCityGridView.findViewWithTag(cityName);
        if(item != null){
            item.getmFreshProgressBar().setVisibility(View.GONE);
            item.getmCityNameText().setText(cityName);
            item.getmWeatherText().setText(temp.getWeather());
            item.getmTemperatureText().setText(temp.getTemperature());
            item.getmWeatherImage().setUrl(temp.getDayPictureUrl());
            item.getmCityNameText().setVisibility(View.VISIBLE);
            item.getmWeatherImage().setVisibility(View.VISIBLE);
            item.getmTemperatureText().setVisibility(View.VISIBLE);
            item.getmWeatherText().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showProgressBar(String cityName) {
    }

    @Override
    public void hideProgressBar() {
    }

    public interface MyCityCallBack{
        void addCity();

        void setResult(String cityName);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMyCityCallBack = (MyCityCallBack) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMyCityCallBack = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
