package com.cx.android.weather.presentation.ui;

import com.cx.android.weather.data.model.City;

import java.util.List;

/**
 * chooseCityFragment 回调接口
 * Created by 陈雪 on 2016/3/31.
 */
public interface ISearchCityFragmentView {
    /**
     *更新city recyclerView
     * @param list
     */
    void searchCityResult(List<City> list);

    /**
     * 隐藏city recyclerView
     */
    void hiddenSearchCityRecyclerView();
}
