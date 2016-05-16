package com.cx.android.weather.presentation.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cx.android.weather.R;
import com.cx.android.weather.data.model.City;

import java.util.List;

/**
 * Created by 陈雪 on 2016/3/31.
 */
public class SearchCityAdapter extends RecyclerView.Adapter<SearchCityAdapter.ViewHolder> {
    private List<City> cityDataList;
    private OnItemClickLisenter mLisenter;

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView cityAddress;
        public ViewHolder(View itemView) {
            super(itemView);
            cityAddress = (TextView) itemView.findViewById(R.id.search_city_adapter_item_tv);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_city_adapter_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(cityDataList == null || cityDataList.size() == 0)return;

        final City city = cityDataList.get(position);
        holder.cityAddress.setText(city.getAddress());
        holder.cityAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLisenter.onItemClick(city.getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return cityDataList.size();
    }

    public void setCityDataList(List<City> cityDataList) {
        this.cityDataList = cityDataList;
        notifyDataSetChanged();
    }

    public interface OnItemClickLisenter{
        void onItemClick(String cityName);
    }

    public void setmLisenter(OnItemClickLisenter mLisenter) {
        this.mLisenter = mLisenter;
    }
}
