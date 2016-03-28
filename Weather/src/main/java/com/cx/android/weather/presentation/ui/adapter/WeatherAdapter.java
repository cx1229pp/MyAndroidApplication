package com.cx.android.weather.presentation.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.cx.android.weather.presentation.presenters.WeatherAdapterPresenter;
import com.cx.android.weather.presentation.ui.layout.MyCityGridViewItem;
import com.cx.android.weather.util.WeatherConstant;
import java.util.List;

/**
 * 已选择城市GridView apapter
 * Created by 陈雪 on 2015/11/12.
 */
public class WeatherAdapter extends BaseAdapter{
    private Context mContext;
    private List<String> mCityList;
    private boolean isDeleteImageVisible = false;
    private boolean isUpdateFromNetwork = false;
    private WeatherAdapterPresenter mPresenter;

    public WeatherAdapter(Context context, List<String> cityList, WeatherAdapterPresenter presenter) {
        this.mContext = context;
        this.mCityList = cityList;
        mPresenter = presenter;
        //添加新增城市项
        this.mCityList.add("addImage");
    }

    @Override
    public int getCount() {
        return mCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String cityName = mCityList.get(position);
        MyCityGridViewItem item;

        if(convertView == null){
            item = new MyCityGridViewItem(mContext);
            item.setTag(cityName);
        }else{
            item = (MyCityGridViewItem) convertView;
        }

        //判断是否显示添加城市选项
        boolean isAddImage = false;
        if(WeatherConstant.MY_CITY_FRAGMENT_ADDIMAGE.equals(cityName)){
            isAddImage = true;
            item.getmFreshProgressBar().setVisibility(View.GONE);
        }else{
            isAddImage = false;
            //默认隐藏天气view，显示更新进度bar
            item.getmFreshProgressBar().setVisibility(View.VISIBLE);
            item.getmAddImage().setVisibility(View.GONE);
            item.getmCityNameText().setVisibility(View.GONE);
            item.getmWeatherImage().setVisibility(View.GONE);
            item.getmTemperatureText().setVisibility(View.GONE);
            item.getmWeatherText().setVisibility(View.GONE);
        }

        //设置删除图标的可见
        if(isDeleteImageVisible && !isAddImage){
            item.getmDeleteImage().setVisibility(View.VISIBLE);
        }else{
            item.getmDeleteImage().setVisibility(View.GONE);
        }

        //不是添加项则更新天气
        if(!isAddImage){
            mPresenter.updateAdapterWeatherData(cityName,isUpdateFromNetwork);
        }

        return item;
    }

    public void setDeleteImageVisible(){
        isDeleteImageVisible = !isDeleteImageVisible;
        notifyDataSetChanged();
    }

    public boolean getDeleteImageVisible(){
        return this.isDeleteImageVisible;
    }

    public void setIsUpdateFromNetwork(boolean isUpdateFromNetwork) {
        this.isUpdateFromNetwork = isUpdateFromNetwork;
        notifyDataSetChanged();
    }
}
