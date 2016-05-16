package com.cx.android.weather.presentation.ui.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cx.android.weather.R;
import com.cx.android.weather.presentation.view.SmartImageView;

/**
 * 已选城市列表gridview item
 * Created by 陈雪 on 2016/3/28.
 */
public class MyCityGridViewItem extends FrameLayout{
    private TextView mCityNameText;
    private SmartImageView mWeatherImage;
    private TextView mTemperatureText;
    private TextView mWeatherText;
    private ImageView mDeleteImage;
    private ImageView mAddImage;
    private ProgressBar mFreshProgressBar;
    private Context mContext;

    public MyCityGridViewItem(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init(){
        LayoutInflater.from(mContext).inflate(R.layout.my_city_item, this);
        mCityNameText = (TextView)findViewById(R.id.tv_choose_item_cityname);
        mWeatherImage = (SmartImageView) findViewById(R.id.siv_choose_item_pic);
        mTemperatureText = (TextView) findViewById(R.id.tv_choose_item_temperature);
        mWeatherText = (TextView) findViewById(R.id.tv_choose_item_weather);
        mDeleteImage = (ImageView) findViewById(R.id.iv_choose_item_delete);
        mAddImage = (ImageView) findViewById(R.id.my_city_item_addButton);
        mFreshProgressBar = (ProgressBar) findViewById(R.id.my_city_item_pb);
    }

    public TextView getmCityNameText() {
        return mCityNameText;
    }

    public SmartImageView getmWeatherImage() {
        return mWeatherImage;
    }

    public TextView getmTemperatureText() {
        return mTemperatureText;
    }

    public TextView getmWeatherText() {
        return mWeatherText;
    }

    public ImageView getmDeleteImage() {
        return mDeleteImage;
    }

    public ImageView getmAddImage() {
        return mAddImage;
    }

    public ProgressBar getmFreshProgressBar() {
        return mFreshProgressBar;
    }
}
