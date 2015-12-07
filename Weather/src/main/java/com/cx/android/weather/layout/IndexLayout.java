package com.cx.android.weather.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cx.android.weather.R;
import com.cx.android.weather.model.Weather;
import com.cx.android.weather.model.WeatherIndex;

import java.util.List;

/**
 * 自定义天气指数布局
 * Created by chenxue on 2015/5/18.
 */
public class IndexLayout extends LinearLayout{
    private Context context;
    private TextView tvTitle1;
    private TextView tvZs1;
    private TextView tvDes1;
    private TextView tvTitle2;
    private TextView tvZs2;
    private TextView tvDes2;
    private TextView tvTitle3;
    private TextView tvZs3;
    private TextView tvDes3;
    private TextView tvTitle4;
    private TextView tvZs4;
    private TextView tvDes4;

    public IndexLayout(Context context) {
       this(context,null);
    }

    public IndexLayout(Context context, AttributeSet attrs) {
       this(context,attrs,0);
    }

    public IndexLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        if(isInEditMode()){return;}
        init();
    }

    private  void init(){
        LayoutInflater.from(context).inflate(R.layout.index_layout,this);
        View indexItemView1 = findViewById(R.id.index_item_ly_1);
        View indexItemView2 = findViewById(R.id.index_item_ly_2);
        View indexItemView3 = findViewById(R.id.index_item_ly_3);
        View indexItemView4 = findViewById(R.id.index_item_ly_4);

        tvTitle1 = (TextView) indexItemView1.findViewById(R.id.tv_index_title);
        tvZs1 = (TextView) indexItemView1.findViewById(R.id.tv_index_zs);
        tvDes1 = (TextView) indexItemView1.findViewById(R.id.tv_index_des);

        tvTitle2 = (TextView) indexItemView2.findViewById(R.id.tv_index_title);
        tvZs2 = (TextView) indexItemView2.findViewById(R.id.tv_index_zs);
        tvDes2 = (TextView) indexItemView2.findViewById(R.id.tv_index_des);

        tvTitle3 = (TextView) indexItemView3.findViewById(R.id.tv_index_title);
        tvZs3 = (TextView) indexItemView3.findViewById(R.id.tv_index_zs);
        tvDes3 = (TextView) indexItemView3.findViewById(R.id.tv_index_des);

        tvTitle4 = (TextView) indexItemView4.findViewById(R.id.tv_index_title);
        tvZs4 = (TextView) indexItemView4.findViewById(R.id.tv_index_zs);
        tvDes4 = (TextView) indexItemView4.findViewById(R.id.tv_index_des);
    }

    public void setData(Weather weather){
        List<WeatherIndex> indexList = weather.getIndexList();
        if(indexList != null && indexList.size() > 0){
            WeatherIndex index1 = indexList.get(0);
            WeatherIndex index2 = indexList.get(1);
            WeatherIndex index3 = indexList.get(2);
            WeatherIndex index4 = indexList.get(3);

            tvTitle1.setText(index1.getTipt());
            tvZs1.setText(index1.getZs());
            tvDes1.setText(index1.getDes());

            tvTitle2.setText(index2.getTipt());
            tvZs2.setText(index2.getZs());
            tvDes2.setText(index2.getDes());

            tvTitle3.setText(index3.getTipt());
            tvZs3.setText(index3.getZs());
            tvDes3.setText(index3.getDes());

            tvTitle4.setText(index4.getTipt());
            tvZs4.setText(index4.getZs());
            tvDes4.setText(index4.getDes());
        }
    }
}
