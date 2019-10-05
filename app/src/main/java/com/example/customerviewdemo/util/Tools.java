package com.example.customerviewdemo.util;

import android.animation.ObjectAnimator;
import android.view.View;

public class Tools {
    public static void hideView(View view,int duration) {
        //以中心点旋转view.getWidth()/2,view.getHeight()
        /*RotateAnimation ra = new RotateAnimation(0, 180, view.getWidth() / 2, view.getHeight());
        ra.setDuration(duration);
        ra.setFillAfter(true);//动画停留再播放完成的状态
        view.startAnimation(ra);
        view.setEnabled(false);*/
        //改为属性动画
        //view.setRotation(); ===> rotation
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",0,180);
        animator.setDuration(500);
        animator.setStartDelay(duration);
        animator.start();
        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
    }

    public static void showView(View view,int duration) {
        /*RotateAnimation ra = new RotateAnimation(180, 360, view.getWidth() / 2, view.getHeight());
        ra.setDuration(duration);
        ra.setFillAfter(true);//动画停留再播放完成的状态
        view.startAnimation(ra);
        view.setEnabled(true);*/

        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",180,360);
        animator.setDuration(500);
        animator.setStartDelay(duration);
        animator.start();
        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
    }
}
