package com.example.customerviewdemo.util;

import android.os.SystemClock;

public class MyScroller {
    //起始坐标
    private int startX;
    private int startY;
    //X轴移动距离
    private float distanceX;
    //Y轴移动距离
    private int distanceY;
    //开始时间
    private long startTime;
    /**
     * 是否移动完成
     * false：没有移动完成
     * true：移动结束
     */
    private boolean isFinish;

    //总时间500ms
    private long totalTime = 500;

    private float currX;
    //要跳转的页数，从起始位置到结束位置滑动了多少页，总时间 = 500 * pages

    public void startScroll(int scrollX, int scrollY, float distanceX, int i) {

        this.startX = scrollX;
        this.startY = scrollY;
        this.distanceX = distanceX;
        this.distanceY = i;
        this.startTime = SystemClock.uptimeMillis();//系统开机时间
        this.isFinish = false;
    }

    /**
     * 速度
     * 求移动一小段距离
     * 求移动一小段对应的坐标
     * 求移动一小段对应的时间
     * true :正在移动
     * false:移动结束
     *
     * @return
     */
    public boolean computeScrollOffset() {


        if (isFinish) {
            return false;
        }
        long endTime = SystemClock.uptimeMillis();
        long passTime = endTime - startTime;
        if (passTime < totalTime) {

            //float volecity = distanceX / totalTime;
            //移动一小段对应的距离
            float distanceSmallX = passTime * distanceX / totalTime;

            currX = startX + distanceSmallX;

        } else {
            isFinish = true;
            currX = startX + distanceX;
        }
        return true;
    }

    public float getCurrX() {
        return currX;
    }

    public void setCurrX(float currX) {
        this.currX = currX;
    }
}
