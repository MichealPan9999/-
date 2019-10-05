package com.example.customerviewdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.customerviewdemo.R;

/**
 * 一个视图从创建到显示过程中的主要方法：
 * 1.构造方法实例化类
 * 2.测量 measure(int ,int) --> onMeasure()
 * 如果当前view是一个ViiewGroup，还有义务测量孩子
 * 孩子有建议权
 * 3.onLayout指定位置
 * 指定控件的位置，一般View不用写这个方法，ViewGroup的时候才需要，一般View不需要重写该方法
 * 4.绘制视图 draw --> onDraw(canvas)
 * 根据上面两个方法参数，进入绘制
 */
public class MyToggleButton extends View implements View.OnClickListener, View.OnTouchListener {

    private Bitmap backgroupBitmap;
    private Bitmap slidingBitmap;
    //距离左边最大距离
    private int slideLeftMax;
    private Paint mPaint;
    private int slideLeft;

    private float startX;
    private float lastX;

    private boolean isOpen = false;
    //点击事件跟滑动事件使能
    private boolean isEnableClick = true;


    public MyToggleButton(Context context) {
        super(context);
        initView();
    }

    public MyToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public MyToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        backgroupBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        slideLeftMax = backgroupBitmap.getWidth() - slidingBitmap.getWidth();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置抗锯齿

        setOnClickListener(this);
        setOnTouchListener(this);
    }

    /**
     * 测量视图，目的是要显示的图片多长多宽
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //setMeasuredDimension 保存计算结果
        setMeasuredDimension(backgroupBitmap.getWidth(), backgroupBitmap.getHeight());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //        super.onDraw(canvas);
        canvas.drawBitmap(backgroupBitmap, 0, 0, mPaint);
        canvas.drawBitmap(slidingBitmap, slideLeft, 0, mPaint);
    }


    @Override
    public void onClick(View v) {
        if (isEnableClick) {
            isOpen = !isOpen;
            flushview();
        }
    }

    private void flushview() {
        if (isOpen) {
            slideLeft = slideLeftMax;
        } else {
            slideLeft = 0;
        }
        invalidate();//导致onDraw方法执行
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);//执行父类方法，如果没有加这句话，最后要return false否则点击功能无效
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //记录按下的坐标
                lastX = startX = event.getX();
                isEnableClick = true;
                break;
            case MotionEvent.ACTION_MOVE:

                //2.计算结束值
                float endX = event.getX();
                //3.计算偏移量
                float distanceX = endX - startX;
                slideLeft = (int) (slideLeft + distanceX);
                //屏蔽非法值

                if (slideLeft < 0) {
                    slideLeft = 0;
                } else if (slideLeft > slideLeftMax) {
                    slideLeft = slideLeftMax;
                }
                //刷新
                invalidate();

                //数据还原
                startX = event.getX();
                if (Math.abs(endX - lastX) > 5) {
                    isEnableClick = false;
                }
                break;
            case MotionEvent.ACTION_UP:

                if (!isEnableClick) {
                    if (slideLeft > slideLeftMax / 2) {
                        isOpen = true;
                    } else {
                        isOpen = false;
                    }
                    flushview();
                }
                break;
        }
        return true;
    }
}
