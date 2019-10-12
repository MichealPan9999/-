package com.example.customerviewdemo;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.customerviewdemo.view.RippleImageView;

import org.w3c.dom.Text;

public class MyRippleActivity extends AppCompatActivity {
    private String[] dotText = {" . ", " . . ", " . . ."};
    private ValueAnimator valueAnimator;
    /**
     * 被呼叫人的名字
     */
    private String mCalledName = "Tom";
    private RippleImageView ripviewEndCalling;
    private TextView tvcontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ripple);
        ripviewEndCalling = findViewById(R.id.ripview_end_calling);
        tvcontent = findViewById(R.id.tv_calling_name_with_points);
        startLoadingAnimator();
    }

    private void startLoadingAnimator() {
        if (ripviewEndCalling != null) {
            ripviewEndCalling.startWaveAnimation();//记得在电话接通后将其stop掉
            ripviewEndCalling.requestFocus();
        }
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(0, 3).setDuration(2400);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int i = (int) animation.getAnimatedValue();
                    tvcontent.setText(mCalledName + dotText[i % dotText.length]);
                }
            });
        }
        valueAnimator.start();
    }

    private void stopLoadingAnimator() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
            valueAnimator = null;
        }
        if (ripviewEndCalling != null) {
            ripviewEndCalling.stopWaveAnimation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLoadingAnimator();
    }
}
