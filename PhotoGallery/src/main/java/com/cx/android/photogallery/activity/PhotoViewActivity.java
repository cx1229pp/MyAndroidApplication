package com.cx.android.photogallery.activity;

import android.support.v4.app.Fragment;

import com.cx.android.photogallery.fragment.PhotoViewFragment;

/**
 * Created by 陈雪 on 2015/10/22.
 */
public class PhotoViewActivity extends BaseActivity{
    @Override
    protected Fragment createFragment() {
        return new PhotoViewFragment();
    }
}
