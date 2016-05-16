package com.cx.android.weather.domain.interactors.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.cx.android.weather.data.model.City;
import com.cx.android.weather.domain.executor.MainThread;
import com.cx.android.weather.domain.interactors.SearchCityInteractor;
import com.cx.android.weather.util.WeatherConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈雪 on 2016/3/30.
 */
public class SearchCityInteractorImpl implements SearchCityInteractor {
    private SearchCityInteractor.Callback mCallback;
    private Context mContext;

    public SearchCityInteractorImpl(Context context,SearchCityInteractor.Callback callback){
        mContext = context;
        mCallback = callback;
    }

    @Override
    public void execute(String cityName) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        try {
            String url = WeatherConstant.getTxAreaQueryUrl(URLEncoder.encode(cityName,"UTF-8"));
            JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    mCallback.onResponse(parseJSONResponse(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mCallback.onErrorResponse(error.getMessage());
                }
            });

            queue.add(jsonRequest);
        } catch (UnsupportedEncodingException e) {
            Log.d("",e.getMessage());
        }
    }

    /**
     * 解析返回的查询json结果
     * @param response
     * @return
     */
    private List<City> parseJSONResponse(JSONObject response){
        List<City> list = null;
        try {
            JSONArray array = response.getJSONArray("result");
            if(array != null && array.length() > 0){
                JSONArray resultArray = array.getJSONArray(0);
                list = new ArrayList<>();
                int resultArrayLength = resultArray.length();
                for(int i=0; i<resultArrayLength; i++){
                    JSONObject jsonObject = resultArray.getJSONObject(i);
                    City city = new City();
                    city.setName(jsonObject.getString("fullname"));
                    city.setAddress(jsonObject.getString("address"));
                    list.add(city);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
