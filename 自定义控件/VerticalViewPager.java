package com.dtdream.geelyconsumer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LiPin on 2017/6/28 11:21.
 * 描述：可以竖直滑动的viewpager
 */

public class VerticalViewPager extends ViewGroup {
    private static final String TAG = "VerticalViewPager";
    /**
     * 手势识别器
     * 1.定义出来
     * 2.实例化-把想要的方法给重新
     * 3.在onTouchEvent()把事件传递给手势识别器
     */

    private GestureDetector detector;

    /**
     * 当前页面的下标位置
     */
    private int currentIndex;

    private float startX;
    private float startY;

    private MyScroller scroller;

    public VerticalViewPager(Context context) {
        this(context, null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        scroller = new MyScroller();
        //2.实例化手势识别器
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

            /**
             *
             * @param e1
             * @param e2
             * @param distanceX 在X轴滑动了的距离
             * @param distanceY 在Y轴滑动了的距离
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                Log.e(TAG, "distanceY--------" + distanceY);
                /**
                 *x:要在X轴移动的距离
                 *y:要在Y轴移动的距离
                 */
                scrollBy(0, (int) distanceY);

                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return super.onDoubleTap(e);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历孩子，给每个孩子指定在屏幕的坐标位置
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(0, i * getHeight(), getWidth(), (i + 1) * getHeight());
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        //3.把事件传递给手势识别器
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.记录坐标
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                float endY = event.getY();
                //下标位置
                int tempIndex = currentIndex;
                float dx = endX - startX;
                float dy = endY - startY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    // 左右滑动
                    return false;// 不处理
                }
                if ((startY - endY) > getHeight() / 4) {
                    //显示下一个页面
                    tempIndex++;
                } else if ((endY - startY) > getHeight() / 4) {
                    //显示上一个页面
                    tempIndex--;
                }
                //根据下标位置移动到指定的页面
                scrollToPager(tempIndex);
                break;
        }
        return true;
    }

    /**
     * 屏蔽非法值，根据位置移动到指定页面
     *
     * @param tempIndex
     */
    private void scrollToPager(int tempIndex) {
        if (tempIndex < 0) {
            tempIndex = 0;
        }
        if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }
        //当前页面的下标位置
        currentIndex = tempIndex;
        int distanceY = currentIndex * getHeight() - getScrollY();
        //移动到指定的位置
        scroller.startScroll(getScrollX(), getScrollY(), 0, distanceY);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            float currY = scroller.getCurrY();
            scrollTo(0, (int) currY);
            invalidate();
        }

    }
}
