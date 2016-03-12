package com.cx.android.weather.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.cx.android.weather.util.DiskLruCacheUtil;
import com.cx.android.weather.util.HttpUtil;
import com.cx.android.weather.util.WeatherConstant;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义ImageView，用于异步获取网络图片
 * Created by chenxue on 2015/5/15.
 */
public class SmartImageView extends ImageView{
    private Context mContext;

    public SmartImageView(Context context) {
        super(context);
        this.mContext = context;
    }

    public SmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public void setUrl(String url){
        new WebImageTask().execute(url);
    }

    public void setImage(Bitmap bm){
        setImageBitmap(bm);
    }

    private class WebImageTask extends AsyncTask<String,Integer,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bm = null;
            String url = params[0];
            InputStream is = null;
            DiskLruCacheUtil diskLruCacheUtil = new DiskLruCacheUtil(mContext, WeatherConstant.DISKCACHE_FORDER);
            try {
                is = diskLruCacheUtil.getDiskCache(url);
                if(is != null){
                    bm = BitmapFactory.decodeStream(is);
                }else{
                    try {
                        byte[] b = HttpUtil.requestByte(url);
                        if(b != null){
                            bm = BitmapFactory.decodeByteArray(b,0,b.length);
                        }

                        diskLruCacheUtil.writeDiskCache(url,b);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bm) {
            setImage(bm);
        }
    }
}
