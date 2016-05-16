package com.cx.android.weather.presentation.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import com.cx.android.weather.data.db.SharedPreferencesUtil;
import com.cx.android.weather.data.db.WeatherDao;
import com.cx.android.weather.util.WeatherConstant;
import com.cx.android.weather.presentation.IHomeView;
import java.util.List;

/**
 * 天气预报首页presenter
 * Created by 陈雪 on 2016/3/14.
 */
public class HomePresenter {
    private IHomeView homeActivity;
    private Context context;
    private WeatherDao weatherDao = null;
    //已选城市list
    private List<String> cityList = null;
    //当前城市名称
    private String share_city;

    public HomePresenter(IHomeView homeActivity,Context context){
        this.homeActivity = homeActivity;
        this.context = context;
    }

    public void onViewStart(){
        cityList = weatherDao.querySelectCitys();
        //如果没有已选城市，则跳转城市查询页面
        if(cityList == null || cityList.size() == 0){
           homeActivity.chooseCity();
        }else{
            //查询当前选中城市，如果没有则默认选中已选城市列表第一个
            SharedPreferences sharedPreferences = SharedPreferencesUtil.getInstance(context);
            share_city = sharedPreferences.getString(WeatherConstant.TAG_SHARE_CITY, "");
            if(!"".equals(share_city)) {
                homeActivity.setTabCityName(share_city);
            }else{
                homeActivity.setTabCityName(cityList.get(0));
            }

            homeActivity.initViewPager(cityList);

            //设置tabLayout
            for(int i=0; i<cityList.size(); i++){
                homeActivity.tabLayoutAddTab();
                if(share_city.equals(cityList.get(i))){
                    homeActivity.setCurrentViewPagerItem(i);
                }
            }
        }
    }

    public void instanceDao(){
        weatherDao = WeatherDao.getInstance(context);
    }

    public void closeDao(){
        weatherDao.closeDB();
    }

    public String getShareCity(){
        return share_city;
    }

    /**
     * 保存当前选中城市
     * @param chooseCity
     */
    public void savePreferences(String chooseCity) {
        SharedPreferences sharedPreferences = SharedPreferencesUtil.getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WeatherConstant.TAG_SHARE_CITY,chooseCity);
        editor.apply();
    }

    public void viewPagerSelected(int position){
        String cityNameString = cityList.get(position);
        homeActivity.setTabCityName(cityNameString);
        homeActivity.setBackgroudImageResource(cityNameString);
        savePreferences(cityNameString);
    }
}
