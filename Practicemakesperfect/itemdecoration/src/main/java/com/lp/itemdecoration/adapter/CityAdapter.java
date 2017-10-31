package com.lp.itemdecoration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lp.itemdecoration.R;
import com.lp.itemdecoration.bean.CityBean;

import java.util.List;

/**
 * @author LiPin
 * @date 2017/10/31 10:27
 * 描述：
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private List<CityBean> mCityBeans;
    private LayoutInflater mInflater;

    public CityAdapter(Context context, List<CityBean> datas) {
        mCityBeans = datas;
        mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<CityBean> datas) {
        this.mCityBeans = datas;
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_city, parent, false));
    }

    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder holder, int position) {
        holder.tvCity.setText(mCityBeans.get(position).getCity());
    }

    @Override
    public int getItemCount() {
        return mCityBeans != null ? mCityBeans.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCity;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tvCity);
        }
    }
}
