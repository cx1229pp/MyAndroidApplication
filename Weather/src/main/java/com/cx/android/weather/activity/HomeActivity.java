package com.cx.android.weather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.cx.android.weather.R;
import com.cx.android.weather.db.SharedPreferencesUtil;
import com.cx.android.weather.db.WeatherDao;
import com.cx.android.weather.fragment.WeatherFragment;
import com.cx.android.weather.util.WeatherConstant;

import java.util.List;

/**
 *天气预报home页
 */
public class HomeActivity extends FragmentActivity implements WeatherFragment.CallBack , View.OnClickListener{
    //选择未选城市请求code
    private static final int REQUEST_CHOOSE_CITY = 1;
    //选择已选城市请求code
    private static final int REQUEST_MY_CITY = 2;
    private TabLayout mTabLayout;
    private ViewPager mWeatherViewPager;
    private TextView mCityName;
    private TextView mUpdateTime;
    private WeatherDao weatherDao = null;
    //已选城市list
    private List<String> cityList = null;
    private LinearLayout mHomeLayout;
    //当前天气背景图片Id
    private int backgroudImageResource;
    //已选城市天气背景图片map
    private ArrayMap<String,Integer> mWeatherBackgroungMap = new ArrayMap<>();
    //当前城市名称
    private String share_city;

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
        //查询已选城市list
        cityList = weatherDao.querySelectCitys();

        //如果没有选择城市则跳转到城市查询页面
        if(cityList == null || cityList.size() == 0){
            chooseCity();
        }else{
            mTabLayout.removeAllTabs();

            //查询当前选中城市，如果没有则默认选中已选城市列表第一个
            SharedPreferences sharedPreferences = SharedPreferencesUtil.getInstance(this);
            share_city = sharedPreferences.getString(WeatherConstant.TAG_SHARE_CITY, "");
            if(!"".equals(share_city)) {
                mCityName.setText(share_city);
            }else{
                mCityName.setText(cityList.get(0));
            }

            /**
             * 设置viewPager
             */
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

            /**
             * 设置viewPager滚动监听
             */
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
                    String cityNameString = cityList.get(position);
                    mCityName.setText(cityNameString);
                    setBackgroudImageResource(cityNameString);
                    savePreferences(cityNameString);
                }
            });

            //设置tabLayout
            for(int i=0; i<cityList.size(); i++){
                mTabLayout.addTab(mTabLayout.newTab().setIcon(R.mipmap.btn_tab));
                if(share_city.equals(cityList.get(i))){
                    mWeatherViewPager.setCurrentItem(i);
                }
            }
        }
    }

    /**
     * 设置当前城市天气背景图片
     * @param cityName
     */
    private void setBackgroudImageResource(String cityName){
        if(mWeatherBackgroungMap.size() > 0){
            backgroudImageResource = mWeatherBackgroungMap.get(cityName);
            mHomeLayout.setBackgroundResource(backgroudImageResource);
            savePreferences(cityName);
        }
    }

    /**
     * 启动城市查询页面
     */
    private void chooseCity() {
        Intent i = new Intent(this,ChooseCityActivity.class);
        startActivityForResult(i, REQUEST_CHOOSE_CITY);
    }

    /**
     * 设置天气背景图片map回调函数
     * @param cityName
     * @param backgroudImageResource
     */
    @Override
    public void setHomeBackground(String cityName,int backgroudImageResource) {
        mWeatherBackgroungMap.put(cityName,backgroudImageResource);
        if(share_city.equals(cityName)){
            setBackgroudImageResource(cityName);
        }
    }

    /**
     * 设置更新时间回调函数
     * @param updateTime
     */
    @Override
    public void setUpdateTime(String updateTime) {
        mUpdateTime.setText(updateTime);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)return;

        if(requestCode == REQUEST_CHOOSE_CITY){
            String chooseCity = data.getStringExtra(WeatherConstant.TAG_CHOOSE_CITY);
            savePreferences(chooseCity);
        }else if(requestCode == REQUEST_MY_CITY){
            String chooseCity = data.getStringExtra(WeatherConstant.TAG_MYCITY_RESULT);
            savePreferences(chooseCity);
        }
    }

    /**
     * 保存当前选中城市
     * @param chooseCity
     */
    private void savePreferences(String chooseCity) {
        SharedPreferences sharedPreferences = SharedPreferencesUtil.getInstance(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WeatherConstant.TAG_SHARE_CITY,chooseCity);
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
