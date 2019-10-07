package com.example.customerviewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.customerviewdemo.util.MyScroller;

public class MyViewPager extends ViewGroup {
    /**
     * 手势识别器
     * 1.定义出来
     * 2.实例化，把想要的方法给重新实例化
     * 3.在onTouchEvent把事件传递给手势识别器
     */
    private GestureDetector detector;

    private float startX = 0;
    private float endX = 0;
    private int currentIndex;

    private MyScroller scroller;

    /**
     * 实例化手势识别器
     *
     * @param context
     */
    private void initview(Context context) {
        scroller = new MyScroller();
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                Log.d("panzqww", "-----onLongPress----");
            }

            /**
             *
             * @param e1        按下时的event
             * @param e2        离开时的event
             * @param distanceX x轴上移动的距离
             * @param distanceY y轴上移动的距离
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                scrollBy((int) distanceX, getScrollY());
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d("panzqww", "-----onDoubleTap----");
                return super.onDoubleTap(e);
            }
        });
    }

    public MyViewPager(Context context) {
        super(context);
        initview(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        detector.onTouchEvent(event);//把事件传递给手势识别器

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                endX = event.getX();
                int tmpIndex = currentIndex;
                if ((startX - endX) > getWidth()/2)
                {
                    tmpIndex ++;
                }else if((endX - startX)>getWidth()/2)
                {
                    tmpIndex --;

                }
                scrollToPager(tmpIndex);
                break;
        }
        return true;
    }

    /**
     * 屏蔽非法值
     * @param tmpIndex
     */
    private void scrollToPager(int tmpIndex) {
        if(tmpIndex < 0)
        {
            tmpIndex = 0;
        }else if(tmpIndex > getChildCount() -1)
        {
            tmpIndex = getChildCount() -1;
        }
        currentIndex = tmpIndex;

        //scrollTo(currentIndex*getWidth(),getScrollY());
        //解决滑动生硬问题
        //总距离
        float distanceX = currentIndex*getWidth() - getScrollX();
        scroller.startScroll(getScrollX(),getScrollY(),distanceX,0);

        invalidate();//调用computeScroll

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset())
        {
            float curX = scroller.getCurrX();
            scrollTo((int) curX,0);
            invalidate();
        }
    }
}
