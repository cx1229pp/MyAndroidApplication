package com.cx.android.weather.repository;

import android.os.Build;

import com.cx.android.weather.ApplicationTestCase;
import com.cx.android.weather.data.model.Weather;
import com.cx.android.weather.data.repository.WeatherDataRepository;
import com.cx.android.weather.data.repository.impl.WeatherDataRepositoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;


/**
 * Created by 陈雪 on 2016/3/23.
 */
public class WeatherDataRepositoryTest extends ApplicationTestCase {
    @Mock private WeatherDataRepository weatherDataRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        weatherDataRepository =
                new WeatherDataRepositoryImpl(RuntimeEnvironment.application);
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.KITKAT)
    public void addCityTest(){
        weatherDataRepository.addCity("昆明");
        List<String> list = weatherDataRepository.querySelectdCitys();
        System.out.print("size:"+list.size());
        System.out.print("isSelected:"+weatherDataRepository.isSelectedCity("昆明"));
    }


    @Test
    @Config(sdk = Build.VERSION_CODES.KITKAT)
    public void saveCurrentCityTest(){
        weatherDataRepository.saveCurrentCity("昆明");
        String cityName = weatherDataRepository.getCurrentCity();
        System.out.print("cityName:"+cityName);
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.KITKAT)
    public void getWeatherDataTest(){
        Weather weather = weatherDataRepository.getWeatherDataFromCache("昆明");
        System.out.println("weather:"+weather.getCurrentCity() +  "-" + weather.getDate());

        Weather weather2 = weatherDataRepository.getWeatherDataFromCache("昆明");
        System.out.println("weather2:"+weather2.getCurrentCity() +  "-" + weather2.getDate());
    }


}
