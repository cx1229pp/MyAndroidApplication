package com.cx.android.weather.presentation.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cx.android.weather.R;
import com.cx.android.weather.presentation.ui.fragment.WeatherFragment;
import com.cx.android.weather.presentation.presenters.HomePresenter;
import com.cx.android.weather.util.WeatherConstant;
import com.cx.android.weather.presentation.IHomeView;
import java.util.List;

/**
 *天气预报home页
 */
public class HomeActivity extends FragmentActivity implements WeatherFragment.CallBack
        , View.OnClickListener,IHomeView{
    //选择未选城市页面请求code
    private static final int REQUEST_CHOOSE_CITY = 1;
    //选择已选城市页面请求code
    private static final int REQUEST_MY_CITY = 2;
    private TabLayout mTabLayout;
    private ViewPager mWeatherViewPager;
    private TextView mCityName;
    //private TextView mUpdateTime;
    private LinearLayout mHomeLayout;
    //当前天气背景图片Id
    private int backgroudImageResource;
    //已选城市天气背景图片map
    private ArrayMap<String,Integer> mWeatherBackgroungMap = new ArrayMap<>();
    private HomePresenter homePresenter;

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
        homePresenter = new HomePresenter(this,this);
        homePresenter.instanceDao();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTabLayout.removeAllTabs();
        homePresenter.onViewStart();
    }

    /**
     * 设置当前城市天气背景图片
     * @param cityName
     */
    public void setBackgroudImageResource(String cityName){
        if(mWeatherBackgroungMap != null && mWeatherBackgroungMap.size() > 0){
            if(mWeatherBackgroungMap.get(cityName) != null) {
                backgroudImageResource = mWeatherBackgroungMap.get(cityName);
                mHomeLayout.setBackgroundResource(backgroudImageResource);
            }
            homePresenter.savePreferences(cityName);
        }
    }

    @Override
    public void initViewPager(final List<String> cityList) {
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
                homePresenter.viewPagerSelected(position);
            }
        });
    }

    /**
     * 启动城市查询页面
     */
    public void chooseCity() {
        Intent i = new Intent(this,ChooseCityActivity.class);
        startActivityForResult(i, REQUEST_CHOOSE_CITY);
    }

    @Override
    public void setTabCityName(String cityName) {
        mCityName.setText(cityName);
    }

    @Override
    public void tabLayoutAddTab() {
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.shape_weather_tab));
    }

    @Override
    public void setCurrentViewPagerItem(int i) {
        mWeatherViewPager.setCurrentItem(i);
    }

    /**
     * 设置天气背景图片map回调函数
     * @param cityName
     * @param backgroudImageResource
     */
    @Override
    public void setHomeBackground(String cityName,int backgroudImageResource) {
        mWeatherBackgroungMap.put(cityName,backgroudImageResource);
        if(homePresenter.getShareCity().equals(cityName)){
            setBackgroudImageResource(cityName);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)return;

        if(requestCode == REQUEST_CHOOSE_CITY){
            String chooseCity = data.getStringExtra(WeatherConstant.TAG_CHOOSE_CITY);
            homePresenter.savePreferences(chooseCity);
        }else if(requestCode == REQUEST_MY_CITY){
            String chooseCity = data.getStringExtra(WeatherConstant.TAG_MYCITY_RESULT);
            homePresenter.savePreferences(chooseCity);
        }
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
        homePresenter.closeDao();
    }
}
