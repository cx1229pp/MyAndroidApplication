package com.cx.android.photogallery.activity;

import android.support.v4.app.Fragment;

import com.cx.android.photogallery.fragment.PhotoGalleryFragment;

public class PhotoGalleryActivity extends BaseActivity {

    @Override
    protected Fragment createFragment() {
        return new PhotoGalleryFragment();
    }

}
