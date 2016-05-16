package com.cx.android.weather.domain;

import android.content.Context;
import android.os.Build;

import com.cx.android.weather.ApplicationTestCase;
import com.cx.android.weather.domain.executor.MainThread;
import com.cx.android.weather.domain.interactors.SearchCityInteractor;
import com.cx.android.weather.domain.interactors.impl.SearchCityInteractorImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;

/**
 * Created by 陈雪 on 2016/3/30.
 */
public class SearchCityInteractorTest extends ApplicationTestCase {
    private SearchCityInteractor interactor;

    @Mock MainThread mainThread;
    @Mock SearchCityInteractor.Callback callback;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        interactor = new SearchCityInteractorImpl(mainThread, RuntimeEnvironment.application,callback);
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.KITKAT)
    public void executeTest(){
        interactor.execute("昆明");
        //verify(callback).onResponse(null);
    }
}
