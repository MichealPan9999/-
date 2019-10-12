package com.example.customerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.customerviewdemo.view.NineKeyboardView;

public class MyDialActivity extends Activity {

    private NineKeyboardView nineKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dial);
        initViews();
    }

    private void initViews() {

        /**
         * 初始化九宫格按键。
         */

        nineKeyboard = new NineKeyboardView((ViewGroup) MyDialActivity.this.findViewById(R.id.keyboard_group));
        nineKeyboard.init(keyboardFilterCallback);

    }

    private NineKeyboardView.onKeyboardFilterCallback keyboardFilterCallback = new NineKeyboardView.onKeyboardFilterCallback() {
        @Override
        public void onFilterChanged() {

        }

        //执行拨号动作
        @Override
        public void onDialNumberCall(String number, int type) {
            //TODO 执行拨号操作
            Log.d("panzq","number = "+number+" type = "+type);
        }


    };
}
