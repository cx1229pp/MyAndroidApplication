package com.cx.android.weather.presentation.presenters;

import android.content.Context;

import com.cx.android.weather.data.model.City;
import com.cx.android.weather.domain.interactors.SearchCityInteractor;
import com.cx.android.weather.domain.interactors.impl.SearchCityInteractorImpl;
import com.cx.android.weather.presentation.presenters.base.BasePresenter;
import com.cx.android.weather.presentation.ui.ISearchCityFragmentView;

import java.util.List;

/**
 * Created by 陈雪 on 2016/3/30.
 */
public class SearchCityFragmentPresenter implements BasePresenter,SearchCityInteractor.Callback{
    private Context mContext;
    private ISearchCityFragmentView mSearchCityFragment;

    public SearchCityFragmentPresenter(Context context,ISearchCityFragmentView searchCityFragmentView){
        mContext = context;
        mSearchCityFragment = searchCityFragmentView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }


    @Override
    public void onResponse(List<City> list) {
        if(list != null && list.size() > 0){
            mSearchCityFragment.searchCityResult(list);
        }
    }

    @Override
    public void onErrorResponse(String errorResponse) {

    }

    public void searchCity(String keyword){
        if(!"".equals(keyword)){
            SearchCityInteractor interactor = new SearchCityInteractorImpl(mContext,this);
            interactor.execute(keyword);
        }else{
            mSearchCityFragment.hiddenSearchCityRecyclerView();
        }
    }
}
