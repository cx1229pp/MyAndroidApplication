package com.cx.android.photogallery.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by 陈雪 on 2015/10/10.
 */
public class ThumbnailDownloader<Token> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    private ArrayMap<Token,String> requestsMap = new ArrayMap<>();
    private Handler handler;
    private Handler mResponseHandler;
    private static final int MESSAGE_TAG = 0;
    private HandleResponseListener<Token> mHandleResponseListener;
    private LruCache<String,Bitmap> mLruCache;
    private DiskLruCacheUtil mDiskLruCacheUtil;

    public ThumbnailDownloader(Handler mResponseHandler,DiskLruCacheUtil mDiskLruCacheUtil
                                ,LruCache<String,Bitmap> mLruCache) {
        super(TAG);
        this.mResponseHandler = mResponseHandler;
        this.mDiskLruCacheUtil = mDiskLruCacheUtil;
        this.mLruCache = mLruCache;
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == MESSAGE_TAG){
                    @SuppressWarnings("unchecked")
                    Token token = (Token) msg.obj;
                    handleRequest(token);
                }
            }
        };
    }

    public void downloader(Token token,String imageUrl){
        requestsMap.put(token, imageUrl);

        handler.obtainMessage(MESSAGE_TAG,token)
                .sendToTarget();
    }

    private void handleRequest(final Token token){
        final String url = requestsMap.get(token);
        if(url == null || "".equals(url))return;

        final Bitmap bitmap;
        byte[] data = HttpUtil.requestByte(url);
        if(data == null)return;
        //写入DISK
        try {
            mDiskLruCacheUtil.writeDiskCache(url, data);
            Log.d(TAG,"写入DISK");
        } catch (IOException e) {
            e.printStackTrace();
        }

        bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
        //写入内存
        mLruCache.put(url, bitmap);
        Log.d(TAG, "写入内存");

        mResponseHandler.post(new Runnable() {
            @Override
            public void run() {
                if(!url.equals(requestsMap.get(token)))return;

                requestsMap.remove(token);
                mHandleResponseListener.handleResponse(url,bitmap);
            }
        });
    }

    public void setmHandleResponseListener(HandleResponseListener<Token> mHandleResponseListener){
        this.mHandleResponseListener = mHandleResponseListener;
    }

    public interface HandleResponseListener<Token>{
        //void handleResponse(Token token,Bitmap bitmap);

        void handleResponse(String url,Bitmap bitmap);
    }

    public void cleanTheard(){
        handler.removeMessages(MESSAGE_TAG);
        requestsMap.clear();
    }
}
