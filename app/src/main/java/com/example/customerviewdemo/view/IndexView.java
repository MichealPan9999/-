package com.example.customerviewdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 快速索引
 * 绘制快速索引的字母
 * 1.把26个字母放入数组中
 * 2.onMeasure计算每条的高itemHeight和宽itemwidth
 * 3.onDraw和wordWidth，wordHeight,wordX,wordY
 * <p>
 * 手指按下文字变色
 * 1.重写onToucheEvent()，返回true，再down和move的过程中计算该高亮那个值
 * int touchIndex = Y/itemHeight;
 * //强制绘制
 * 2.再onDraw()方法对应下表设置画笔变色
 * <p>
 * 3.再up的时候toucheIndex = -1; 强制绘制
 */
public class IndexView extends View {

    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};
    private int itemWidth;
    private int itemHeight;
    private Paint mPaint;
    private int touchIndex = -1;

    private onIndexChangeListener mIndexChangeListener;


    public IndexView(Context context) {
        super(context);
    }

    public IndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setTextSize(60);//设置字体大小
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public IndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public IndexView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        itemHeight = getMeasuredHeight() / words.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < words.length; i++) {
            if (touchIndex == i) {
                mPaint.setColor(Color.GRAY);
            } else {
                mPaint.setColor(Color.WHITE);
            }
            String word = words[i];
            Rect rect = new Rect();//方法一执行完，rect就有值了
            //0，1的取一个字母
            mPaint.getTextBounds(word, 0, word.length(), rect);
            int wordWidth = rect.width();
            int wordHeight = rect.height();

            float wordX = itemWidth / 2 - wordWidth / 2;
            float wordY = itemHeight / 2 + wordHeight / 2 + i * itemHeight;

            canvas.drawText(word, wordX, wordY, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float Y = event.getY();
                int index = (int) (Y / itemHeight);//得到字母的索引
                if (index != touchIndex) {
                    touchIndex = index;
                    invalidate();//强制绘制 onDraw方法会执行
                    if (mIndexChangeListener != null && touchIndex < words.length) {
                        mIndexChangeListener.onIndexChange(words[touchIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 字母下标索引变化的监听器
     */
    public interface onIndexChangeListener {

        /**
         * 当前字母下标位置发生变化的时候回调
         *
         * @param word
         */
        void onIndexChange(String word);
    }

    public onIndexChangeListener getmIndexChangeListener() {
        return mIndexChangeListener;
    }

    public void setmIndexChangeListener(onIndexChangeListener mIndexChangeListener) {
        this.mIndexChangeListener = mIndexChangeListener;
    }
}
