package com.example.customerviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_youkuMenu;
    private Button btn_toadbar;
    private Button btn_topopupWindow;
    private Button btn_togglebutton;
    private Button btn_toMyAttribute;
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
        btn_youkuMenu.setOnClickListener(this);
        btn_toadbar.setOnClickListener(this);
        btn_topopupWindow.setOnClickListener(this);
        btn_togglebutton.setOnClickListener(this);
        btn_toMyAttribute.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.to_youku_menu:

                intent = new Intent(MainActivity.this,YouKuMenuActivity.class);
                startActivity(intent);
                break;

            case R.id.to_ad_bar:

                intent = new Intent(MainActivity.this,ADBarActivity.class);
                startActivity(intent);
                break;
            case R.id.to_popupwidow:

                intent = new Intent(MainActivity.this,PopupWindowActivity.class);
                startActivity(intent);
                break;
            case R.id.to_togglebutton:

                intent = new Intent(MainActivity.this,SwitchActivity.class);
                startActivity(intent);
                break;
            case R.id.to_myAttribute:

                intent = new Intent(MainActivity.this,MyAttributeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
