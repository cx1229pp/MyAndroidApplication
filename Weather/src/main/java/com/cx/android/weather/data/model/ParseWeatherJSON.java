package com.cx.android.weather.data.model;

import android.text.TextUtils;


import com.cx.android.weather.data.model.Temperature;
import com.cx.android.weather.data.model.Weather;
import com.cx.android.weather.data.model.WeatherIndex;
import com.cx.android.weather.data.net.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ldn on 2015/5/8.
 * 解析天气接口返回的json数据
 */
public class ParseWeatherJSON {
    public static Weather getWeatherData(String url){
        String result = HttpUtil.requestString(url);
        return praseJson(result);
    }

    public static Weather praseJson(String jsonString){
        Weather weather = null;
        try{
            if(!TextUtils.isEmpty(jsonString)){
                JSONObject jsonObj = new JSONObject(jsonString);
                String status = jsonObj.getString("status");
                if(status.equals("success")){
                    weather = new Weather();
                    String date = jsonObj.getString("date");
                    weather.setDate(date);

                    JSONArray resultsJson = jsonObj.getJSONArray("results");
                    JSONObject result = resultsJson.getJSONObject(0);

                    String currentCity = result.getString("currentCity");
                    weather.setCurrentCity(currentCity);

                    String pm25 = result.getString("pm25");
                    weather.setPm25(pm25);

                    List<WeatherIndex> indexList = new ArrayList<WeatherIndex>();
                    JSONArray indexJson = result.getJSONArray("index");
                    if(indexJson != null){
                        for(int i=0; i<indexJson.length(); i++){
                            JSONObject obj = indexJson.getJSONObject(i);
                            WeatherIndex index = new WeatherIndex();
                            index.setTitle(obj.getString("title"));
                            index.setZs(obj.getString("zs"));
                            index.setTipt(obj.getString("tipt"));
                            index.setDes(obj.getString("des"));
                            indexList.add(index);
                        }

                        weather.setIndexList(indexList);
                    }

                    List<Temperature> temperatureList = new ArrayList<Temperature>();
                    JSONArray weatherDataJson = result.getJSONArray("weather_data");
                    if(weatherDataJson != null){
                        for(int i=0; i<weatherDataJson.length(); i++){
                            JSONObject obj = weatherDataJson.getJSONObject(i);
                            Temperature t = new Temperature();
                            t.setDate(obj.getString("date"));
                            t.setDayPictureUrl(obj.getString("dayPictureUrl"));
                            t.setNightPictureUrl(obj.getString("nightPictureUrl"));
                            t.setTemperature(obj.getString("temperature"));
                            t.setWeather(obj.getString("weather"));
                            t.setWind(obj.getString("wind"));

                            temperatureList.add(t);
                        }

                        weather.setTemperatureList(temperatureList);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return weather;
    }
}