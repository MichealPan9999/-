package com.example.customerviewdemo;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.customerviewdemo.view.IndexView;

import org.w3c.dom.Text;

public class MyContactsActivity extends Activity {

    private TextView tvIndexContent;
    private IndexView indexView;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);
        tvIndexContent = findViewById(R.id.tv_word);
        indexView = findViewById(R.id.iv_words);

        indexView.setmIndexChangeListener(new IndexView.onIndexChangeListener() {
            @Override
            public void onIndexChange(String word) {
                updateTextContent(word);
            }
        });
    }

    private void updateTextContent(String word) {
        tvIndexContent.setText(word);
        tvIndexContent.setVisibility(View.VISIBLE);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 再主线程中定义的，所以也是运行再主线程
                tvIndexContent.setVisibility(View.GONE);
            }
        }, 3000);
    }
}
