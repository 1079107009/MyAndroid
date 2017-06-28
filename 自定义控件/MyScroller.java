package com.dtdream.geelyconsumer.view;

import android.os.SystemClock;

/**
 * 作者：杨光福 on 2016/5/16 11:54
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：xxxx
 */
public class MyScroller {

    /**
     * X轴的起始坐标
     */
    private float startY;
    /**
     * Y轴的起始坐标
     */
    private float startX;

    /**
     * 在X轴要移动的距离
     */
    private int distanceX;
    /**
     * 在Y轴要移动的距离
     */
    private int distanceY;
    /**
     * 开始的时间
     */
    private long startTime;

    /**
     * 总时间
     */
    private long totalTime = 500;
    /**
     * 是否移动完成
     * false没有移动完成
     * true:移动结束
     */
    private boolean isFinish;
    private float currY;

    /**
     * 得到坐标
     */
    public float getCurrY() {
        return currY;
    }

    public void startScroll(float startX, float startY, int distanceX, int distanceY) {
        this.startY = startY;
        this.startX = startX;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.startTime = SystemClock.uptimeMillis();//系统开机时间
        this.isFinish = false;
    }

    /**
     * 速度
     * 求移动一小段的距离
     * 求移动一小段对应的坐标
     * 求移动一小段对应的时间
     * true:正在移动
     * false:移动结束
     *
     * @return
     */
    public boolean computeScrollOffset() {
        if (isFinish) {
            return false;
        }

        long endTime = SystemClock.uptimeMillis();

        //这一小段所花的时间
        long passTime = endTime - startTime;
        if (passTime < totalTime) {
            //还没有移动结束
            //计算平均速度
            //移动这个一小段对应的距离
            float distanceSmallY = passTime * distanceY / totalTime;

            currY = startY + distanceSmallY;

        } else {
            //移动结束
            isFinish = true;
            currY = startY + distanceY;
        }

        return true;
    }
}
