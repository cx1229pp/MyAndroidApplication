package com.cx.android.weather.util;


import com.cx.android.weather.R;

import java.util.ArrayList;
import java.util.List;

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
    private static final String GET_WEATHER_URL = "http://api.map.baidu.com/telematics/v3/weather?location=LOCATION&output=json&ak=AK";
    private static final String BAIDU_KEY = "jnS1ccPEB8FFoFFu89RURUjQ";

    //腾讯地图接口
    private static final String TX_AREA_QUERY_URL = "http://apis.map.qq.com/ws/district/v1/search?&keyword=KEYWORD&key=UD2BZ-MZYR5-INBIZ-QFG4O-76GBE-MKBZS";

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
    public static final String TAG_MYCITY_RESULT = "tag_mycity_result";
    public static final String TAG_CHOOSE_CITY = "tag_choose_city";
    public static final String TAG_SHARE_CITY = "tag_share_city";

    //SearchCityFragment
    public static final String SEARCHCITYFRAGMENT_CITYNAME = "searchCityFragment_cityname";
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

    public static List<String> getHotCitysData(){
        List<String> list = new ArrayList<>();
        list.add("北京");
        list.add("上海");
        list.add("广州");
        list.add("深圳");
        list.add("重庆");
        list.add("成都");
        list.add("武汉");
        list.add("南京");
        list.add("苏州");
        list.add("杭州");
        list.add("三亚");
        list.add("厦门");
        list.add("天津");
        list.add("沈阳");
        list.add("郑州");
        list.add("济南");
        list.add("兰州");
        list.add("西安");
        list.add("贵阳");
        list.add("南宁");
        list.add("福州");
        list.add("南昌");
        list.add("合肥");
        list.add("长沙");
        list.add("香港");
        list.add("澳门");
        list.add("台北");
        list.add("高雄");

        return list;
    }

    public static String getTxAreaQueryUrl(String keyword){
        return TX_AREA_QUERY_URL.replace("KEYWORD",keyword);
    }

    public static String getWeatherUrl(String location){
        return GET_WEATHER_URL.replace("AK",BAIDU_KEY).replace("LOCATION",location);
    }
}
