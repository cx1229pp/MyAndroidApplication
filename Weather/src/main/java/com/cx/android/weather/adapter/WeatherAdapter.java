package com.cx.android.weather.adapter;

import android.content.Context;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cx.android.weather.R;
import com.cx.android.weather.model.Temperature;
import com.cx.android.weather.model.Weather;
import com.cx.android.weather.util.DiskLruCacheUtil;
import com.cx.android.weather.util.ParseWeatherJSON;
import com.cx.android.weather.util.UpdateWeatherCacheUtil;
import com.cx.android.weather.util.UpdateWeatherTask;
import com.cx.android.weather.util.WeatherConstant;
import com.cx.android.weather.view.SmartImageView;

import java.util.List;

/**
 * 已选择城市GridView apapter
 * Created by 陈雪 on 2015/11/12.
 */
public class WeatherAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mCityList;
    private boolean isDeleteImageVisible = false;
    private boolean isUpdateFromNetwork = false;
    private boolean isAddImage = false;
    private LruCache<String,String> mLruCache;

    public WeatherAdapter(Context context, List<String> cityList,LruCache<String,String> lruCache) {
        this.mContext = context;
        this.mCityList = cityList;
        this.mLruCache = lruCache;
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
        final ViewHandler viewHandler;
        View view;

        if(convertView == null){
            viewHandler = new ViewHandler();
            view = View.inflate(mContext, R.layout.my_city_item,null);
            viewHandler.cityNameText = (TextView) view.findViewById(R.id.tv_choose_item_cityname);
            viewHandler.cityNameText.setText(cityName);
            viewHandler.weatherImage = (SmartImageView) view.findViewById(R.id.siv_choose_item_pic);
            viewHandler.temperatureText = (TextView) view.findViewById(R.id.tv_choose_item_temperature);
            viewHandler.weatherText = (TextView) view.findViewById(R.id.tv_choose_item_weather);
            viewHandler.deleteImage = (ImageView) view.findViewById(R.id.iv_choose_item_delete);
            viewHandler.addImage = (ImageView) view.findViewById(R.id.my_city_item_addButton);

            view.setTag(viewHandler);
        }else{
            view = convertView;
            viewHandler = (ViewHandler) view.getTag();
            viewHandler.cityNameText.setText(cityName);
        }

        if(WeatherConstant.MY_CITY_FRAGMENT_ADDIMAGE.equals(cityName)){
            isAddImage = true;
            viewHandler.cityNameText.setVisibility(View.INVISIBLE);
            viewHandler.cityNameText.setVisibility(View.INVISIBLE);
            viewHandler.weatherImage.setVisibility(View.INVISIBLE);
            viewHandler.temperatureText.setVisibility(View.INVISIBLE);
            viewHandler.weatherText.setVisibility(View.INVISIBLE);
        }else{
            isAddImage = false;
            viewHandler.addImage.setVisibility(View.INVISIBLE);
        }

        //设置删除图标的可见
        if(isDeleteImageVisible && !isAddImage){
            viewHandler.deleteImage.setVisibility(View.VISIBLE);
        }else{
            viewHandler.deleteImage.setVisibility(View.INVISIBLE);
        }

        if(!isAddImage){
            updateWeather(cityName, viewHandler);
        }

        return view;
    }

    /**
     * 先从缓存中获取数据，如果获取不到则从网络获取
     * @param share_city 当前城市名称
     */
    private void updateWeather(final String share_city, final ViewHandler viewHandler) {
        DiskLruCacheUtil diskLruCacheUtil = new DiskLruCacheUtil(mContext.getApplicationContext(), WeatherConstant.DISKCACHE_FORDER);
        final UpdateWeatherCacheUtil cacheUtil = new UpdateWeatherCacheUtil(mLruCache,diskLruCacheUtil);
        Weather mWeather = cacheUtil.getFromCache(share_city);

        if(isUpdateFromNetwork || mWeather == null){
            UpdateWeatherTask updateWeatherTask = new UpdateWeatherTask();
            updateWeatherTask.execute(share_city);
            updateWeatherTask.setmOnWeatherPostLisenter(new UpdateWeatherTask.OnWeatherPostLisenter() {
                @Override
                public void onPost(String weatherJson) {
                    if (weatherJson != null && !"".equals(weatherJson)) {
                        cacheUtil.setCache(share_city, weatherJson);
                        setWeatherData(ParseWeatherJSON.praseJson(weatherJson),viewHandler);
                    }
                }
            });
        }else{
            setWeatherData(mWeather,viewHandler);
        }
    }

    private void setWeatherData(Weather weather,ViewHandler viewHandler) {
        Temperature temp = weather.getTemperatureList().get(0);
        viewHandler.weatherText.setText(temp.getWeather());
        viewHandler.weatherImage.setUrl(temp.getDayPictureUrl());
        viewHandler.temperatureText.setText(temp.getTemperature());
    }

    class ViewHandler{
        TextView cityNameText;
        SmartImageView weatherImage;
        TextView temperatureText;
        TextView weatherText;
        ImageView deleteImage;
        ImageView addImage;
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
