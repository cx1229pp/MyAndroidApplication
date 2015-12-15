package com.cx.android.weather.util;


import com.cx.android.weather.R;

/**
 * 常量工具类
 * Created by ldn on 2015/5/16.
 */
public class WeatherConstant {
    //星期一
    public static final int MONDAY_ZH = R.string.week1;
    //星期二
    public static final int TUESDAY_ZH = R.string.week2;
    //星期三
    public static final int WEDNESDAY_ZH = R.string.week3;
    //星期四
    public static final int THURSDAY_ZH = R.string.week4;
    //星期五
    public static final int FRIDAY_ZH = R.string.week5;
    //星期六
    public static final int SATURDAY_ZH = R.string.week6;
    //星期日
    public static final int SUNDAY_ZH = R.string.week7;

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;

    //百度天气接口
    public static final String GET_WEATHER_URL = "http://api.map.baidu.com/telematics/v3/weather?location=LOCATION&output=json&ak=AK";
    public static final String BAIDU_KEY = "jnS1ccPEB8FFoFFu89RURUjQ";

    //天气背景
    private static final int BG_QING_DAY = R.mipmap.bg_qing_day;
    private static final int BG_DUOYUN_DAY = R.mipmap.bg_duoyun_day;
    private static final int BG_QING_NIGHT = R.mipmap.bg_qing_night;
    private static final int BG_DUOYUN_NIGHT = R.mipmap.bg_duoyun_night;
    private static final int BG_XIAOYU = R.mipmap.bg_xiaoyu;
    private static final int BG_LEIYU = R.mipmap.bg_leiyu;
    private static final int BG_XUE = R.mipmap.bg_xue;
    private static final int BG_WU = R.mipmap.bg_wu;
    private static final int BG_YIN = R.mipmap.bg_ying;

    public static final String DISKCACHE_FORDER = "WeatherCache";
    public static final String BACKGROUND_IMAGE_EXTRA = "background_image_extra";
    public static final String TAG_SHARE_BACKGROUND = "tag_share_background";

    public static final String MY_CITY_FRAGMENT_ADDIMAGE = "addImage";
    /**
     * 获取天气背景
     * @param weather
     * @return
     */
    public static int getWeatherBG(String weather){
        int bgName = 0;
        if(weather.contains("晴")){
            bgName = BG_QING_DAY;
        }else if(weather.contains("雪")){
            bgName = BG_XUE;
        }else if(weather.contains("雨")){
            bgName = BG_XIAOYU;
        }else if(weather.contains("雷")){
            bgName = BG_LEIYU;
        }else if(weather.contains("雾")){
            bgName = BG_WU;
        }else if(weather.contains("阴")){
            bgName = BG_YIN;
        }else if(weather.contains("多云")){
            bgName = BG_DUOYUN_DAY;
        }else if(weather.contains("霾")){
            bgName = BG_DUOYUN_DAY;
        }else if(weather.contains("晴")){
            bgName = BG_DUOYUN_DAY;
        }

        return bgName;
    }

    /**
     * 获取星期数
     * @param week
     * @return
     */
    public static int getWeekZH(int week){
        int weekStrId = 0;
        switch (week){
            case MONDAY :
                weekStrId = MONDAY_ZH;
            break;

            case 8 :
                weekStrId = MONDAY_ZH;
                break;

            case TUESDAY :
                weekStrId = TUESDAY_ZH;
                break;

            case 9 :
                weekStrId = TUESDAY_ZH;
                break;

            case WEDNESDAY :
                weekStrId = WEDNESDAY_ZH;
                break;

            case 10 :
                weekStrId = WEDNESDAY_ZH;
                break;

            case THURSDAY :
                weekStrId = THURSDAY_ZH;
                break;

            case FRIDAY :
                weekStrId = FRIDAY_ZH;
                break;

            case SATURDAY :
                weekStrId = SATURDAY_ZH;
                break;

            case SUNDAY :
                weekStrId = SUNDAY_ZH;
                break;

            case 0 :
                weekStrId = SUNDAY_ZH;
                break;
        }

        return weekStrId;
    }
}
