package com.example.customerviewdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.customerviewdemo.view.MyViewPager;

public class MyViewpagerActivity extends Activity {

    private MyViewPager myViewPager;
    private RadioGroup rg_main;
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
        rg_main = findViewById(R.id.rg_main);

        for (int i =0;i<ids.length;i++)
        {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);

            //添加到myviewPager中
            myViewPager.addView(imageView);
        }
        for (int i =0;i<myViewPager.getChildCount();i++)
        {
            RadioButton button = new RadioButton(this);
            button.setId(i);

            rg_main.addView(button);
            if (i == 0)
            {
                button.setChecked(true);
            }
        }
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Log.d("panzqww","onCheckedChanged ===== checkedId = "+checkedId);
                myViewPager.scrollToPager(checkedId);
            }
        });

        myViewPager.setmOnPageChangeListener(new MyViewPager.OnPageChangeListener() {
            @Override
            public void onPageChange(int page) {
                rg_main.check(page);
            }
        });

    }
}
