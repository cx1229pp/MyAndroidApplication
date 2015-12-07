package com.cx.android.photogallery.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by 陈雪 on 2015/10/20.
 */
public class PhotoService extends IntentService {
    private static final String TAG = "phtotservice";

    public PhotoService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("PhotoService","PhotoService is run!");
    }
}
