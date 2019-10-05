package com.example.customerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.customerviewdemo.util.Tools;

public class YouKuMenuActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView icon_home;
    private ImageView icon_menu;
    private RelativeLayout level1;
    private RelativeLayout level2;
    private RelativeLayout level3;

    private boolean isShowLevel3 = true;
    private boolean isShowLevel2 = true;
    private boolean isShowLevel1 = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_ku_menu);

        icon_home = findViewById(R.id.icon_home);
        icon_menu = findViewById(R.id.icon_menu);
        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        icon_home.setOnClickListener(this);
        icon_menu.setOnClickListener(this);
        level1.setOnClickListener(this);
        level2.setOnClickListener(this);
        level3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_home:
                if (isShowLevel2) {
                    isShowLevel2 = false;
                    Tools.hideView(level2, 500);
                    if (isShowLevel3) {
                        isShowLevel3 = false;
                        Tools.hideView(level3, 200);
                    }
                } else {
                    isShowLevel2 = true;
                    Tools.showView(level2, 500);
                }
                break;
            case R.id.icon_menu:
                if (isShowLevel3) {
                    isShowLevel3 = false;
                    Tools.hideView(level3, 500);
                } else {
                    isShowLevel3 = true;
                    Tools.showView(level3, 500);
                }
                break;
            case R.id.level1:
                break;
            case R.id.level2:
                break;
            case R.id.level3:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //如果一级，二级三级菜单是显示的就全部隐藏
            if (isShowLevel1) {
                isShowLevel1 = false;
                Tools.hideView(level1, 500);
                if (isShowLevel2) {
                    isShowLevel2 = false;
                    Tools.hideView(level2, 200);
                    if (isShowLevel3) {
                        isShowLevel3 = false;
                        Tools.hideView(level3, 400);
                    }
                }
            } else {
                isShowLevel1 = true;
                Tools.showView(level1, 500);
                isShowLevel2 = true;
                Tools.showView(level2, 200);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
