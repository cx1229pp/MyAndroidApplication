package com.cx.android.weather.domain;

import com.cx.android.weather.data.repository.WeatherDataRepository;
import com.cx.android.weather.domain.executor.Executor;
import com.cx.android.weather.domain.executor.MainThread;
import com.cx.android.weather.domain.interactors.WeatherDataInteractor;
import com.cx.android.weather.domain.interactors.impl.WeatherDataInteractorImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by 陈雪 on 2016/3/23.
 */
public class WeatherDataInteractorTest {
    private WeatherDataInteractorImpl weatherDataInteractor;

    @Mock Executor executor;
    @Mock MainThread mainThread;
    @Mock WeatherDataInteractor.Callback mCallback;
    @Mock WeatherDataRepository mWeatherDataRepository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        weatherDataInteractor = new WeatherDataInteractorImpl(executor,mainThread
                ,mCallback,mWeatherDataRepository,"昆明",false);
    }

    @Test
    public void testGetWeather(){
        weatherDataInteractor.run();

        verify(mWeatherDataRepository).getWeatherDataFromCache("昆明");
        verifyNoMoreInteractions(mWeatherDataRepository);
    }
}
