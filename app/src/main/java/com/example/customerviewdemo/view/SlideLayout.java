package com.example.customerviewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class SlideLayout extends FrameLayout {
    private View contentViiew;
    private View menuView;

    private int contentWidth;
    private int menuWidth;
    private int viewHeight;

    private Scroller myScroller;

    public SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        myScroller = new Scroller(context);
    }

    /**
     * 当布局加载完成的时候调用该方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentViiew = getChildAt(0);
        menuView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        contentWidth = contentViiew.getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();
        viewHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        menuView.layout(contentWidth, 0, contentWidth + menuWidth, viewHeight);
    }

    private float startX;
    private float startY;
    private float downX;//只赋值一次
    private float downY;//只赋值一次

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下记录坐标
                startX = event.getX();
                startY = event.getY();
                downX = startX;
                downY = startY;

                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();
                //计算偏移量
                float distanceX = endX - startX;

                int toScrollX = (int) (getScrollX() - distanceX);
                if (toScrollX < 0) {
                    toScrollX = 0;
                } else if (toScrollX > menuWidth) {
                    toScrollX = menuWidth;
                }
                scrollTo(toScrollX, getScrollY());
                startX = event.getX();
                startY = event.getY();
                //再X轴和Y轴滑动的距离
                float Dx = Math.abs(endX - downX);
                float Dy = Math.abs(endY - downY);
                if ((Dx > Dy) && (Dx > 8)) {
                    //水平方向滑动，响应侧滑，反拦截，事件给slideLayout
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                int totalScrollX = getScrollX();//偏移量
                if (totalScrollX < menuWidth / 2) {
                    //关闭 delete
                    closeMenu();
                } else {
                    //打开 delete
                    openMenu();
                }
                break;
        }
        return true;
    }

    public void closeMenu() {
        int distanceX = 0 - getScrollX();
        myScroller.startScroll(getScrollX(), getScrollY(), distanceX, getScrollY());
        invalidate();
        if (onStateChangeListener != null) {
            onStateChangeListener.onClose(this);
        }
    }

    public void openMenu() {
        int distanceX = menuWidth - getScrollX();
        myScroller.startScroll(getScrollX(), getScrollY(), distanceX, getScrollY());
        invalidate();
        if (onStateChangeListener != null) {
            onStateChangeListener.onOpen(this);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (myScroller.computeScrollOffset()) {
            scrollTo(myScroller.getCurrX(), myScroller.getCurrY());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下记录坐标
                startX = event.getX();
                downX = startX;
                if (onStateChangeListener != null) {
                    onStateChangeListener.onDown(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();
                //计算偏移量
                float distanceX = endX - startX;

                int toScrollX = (int) (getScrollX() - distanceX);
                if (toScrollX < 0) {
                    toScrollX = 0;
                } else if (toScrollX > menuWidth) {
                    toScrollX = menuWidth;
                }
                scrollTo(toScrollX, getScrollY());
                startX = event.getX();
                //再X轴和Y轴滑动的距离
                float Dx = Math.abs(endX - downX);
                if ((Dx > 8)) {
                    //水平方向滑动，响应侧滑，反拦截，事件给slideLayout
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                int totalScrollX = getScrollX();//偏移量
                if (totalScrollX < menuWidth / 2) {
                    //关闭 delete
                    closeMenu();
                } else {
                    //打开 delete
                    openMenu();
                }
                break;
        }
        return intercept;
    }

    /**
     * 监听slideLayout状态的改变
     */
    public interface OnStateChangeListener {
        void onClose(SlideLayout layout);

        void onDown(SlideLayout layout);

        void onOpen(SlideLayout layout);
    }

    private OnStateChangeListener onStateChangeListener;

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }
}
