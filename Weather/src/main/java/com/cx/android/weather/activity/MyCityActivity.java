package com.cx.android.weather.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.cx.android.weather.fragment.MyCityFragment;

/**
 * Created by 陈雪 on 2015/11/12.
 */
public class MyCityActivity extends BaseActivity
        implements MyCityFragment.MyCityCallBack{
    private static final int REQUEST_CHOOSE_CITY = 1;
    public static final String TAG_MYCITY_RESULT = "tag_mycity_result";

    @Override
    protected Fragment createFragment() {
        return new MyCityFragment();
    }

    @Override
    public void addCity() {
        Intent i = new Intent(this,ChooseCityActivity.class);
        startActivityForResult(i,REQUEST_CHOOSE_CITY);
    }

    @Override
    public void setResult(String cityName) {
        Intent i = new Intent();
        i.putExtra(TAG_MYCITY_RESULT,cityName);
        setResult(Activity.RESULT_OK,i);
        finish();
    }
}