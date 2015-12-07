package com.cx.android.weather.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.cx.android.weather.R;
import com.cx.android.weather.adapter.WeatherAdapter;
import com.cx.android.weather.db.WeatherDao;
import com.cx.android.weather.model.Weather;
import com.cx.android.weather.util.WeatherConstant;

import java.util.List;

/**
 * Created by 陈雪 on 2015/11/12.
 */
public class MyCityFragment extends Fragment implements View.OnClickListener{
    private GridView mCityGridView;
    private MyCityCallBack mMyCityCallBack;
    private WeatherAdapter weatherAdapter;
    private List<String> list = null;
    private WeatherDao weatherDao = null;
    private LruCache<String,String> mLruCache;

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
        View view = inflater.inflate(R.layout.fragment_my_city, container, false);
        //获取天气背景图片
        int backgroundImageResource = getActivity().getIntent().getIntExtra(WeatherConstant.BACKGROUND_IMAGE_EXTRA,0);
        if(backgroundImageResource != 0){
            Log.d("backgroundImageResource","0:"+backgroundImageResource);
            view.setBackgroundResource(backgroundImageResource);
        }

        mCityGridView = (GridView) view.findViewById(R.id.gv_choose_city);
        ImageView mAddCityImage = (ImageView) view.findViewById(R.id.iv_add_city);
        mAddCityImage.setOnClickListener(this);
        ImageView mEditImageView = (ImageView) view.findViewById(R.id.iv_choose_city_edit);
        mEditImageView.setOnClickListener(this);
        weatherDao = WeatherDao.getInstance(getActivity());

        ImageView refreshImage = (ImageView) view.findViewById(R.id.iv_choose_city_refresh);
        refreshImage.setOnClickListener(this);

        ImageView backImage = (ImageView) view.findViewById(R.id.iv_choose_city_back);
        backImage.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setGridViewAdapter();
        mCityGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityName = list.get(position);
                if(weatherAdapter.getDeleteImageVisible()){
                    weatherDao.deleteSelectCity(cityName);
                    setGridViewAdapter();
                }else{
                    mMyCityCallBack.setResult(cityName);
                }
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

    private void setGridViewAdapter() {
        list = weatherDao.querySelectCitys();
        weatherAdapter = new WeatherAdapter(getActivity(),list,mLruCache);
        mCityGridView.setAdapter(weatherAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_add_city :
                mMyCityCallBack.addCity();
                break;
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

}
