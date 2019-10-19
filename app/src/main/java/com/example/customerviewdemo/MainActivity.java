package com.example.customerviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btn_youkuMenu;
    private Button btn_toadbar;
    private Button btn_topopupWindow;
    private Button btn_togglebutton;
    private Button btn_toMyAttribute;
    private Button btn_toMyViewPager;
    private Button btn_toMyDialView;
    private Button btn_toMyRippleView;
    private Button btn_toMyContactsView;
    private Button to_mySlideActivity;
    private Button to_WaveActivity;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_youkuMenu = findViewById(R.id.to_youku_menu);
        btn_toadbar = findViewById(R.id.to_ad_bar);
        btn_topopupWindow = findViewById(R.id.to_popupwidow);
        btn_togglebutton = findViewById(R.id.to_togglebutton);
        btn_toMyAttribute = findViewById(R.id.to_myAttribute);
        btn_toMyViewPager = findViewById(R.id.to_myViewPager);
        btn_toMyDialView = findViewById(R.id.to_myDialView);
        btn_toMyRippleView = findViewById(R.id.to_myRippleView);
        btn_toMyContactsView = findViewById(R.id.to_myContactsView);
        to_mySlideActivity = findViewById(R.id.to_mySlideActivity);
        to_WaveActivity = findViewById(R.id.to_WaveActivity);
        btn_youkuMenu.setOnClickListener(this);
        btn_toadbar.setOnClickListener(this);
        btn_topopupWindow.setOnClickListener(this);
        btn_togglebutton.setOnClickListener(this);
        btn_toMyAttribute.setOnClickListener(this);
        btn_toMyDialView.setOnClickListener(this);
        btn_toMyViewPager.setOnClickListener(this);
        btn_toMyRippleView.setOnClickListener(this);
        btn_toMyContactsView.setOnClickListener(this);
        to_mySlideActivity.setOnClickListener(this);
        to_WaveActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_youku_menu:

                intent = new Intent(MainActivity.this, YouKuMenuActivity.class);
                startActivity(intent);
                break;

            case R.id.to_ad_bar:

                intent = new Intent(MainActivity.this, ADBarActivity.class);
                startActivity(intent);
                break;
            case R.id.to_popupwidow:

                intent = new Intent(MainActivity.this, PopupWindowActivity.class);
                startActivity(intent);
                break;
            case R.id.to_togglebutton:

                intent = new Intent(MainActivity.this, SwitchActivity.class);
                startActivity(intent);
                break;
            case R.id.to_myAttribute:

                intent = new Intent(MainActivity.this, MyAttributeActivity.class);
                startActivity(intent);
                break;
            case R.id.to_myViewPager:

                intent = new Intent(MainActivity.this, MyViewpagerActivity.class);
                startActivity(intent);
                break;
            case R.id.to_myDialView:

                intent = new Intent(MainActivity.this, MyDialActivity.class);
                startActivity(intent);
                break;
            case R.id.to_myRippleView:

                intent = new Intent(MainActivity.this, MyRippleActivity.class);
                startActivity(intent);
                break;
            case R.id.to_myContactsView:

                intent = new Intent(MainActivity.this, MyContactsActivity.class);
                startActivity(intent);
                break;
            case R.id.to_mySlideActivity:

                intent = new Intent(MainActivity.this, SlideActivity.class);
                startActivity(intent);
                break;
            case R.id.to_WaveActivity:

                intent = new Intent(MainActivity.this, WaveActivity.class);
                startActivity(intent);
                break;
        }
    }
}
