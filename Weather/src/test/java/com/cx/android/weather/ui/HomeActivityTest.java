package com.cx.android.weather.ui;

import android.app.Fragment;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.cx.android.weather.presentation.ui.activity.HomeActivity;
import com.cx.android.weather.presentation.ui.fragment.WeatherFragment;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by 陈雪 on 2016/3/24.
 */
public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {
    private HomeActivity homeActivity;

    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.setActivityIntent(new Intent(getInstrumentation().getTargetContext(),HomeActivity.class));
        homeActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testContainsUserListFragment() {
        assertThat(homeActivity, is(notNullValue()));
    }
}
