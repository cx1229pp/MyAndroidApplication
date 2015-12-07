package com.cx.android.photogallery.tools;

import android.net.Uri;
import android.util.Log;

import com.cx.android.photogallery.model.GalleryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈雪 on 2015/10/8.
 */
public class PhotoGalleryFetcher {
    private static final String JSON_ARRAY_TAG = "data";
    private static final String JSON_ITEM_ABS_TAG = "abs";
    private static final String JSON_ITEM_URL_TAG = "image_url";
    private static final String FETCHER_URL = "http://image.baidu.com/channel/listjson";
    //pn=0&rn=20&tag1=汽车&tag2=SUV越野车&ie=utf8

    public List<GalleryItem> getPhotoList(String keywords){
        String url = Uri.parse(FETCHER_URL).buildUpon()
                .appendQueryParameter("pn", "0")
                .appendQueryParameter("rn", "20")
                .appendQueryParameter("tag1", "汽车")
                .appendQueryParameter("tag2", keywords)
                .appendQueryParameter("ie","UTF-8")
                .build().toString();

        return fetchPhotoFromJson(HttpUtil.requestString(url));
    }

    private List<GalleryItem> fetchPhotoFromJson(String mJsonString){
        List<GalleryItem> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_ARRAY_TAG);
            if(jsonArray != null){
                int length = jsonArray.length();
                for(int i=0; i<length; i++){
                    JSONObject itemObj = jsonArray.getJSONObject(i);
                    GalleryItem item = new GalleryItem();
                    item.setId(System.currentTimeMillis()+"");
                    item.setTitle(itemObj.get(JSON_ITEM_ABS_TAG).toString());
                    item.setImageUrl(itemObj.get(JSON_ITEM_URL_TAG).toString());

                    list.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
