package com.example.customerviewdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.example.customerviewdemo.view.MyViewPager;

public class MyViewpagerActivity extends Activity {

    private MyViewPager myViewPager;
    private int[] ids = {R.drawable.a1,
            R.drawable.a2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.a5,
            R.drawable.a6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_viewpager);
        myViewPager = findViewById(R.id.myviewpager);

        for (int i =0;i<ids.length;i++)
        {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);

            //添加到myviewPager中
            myViewPager.addView(imageView);
        }
    }
}
