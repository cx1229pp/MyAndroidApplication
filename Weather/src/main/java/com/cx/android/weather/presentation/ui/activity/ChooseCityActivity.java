package com.cx.android.weather.presentation.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.cx.android.weather.data.db.WeatherDao;
import com.cx.android.weather.presentation.ui.fragment.ChooseCityFragment;
import com.cx.android.weather.util.WeatherConstant;

/**
 * 查询城市页面
 * Created by 陈雪 on 2015/11/4.
 */
public class ChooseCityActivity extends BaseActivity implements ChooseCityFragment.CallBack{

    @Override
    protected Fragment createFragment() {
        return new ChooseCityFragment();
    }

    @Override
    public void chooseCity(String cityName) {
        WeatherDao weatherDao = WeatherDao.getInstance(this);
        if(!weatherDao.isSelectCity(cityName)){
            weatherDao.addSelectCity("", cityName);
            Intent i = new Intent();
            i.putExtra(WeatherConstant.TAG_CHOOSE_CITY,cityName);
            setResult(Activity.RESULT_OK,i);
            finish();
        }
    }
}
