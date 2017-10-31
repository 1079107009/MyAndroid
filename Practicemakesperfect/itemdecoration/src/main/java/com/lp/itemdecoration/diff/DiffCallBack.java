package com.lp.itemdecoration.diff;

import android.support.v7.util.DiffUtil;

import com.lp.itemdecoration.bean.CityBean;

import java.util.List;

/**
 * @author LiPin
 * @date 2017/10/31 15:11
 * 描述：
 */

public class DiffCallBack extends DiffUtil.Callback {

    private List<CityBean> mOldDatas, mNewDatas;

    public DiffCallBack(List<CityBean> mOldDatas, List<CityBean> mNewDatas) {
        this.mOldDatas = mOldDatas;
        this.mNewDatas = mNewDatas;
    }

    /**
     * 老数据集size
     *
     * @return
     */
    @Override
    public int getOldListSize() {
        return mOldDatas != null ? mOldDatas.size() : 0;
    }

    /**
     * 新数据集size
     *
     * @return
     */
    @Override
    public int getNewListSize() {
        return mNewDatas != null ? mNewDatas.size() : 0;
    }

    /**
     * 新老数据集在同一个postion的Item是否是一个对象？（可能内容不同，如果这里返回true，会调用下面的方法）
     * 被DiffUtil调用，用来判断 两个对象是否是相同的Item。
     * 例如，如果你的Item有唯一的id字段，这个方法就 判断id是否相等。
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldDatas.get(oldItemPosition).getCity().equals(mNewDatas.get(newItemPosition).getCity());
    }

    /**
     * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
     * DiffUtil用返回的信息（true false）来检测当前item的内容是否发生了变化
     * DiffUtil 用这个方法替代equals方法去检查是否相等。
     * 所以你可以根据你的UI去改变它的返回值
     * 例如，如果你用RecyclerView.Adapter 配合DiffUtil使用，你需要返回Item的视觉表现是否相同。
     * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        CityBean beanOld = mOldDatas.get(oldItemPosition);
        CityBean beanNew = mNewDatas.get(newItemPosition);
        return beanOld.getTag().equals(beanNew.getTag());
    }
}
