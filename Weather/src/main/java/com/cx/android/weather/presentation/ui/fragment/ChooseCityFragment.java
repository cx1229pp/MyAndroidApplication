package com.cx.android.weather.presentation.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.cx.android.weather.R;
import com.cx.android.weather.data.model.City;
import com.cx.android.weather.presentation.presenters.SearchCityFragmentPresenter;
import com.cx.android.weather.presentation.ui.ISearchCityFragmentView;
import com.cx.android.weather.presentation.ui.adapter.DividerItemDecoration;
import com.cx.android.weather.presentation.ui.adapter.SearchCityAdapter;
import com.cx.android.weather.util.WeatherConstant;

import java.util.List;

/**
 * 查询城市fragment
 * Created by 陈雪 on 2015/11/4.
 */
public class ChooseCityFragment extends Fragment implements ISearchCityFragmentView{
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private TextView mLocation;
    private CallBack mCallBack;
    private RecyclerView mCityList;
    private SearchCityFragmentPresenter mPresenter;
    private RelativeLayout mLocationLayout;
    private GridView mHotCitys;
    private SearchCityAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //声明百度定位LocationClient类
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener( myListener );
        initLocation();
        mLocationClient.start();

        //初始化presenter
        mPresenter = new SearchCityFragmentPresenter(getActivity().getApplication(),this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_citylist,container,false);
        mLocation = (TextView) view.findViewById(R.id.tv_location);
        mLocationLayout = (RelativeLayout) view.findViewById(R.id.layout_location);

        mCityList = (RecyclerView) view.findViewById(R.id.fragment_searchcity_rv_citylist);
        initRecyclerView();

        //设置城市查询输入监听
        EditText mSearchEditText = (EditText) view.findViewById(R.id.et_search_city);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //查询城市
                mPresenter.searchCity(s.toString());
            }
        };
        mSearchEditText.addTextChangedListener(textWatcher);

        mHotCitys = (GridView) view.findViewById(R.id.gv_hotcity);
        final List<String> list = WeatherConstant.getHotCitysData();
        mHotCitys.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list));
        //设置item点击监听
        mHotCitys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.chooseCity(list.get(position));
            }
        });

        ImageView backImage = (ImageView) view.findViewById(R.id.iv_back);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backParent();
            }
        });

        return view;
    }

    /**
     * 返回父页面
     */
    private void backParent(){
        if (NavUtils.getParentActivityIntent(getActivity()) != null) {
            NavUtils.navigateUpFromSameTask(getActivity());
        }
    }

    /**
     * 初始化city recyclerView
     */
    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mCityList.setLayoutManager(linearLayoutManager);
        mCityList.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new SearchCityAdapter();
        mAdapter.setmLisenter(new SearchCityAdapter.OnItemClickLisenter() {
            @Override
            public void onItemClick(String cityName) {
                mCallBack.chooseCity(cityName);
            }
        });

        mCityList.setAdapter(mAdapter);
    }

    /**
     * 更新city recyclerView
     * @param list
     */
    private void setRecycleViewAdapter(List<City> list){
        mAdapter.setCityDataList(list);
    }

    @Override
    public void searchCityResult(List<City> list) {
        mCityList.setVisibility(View.VISIBLE);
        mLocationLayout.setVisibility(View.GONE);
        mHotCitys.setVisibility(View.GONE);
        setRecycleViewAdapter(list);
    }

    @Override
    public void hiddenSearchCityRecyclerView() {
        mCityList.setVisibility(View.GONE);
        mLocationLayout.setVisibility(View.VISIBLE);
        mHotCitys.setVisibility(View.VISIBLE);
    }

    /**
     * 百度定位监听
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuilder sb = new StringBuilder(256);
            sb.append(location.getCountry());
            sb.append(location.getProvince());
            sb.append(location.getCity());
            final String cityName = location.getCity();
            mLocation.setText(sb.toString());
            mLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallBack.chooseCity(cityName);
                }
            });
        }
    }

    /**
     * 百度定位初始化
     */
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    //回调函数
    public interface CallBack{
        //返回天气首页
        void chooseCity(String cityName);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBack = (CallBack) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBack = null;
    }
}
