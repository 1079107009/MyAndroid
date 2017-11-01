package com.lp.itemdecoration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lp.itemdecoration.adapter.CityAdapter;
import com.lp.itemdecoration.bean.CityBean;
import com.lp.itemdecoration.diff.DiffCallBack;
import com.lp.itemdecoration.itemdecoration.DividerItemDecoration;
import com.lp.itemdecoration.itemdecoration.TitleItemDecoration;
import com.lp.itemdecoration.view.IndexBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private IndexBar indexBar;
    private TextView mPressedShowTextView;
    private CityAdapter mCityAdapter;
    private List<CityBean> mCityBeans = new ArrayList<>();
    private TitleItemDecoration titleItemDecoration;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        setListener();
    }

    private void setListener() {
        indexBar.setIndexPressedListener(new IndexBar.IndexPressedListener() {
            @Override
            public void onIndexPressed(int index, String text) {
                mPressedShowTextView.setVisibility(View.VISIBLE);
                int position = getPosByTag(text);
                if (position != -1) {
                    mPressedShowTextView.setText(text);
                    mLayoutManager.scrollToPositionWithOffset(position, 0);
                }
            }

            @Override
            public void onMotionEventEnd() {
                mPressedShowTextView.setVisibility(View.GONE);
            }
        });
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
        titleItemDecoration = new TitleItemDecoration(this, mCityBeans);
        mRecyclerView = findViewById(R.id.recyclerView);
        indexBar = findViewById(R.id.indexBar);
        mPressedShowTextView = findViewById(R.id.tvSideBarHint);
        mCityAdapter = new CityAdapter(this, mCityBeans);
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addItemDecoration(titleItemDecoration);
        mRecyclerView.setAdapter(mCityAdapter);
    }

    public void onRefresh(View view) {
        try {
            List<CityBean> newDatas = new ArrayList<>();
            for (CityBean bean : mCityBeans) {
                //clone一遍旧数据 ，模拟刷新操作
                newDatas.add(bean.clone());
            }
            //模拟新增数据
            newDatas.add(new CityBean("X", "西安"));
            //模拟修改数据
            newDatas.get(0).setCity("Android+");
            //利用DiffUtil.calculateDiff()方法，传入一个规则DiffUtil.Callback对象
            // 和是否检测移动item的 boolean变量，得到DiffUtil.DiffResult 的对象
            DiffUtil.DiffResult diffResult = DiffUtil
                    .calculateDiff(new DiffCallBack(mCityBeans, newDatas), false);

            //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，
            // 传入RecyclerView的Adapter
            diffResult.dispatchUpdatesTo(mCityAdapter);

            //别忘了将新数据给Adapter
            mCityBeans = newDatas;
            titleItemDecoration.setDatas(mCityBeans);
            mCityAdapter.setDatas(mCityBeans);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据传入的pos返回tag
     *
     * @param tag
     * @return
     */
    private int getPosByTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return -1;
        }
        for (int i = 0; i < mCityBeans.size(); i++) {
            if (tag.equals(mCityBeans.get(i).getTag())) {
                return i;
            }
        }
        return -1;
    }
}
