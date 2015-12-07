package com.cx.android.weather.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import com.cx.android.weather.fragment.HomeFragment;
import com.cx.android.weather.util.WeatherConstant;

public class HomeActivity extends BaseActivity implements HomeFragment.CallBack{
    private static final int REQUEST_CHOOSE_CITY = 1;
    private static final int REQUEST_MY_CITY = 2;

    @Override
    protected Fragment createFragment() {
        return new HomeFragment();
    }

    @Override
    public void chooseCity() {
        Intent i = new Intent(this,ChooseCityActivity.class);
        startActivityForResult(i, REQUEST_CHOOSE_CITY);
    }

    @Override
    public void myCity(int backgroudImageResource) {
        Intent i = new Intent(this,MyCityActivity.class);
        //设置天气背景图片
        i.putExtra(WeatherConstant.BACKGROUND_IMAGE_EXTRA,backgroudImageResource);
        startActivityForResult(i, REQUEST_MY_CITY);
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
        editor.putString(HomeFragment.TAG_SHARE_CITY,chooseCity);
        editor.apply();
    }

}
