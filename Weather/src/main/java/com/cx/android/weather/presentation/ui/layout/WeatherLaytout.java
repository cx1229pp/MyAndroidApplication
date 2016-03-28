package com.cx.android.weather.presentation.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cx.android.weather.R;
import com.cx.android.weather.data.model.Temperature;
import com.cx.android.weather.data.model.Weather;
import com.cx.android.weather.util.WeatherConstant;
import com.cx.android.weather.presentation.view.SmartImageView;

import java.util.Calendar;
import java.util.List;

/**
 * 自定义天气布局
 * Created by chenxue on 2015/5/11.
 */
public class WeatherLaytout extends LinearLayout{
    private Context context;
    private TextView tvDay1;
    private TextView tvDay2;
    private TextView tvDay3;
    private TextView tvDay4;
    private SmartImageView sivDayImg1;
    private SmartImageView sivDayImg2;
    private SmartImageView sivDayImg3;
    private SmartImageView sivDayImg4;
    private TextView tvDayWeather1;
    private TextView tvDayWeather2;
    private TextView tvDayWeather3;
    private TextView tvDayWeather4;
    private TextView tvDayTemperature1;
    private TextView tvDayTemperature2;
    private TextView tvDayTemperature3;
    private TextView tvDayTemperature4;
    private TextView tvDayWind1;
    private TextView tvDayWind2;
    private TextView tvDayWind3;
    private TextView tvDayWind4;
    private SmartImageView sivDayImgN1;
    private SmartImageView sivDayImgN2;
    private SmartImageView sivDayImgN3;
    private SmartImageView sivDayImgN4;
    private TextView tvWeek1;
    private TextView tvWeek2;
    private TextView tvWeek3;
    private TextView tvWeek4;
    private static final int GET_IMG = 1;

    public WeatherLaytout(Context context) {
        this(context,null);
    }

    public WeatherLaytout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public void init(){
        LayoutInflater.from(context).inflate(R.layout.weather_layout, this);
        View layoutItem1 = findViewById(R.id.weather_1);
        View layoutItem2 = findViewById(R.id.weather_2);
        View layoutItem3 = findViewById(R.id.weather_3);
        View layoutItem4 = findViewById(R.id.weather_4);

        tvDay1 = (TextView)layoutItem1.findViewById(R.id.tv_day);
        tvDay2 = (TextView)layoutItem2.findViewById(R.id.tv_day);
        tvDay3 = (TextView)layoutItem3.findViewById(R.id.tv_day);
        tvDay4 = (TextView)layoutItem4.findViewById(R.id.tv_day);

        sivDayImg1 = (SmartImageView)layoutItem1.findViewById(R.id.image_weather);
        sivDayImg2 = (SmartImageView)layoutItem2.findViewById(R.id.image_weather);
        sivDayImg3 = (SmartImageView)layoutItem3.findViewById(R.id.image_weather);
        sivDayImg4 = (SmartImageView)layoutItem4.findViewById(R.id.image_weather);

        sivDayImgN1 = (SmartImageView)layoutItem1.findViewById(R.id.image_weather_night);
        sivDayImgN2 = (SmartImageView)layoutItem2.findViewById(R.id.image_weather_night);
        sivDayImgN3 = (SmartImageView)layoutItem3.findViewById(R.id.image_weather_night);
        sivDayImgN4 = (SmartImageView)layoutItem4.findViewById(R.id.image_weather_night);

        tvDayTemperature1 = (TextView)layoutItem1.findViewById(R.id.tv_temperature);
        tvDayTemperature2 = (TextView)layoutItem2.findViewById(R.id.tv_temperature);
        tvDayTemperature3 = (TextView)layoutItem3.findViewById(R.id.tv_temperature);
        tvDayTemperature4 = (TextView)layoutItem4.findViewById(R.id.tv_temperature);

        tvDayWeather1 = (TextView)layoutItem1.findViewById(R.id.tv_weather);
        tvDayWeather2 = (TextView)layoutItem2.findViewById(R.id.tv_weather);
        tvDayWeather3 = (TextView)layoutItem3.findViewById(R.id.tv_weather);
        tvDayWeather4 = (TextView)layoutItem4.findViewById(R.id.tv_weather);

        tvDayWind1 = (TextView)layoutItem1.findViewById(R.id.tv_wind);
        tvDayWind2 = (TextView)layoutItem2.findViewById(R.id.tv_wind);
        tvDayWind3 = (TextView)layoutItem3.findViewById(R.id.tv_wind);
        tvDayWind4 = (TextView)layoutItem4.findViewById(R.id.tv_wind);

        tvWeek1 = (TextView)layoutItem1.findViewById(R.id.tv_week_zh);
        tvWeek2 = (TextView)layoutItem2.findViewById(R.id.tv_week_zh);
        tvWeek3 = (TextView)layoutItem3.findViewById(R.id.tv_week_zh);
        tvWeek4 = (TextView)layoutItem4.findViewById(R.id.tv_week_zh);
    }

    public void setData(Weather weather){
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String day = context.getString(R.string.day);
        tvDay1.setText(today + day);
        tvDay2.setText(today + 1 + day);
        tvDay3.setText(today + 2 + day);
        tvDay4.setText(today + 3 + day);

        tvWeek1.setText(context.getString(WeatherConstant.getWeekZH(dayOfWeek - 1)));
        tvWeek2.setText(context.getString(WeatherConstant.getWeekZH(dayOfWeek)));
        tvWeek3.setText(context.getString(WeatherConstant.getWeekZH(dayOfWeek + 1)));
        tvWeek4.setText(context.getString(WeatherConstant.getWeekZH(dayOfWeek + 2)));

        List<Temperature> temperatureList = weather.getTemperatureList();
        if(temperatureList != null && temperatureList.size() > 0){
            Temperature t1 = temperatureList.get(0);
            Temperature t2 = temperatureList.get(1);
            Temperature t3 = temperatureList.get(2);
            Temperature t4 = temperatureList.get(3);

            tvDayWeather1.setText(t1.getWeather());
            tvDayWeather2.setText(t2.getWeather());
            tvDayWeather3.setText(t3.getWeather());
            tvDayWeather4.setText(t4.getWeather());

            tvDayTemperature1.setText(t1.getTemperature());
            tvDayTemperature2.setText(t2.getTemperature());
            tvDayTemperature3.setText(t3.getTemperature());
            tvDayTemperature4.setText(t4.getTemperature());

            tvDayWind1.setText(t1.getWind());
            tvDayWind2.setText(t2.getWind());
            tvDayWind3.setText(t3.getWind());
            tvDayWind4.setText(t4.getWind());

            sivDayImg1.setUrl(t1.getDayPictureUrl());
            sivDayImg2.setUrl(t2.getDayPictureUrl());
            sivDayImg3.setUrl(t3.getDayPictureUrl());
            sivDayImg4.setUrl(t4.getDayPictureUrl());

            sivDayImgN1.setUrl(t1.getNightPictureUrl());
            sivDayImgN2.setUrl(t2.getNightPictureUrl());
            sivDayImgN3.setUrl(t3.getNightPictureUrl());
            sivDayImgN4.setUrl(t4.getNightPictureUrl());
        }
    }
}
