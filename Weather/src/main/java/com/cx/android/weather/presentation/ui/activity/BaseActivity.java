package com.cx.android.weather.presentation.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.cx.android.weather.R;


/**
 * activity基类，用于启动fragment
 * Created by 陈雪 on 2015/9/10.
 */
public abstract class BaseActivity extends FragmentActivity {
    protected abstract Fragment createFragment();

    protected int getLayout(){
        return  R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        startFragment();
    }

    private void startFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment crimFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(crimFragment == null){
            crimFragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container,crimFragment)
                    .commit();
        }
    }
}
