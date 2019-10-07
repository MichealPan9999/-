package com.example.customerviewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.customerviewdemo.R;

public class MyAttributeView extends View {
    private int age2;
    private String name2;
    private Bitmap bg2;

    public MyAttributeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取属性的三种方式
        //1.用命名空间去获取
        String age = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_age");
        String name = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_name");
        String bg = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_bg");
        Log.d("panzqww", "age = " + age + " , name = " + name + " bg = " + bg);
        //2.遍历属性集合
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            attrs.getAttributeName(i);
            Log.d("panzqww", attrs.getAttributeName(i) + " = " + attrs.getAttributeValue(i));
        }

        //3.使用系统工具，获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyAttributeView);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.MyAttributeView_my_age:
                    age2 = typedArray.getInt(index, 0);
                    break;
                case R.styleable.MyAttributeView_my_name:
                    name2 = typedArray.getString(index);
                    break;
                case R.styleable.MyAttributeView_my_bg:
                    Drawable drawable = typedArray.getDrawable(index);
                    BitmapDrawable drawable1 = (BitmapDrawable) drawable;
                    bg2 = drawable1.getBitmap();
                    break;
            }
        }
        typedArray.recycle();//回收

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawText(name2+"-----"+age2,50,50,paint);
        canvas.drawBitmap(bg2,50,50,paint);
    }
}
