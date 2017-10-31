package com.lp.itemdecoration.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.lp.itemdecoration.bean.CityBean;

import java.util.List;

/**
 * @author LiPin
 * @date 2017/10/31 10:40
 * 描述：
 */

public class TitleItemDecoration extends RecyclerView.ItemDecoration {

    private List<CityBean> mCityBeans;
    private Paint mPaint;
    /**
     * 用于存放测量文字Rect
     */
    private Rect mBounds;
    /**
     * title的高
     */
    private int mTitleHeight;
    private static final int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private static final int COLOR_TITLE_FONT = Color.parseColor("#FF000000");
    /**
     * title字体大小
     */
    private static int mTitleFontSize;

    public TitleItemDecoration(Context context, List<CityBean> cityBeans) {
        mCityBeans = cityBeans;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                30, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                16, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleFontSize);
    }

    public void setDatas(List<CityBean> datas) {
        this.mCityBeans = datas;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //Rv的item position在重置时可能为-1
        if (position > -1) {
            if (position == 0) {
                //第一个肯定有头部
                outRect.set(0, mTitleHeight, 0, 0);
            } else {
                if (null != mCityBeans.get(position).getTag()
                        && !mCityBeans.get(position).getTag()
                        .equals(mCityBeans.get(position - 1).getTag())) {
                    //当前位置的首字母和前一个不一样有头部
                    outRect.set(0, mTitleHeight, 0, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position > -1) {
                if (position == 0) {
                    //第一个肯定有头部
                    drawTitleArea(c, left, right, child, params, position);
                } else {
                    if (null != mCityBeans.get(position).getTag()
                            && !mCityBeans.get(position).getTag()
                            .equals(mCityBeans.get(position - 1).getTag())) {
                        //当前位置的首字母和前一个不一样有头部
                        drawTitleArea(c, left, right, child, params, position);
                    }
                }
            }
        }
    }

    private void drawTitleArea(Canvas c, int left, int right, View child,
                               RecyclerView.LayoutParams params, int position) {
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right,
                child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.getTextBounds(mCityBeans.get(position).getTag(), 0,
                mCityBeans.get(position).getTag().length(), mBounds);
        c.drawText(mCityBeans.get(position).getTag(), child.getPaddingLeft(),
                child.getTop() - params.topMargin - mTitleHeight / 2 + mBounds.height() / 2, mPaint);
    }

    //最后调用 绘制在最上层
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int position = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        int left = parent.findViewHolderForLayoutPosition(position).itemView.getPaddingLeft();
        String tag = mCityBeans.get(position).getTag();
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(0, 0, parent.getWidth(), mTitleHeight, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.getTextBounds(tag, 0, tag.length(), mBounds);
        c.drawText(tag, left, mTitleHeight / 2 + mBounds.height() / 2, mPaint);
    }
}
