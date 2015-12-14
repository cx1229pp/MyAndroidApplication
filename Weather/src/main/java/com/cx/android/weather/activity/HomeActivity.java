package com.cx.android.weather.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cx.android.weather.R;
import com.cx.android.weather.db.WeatherDao;
import com.cx.android.weather.fragment.WeatherFragment;
import com.cx.android.weather.util.WeatherConstant;

import java.util.List;

public class HomeActivity extends FragmentActivity implements WeatherFragment.CallBack , View.OnClickListener{
    private static final int REQUEST_CHOOSE_CITY = 1;
    private static final int REQUEST_MY_CITY = 2;
    private TabLayout mTabLayout;
    private ViewPager mWeatherViewPager;
    private TextView mCityName;
    private TextView mUpdateTime;
    private WeatherDao weatherDao = null;
    private List<String> cityList = null;
    private LinearLayout mHomeLayout;
    private int backgroudImageResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init(){
        mHomeLayout = (LinearLayout) findViewById(R.id.activity_home_layout);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mWeatherViewPager = (ViewPager) findViewById(R.id.weatherPager);
        mCityName = (TextView) findViewById(R.id.activity_home_cityName);
        mCityName.setOnClickListener(this);
        mUpdateTime = (TextView) findViewById(R.id.activity_home_updatetime);
        weatherDao = WeatherDao.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cityList = weatherDao.querySelectCitys();

        if(cityList == null || cityList.size() == 0){
            chooseCity();
        }else{
            mTabLayout.removeAllTabs();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String share_city = sharedPreferences.getString(WeatherFragment.TAG_SHARE_CITY, "");
            if(!"".equals(share_city)) {
                mCityName.setText(share_city);
            }else{
                mCityName.setText(cityList.get(0));
            }

            mWeatherViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return WeatherFragment.newInstance(cityList.get(position));
                }

                @Override
                public int getCount() {
                    return cityList.size();
                }
            });

            mWeatherViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout){
                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    Log.d("HomeActivity",position+"");
                    mCityName.setText(cityList.get(position));
                }
            });

            for(int i=0; i<cityList.size(); i++){
                mTabLayout.addTab(mTabLayout.newTab().setIcon(R.mipmap.btn_tab));
                if(share_city.equals(cityList.get(i))){
                    mWeatherViewPager.setCurrentItem(i);
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mTabLayout.removeAllTabs();
    }

    public void chooseCity() {
        Intent i = new Intent(this,ChooseCityActivity.class);
        startActivityForResult(i, REQUEST_CHOOSE_CITY);
    }
    /*
    @Override
    public void myCity(int backgroudImageResource) {
        Intent i = new Intent(this,MyCityActivity.class);
        //设置天气背景图片
        i.putExtra(WeatherConstant.BACKGROUND_IMAGE_EXTRA,backgroudImageResource);
        startActivityForResult(i, REQUEST_MY_CITY);
    }*/

    @Override
    public void setHomeBackground(int backgroudImageResource) {
        this.backgroudImageResource = backgroudImageResource;
        mHomeLayout.setBackgroundResource(backgroudImageResource);
    }

    @Override
    public void setUpdateTime(String updateTime) {
        mUpdateTime.setText(updateTime);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)return;

        if(requestCode == REQUEST_CHOOSE_CITY){
            String chooseCity = data.getStringExtra(ChooseCityActivity.TAG_CHOOSE_CITY);
            savePreferences(chooseCity);
        }else if(requestCode == REQUEST_MY_CITY){
            String chooseCity = data.getStringExtra(MyCityActivity.TAG_MYCITY_RESULT);
            savePreferences(chooseCity);
        }
    }

    private void savePreferences(String chooseCity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WeatherFragment.TAG_SHARE_CITY,chooseCity);
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_home_cityName :
                Intent i = new Intent(this,MyCityActivity.class);
                //设置天气背景图片
                i.putExtra(WeatherConstant.BACKGROUND_IMAGE_EXTRA,backgroudImageResource);
                startActivityForResult(i, REQUEST_MY_CITY);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weatherDao.closeDB();
    }
}
