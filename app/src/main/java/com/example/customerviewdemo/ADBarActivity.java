package com.example.customerviewdemo;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerviewdemo.util.DensityUtil;

import java.util.ArrayList;

public class ADBarActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViews;

    private boolean isScrolled = false;

    private final int[] imageIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
    };

    private final String[] imageDescriptions = {
            "尚硅谷拔河争霸赛",
            "凝聚你我，放飞梦想",
            "抱歉没座位了",
            "7月就业名单全部曝光",
            "平均起薪11345元"
    };
    private int preSelectPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adbar);

        viewPager = findViewById(R.id.viewpager);
        tv_title = findViewById(R.id.tv_title);
        ll_point_group = findViewById(R.id.ll_point_group);

        imageViews = new ArrayList<ImageView>();

        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);

            imageViews.add(imageView);

            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_selector);
            int width = DensityUtil.dip2px(this,8);
            Log.d("panzqww","width ====== "+width);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);

            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
                params.leftMargin = width;
            }

            point.setLayoutParams(params);
            ll_point_group.addView(point);
        }
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 当前页面滚动了的时候回调这个方法
             * @param i 当前页面的位置
             * @param v 滑动页面的百分比
             * @param i1 再屏幕上滑动的像素
             */
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            /**
             * 当某个页面被选中的时候回调
             * @param i 被选中页面的位置
             */
            @Override
            public void onPageSelected(int i) {
                int realPosition = i % imageViews.size();
                tv_title.setText(imageDescriptions[realPosition]);
                ll_point_group.getChildAt(preSelectPosition).setEnabled(false);
                ll_point_group.getChildAt(realPosition).setEnabled(true);
                preSelectPosition = realPosition;
            }

            /**
             * 页面滚动状态变化的时候回调这个方法，静止 -> 滑动，滑动 -> 静止，静止->拖拽
             * @param i
             */
            @Override
            public void onPageScrollStateChanged(int i) {
                if (i == ViewPager.SCROLL_STATE_DRAGGING) {
                    Log.d("panzqww", "SCROLL_STATE_DRAGGING");
                    isScrolled = true;

                } else if (i == ViewPager.SCROLL_STATE_SETTLING) {
                    Log.d("panzqww", "SCROLL_STATE_SETTLING");

                } else if (i == ViewPager.SCROLL_STATE_IDLE) {
                    isScrolled = false;
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(0, 3000);
                    Log.d("panzqww", "SCROLL_STATE_IDLE");
                }
            }
        });
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageViews.size();//保证imabeViews.size的整数倍
        viewPager.setCurrentItem(item);
        handler.sendEmptyMessageDelayed(0, 3000);

    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //return imageViews.size();
            return Integer.MAX_VALUE;
        }

        /**
         * 比较View和object是否同一个实例
         *
         * @param view
         * @param o
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        /**
         * 相当于 getView方法
         *
         * @param container viewPager
         * @param position  当前实例化页面位置
         * @return
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Log.d("panzqww", "instantiateItem === " + position);
            int realPosition = position % imageViews.size();
            ImageView imageView = imageViews.get(realPosition);
            container.addView(imageView);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN://手指按下
                            handler.removeCallbacksAndMessages(null);
                            Log.d("panzqww", " ==== ACTION_DOWN ");
                            break;
                        case MotionEvent.ACTION_MOVE://手指移动
                            Log.d("panzqww", " ==== ACTION_MOVE ");
                            break;
                        case MotionEvent.ACTION_CANCEL://手指移动
                            Log.d("panzqww", " ==== ACTION_CANCEL ");
                            //handler.removeCallbacksAndMessages(null);
                            //handler.sendEmptyMessageDelayed(0, 3000);
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.d("panzqww", " ==== ACTION_UP ");
                            handler.sendEmptyMessageDelayed(0, 3000);
                            break;
                    }
                    return false;//让点击事件消费
                }
            });
            imageView.setTag(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int)v.getTag() % imageViews.size();
                    String text = imageDescriptions[position];
                    Toast.makeText(ADBarActivity.this,text,Toast.LENGTH_SHORT).show();
                }
            });
            return imageView;
        }

        /**
         * 释放资源
         *
         * @param container ViewPager
         * @param position  要释放的位置
         * @param object    要释放的页面
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View) object);
            Log.d("panzqww", "----destroyItem === " + position);

        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = viewPager.getCurrentItem() + 1;
            viewPager.setCurrentItem(item);
            handler.sendEmptyMessageDelayed(0, 4000);
        }
    };
}
