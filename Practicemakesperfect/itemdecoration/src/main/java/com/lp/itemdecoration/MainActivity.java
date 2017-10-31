package com.lp.itemdecoration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CityAdapter mCityAdapter;
    private List<CityBean> mCityBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        mCityBeans.add(new CityBean("A", "安徽"));
        mCityBeans.add(new CityBean("B", "北京"));
        mCityBeans.add(new CityBean("F", "福建"));
        mCityBeans.add(new CityBean("G", "广州"));
        mCityBeans.add(new CityBean("G", "甘肃"));
        mCityBeans.add(new CityBean("G", "贵州"));
        mCityBeans.add(new CityBean("G", "广西"));
        mCityBeans.add(new CityBean("H", "河南"));
        mCityBeans.add(new CityBean("H", "河北"));
        mCityBeans.add(new CityBean("H", "湖南"));
        mCityBeans.add(new CityBean("H", "湖北"));
        mCityBeans.add(new CityBean("H", "黑龙江"));
        mCityBeans.add(new CityBean("J", "江苏"));
        mCityBeans.add(new CityBean("S", "陕西"));
        mCityBeans.add(new CityBean("S", "山西"));
        mCityBeans.add(new CityBean("S", "四川"));
        mCityBeans.add(new CityBean("S", "上海"));
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mCityAdapter = new CityAdapter(this, mCityBeans);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addItemDecoration(new TitleItemDecoration(this, mCityBeans));
        mRecyclerView.setAdapter(mCityAdapter);
    }
}
