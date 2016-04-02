package com.cx.android.weather.presentation.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.cx.android.weather.R;
import com.cx.android.weather.presentation.ui.fragment.MyCityFragment;
import com.cx.android.weather.util.WeatherConstant;

/**
 *已选城市页面
 * Created by 陈雪 on 2015/11/12.
 */
public class MyCityActivity extends FragmentActivity
        implements MyCityFragment.MyCityCallBack{
    private static final int REQUEST_CHOOSE_CITY = 1;
    private int backGroundResource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        backGroundResource = getIntent().getIntExtra(WeatherConstant.BACKGROUND_IMAGE_EXTRA,0);
        startFragment();
    }

    private void startFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = MyCityFragment.newInstance(backGroundResource);
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
    }

    @Override
    public void addCity() {
        Intent i = new Intent(this,ChooseCityActivity.class);
        startActivityForResult(i,REQUEST_CHOOSE_CITY);
    }

    @Override
    public void setResult(String cityName) {
        Intent i = new Intent();
        i.putExtra(WeatherConstant.TAG_MYCITY_RESULT,cityName);
        setResult(Activity.RESULT_OK,i);
        finish();
    }
}
