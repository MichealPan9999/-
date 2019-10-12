package com.example.customerviewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.customerviewdemo.R;


public class RippleImageView extends RelativeLayout {

    private static final int SHOW_SPACING_TIME = 500;
    private static final int MSG_WAVE2_ANIMATION = 1;
    private static final int MSG_WAVE3_ANIMATION = 2;
    private static final int IMAMGEVIEW_SIZE = 80;
    /**
     * 三张波纹图片
     */
    private static final int SIZE = 3;

    /**
     * 动画默认循环播放时间
     */
    private int showSpacingTime = SHOW_SPACING_TIME;
    /**
     * 初始化动画集
     */
    private AnimationSet[] mAnimationSet = new AnimationSet[SIZE];
    /**
     * 水波纹图片
     */
    private ImageView[] imgs = new ImageView[SIZE];
    /**
     * 背景图片
     */
    private ImageView img_bg;
    /**
     * 水波纹和背景图片的大小
     */
    private float imageViewWidth = IMAMGEVIEW_SIZE;
    private float imageViewHeigth = IMAMGEVIEW_SIZE;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_WAVE2_ANIMATION:
                    Bundle bundle = msg.getData();
                    int index = bundle.getInt("index");
                    imgs[index].startAnimation(mAnimationSet[index]);
                    if (index == imgs.length - 1) {
                        mHandler.sendEmptyMessageDelayed(MSG_WAVE3_ANIMATION, 1000);
                    }
                    break;
                case MSG_WAVE3_ANIMATION:
                    startWaveAnimation();
                    break;
                default:
            }
            return false;
        }
    });

    public RippleImageView(Context context) {
        this(context, null);
    }

    public RippleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        getAttributeSet(context, attrs, defStyleAttr);

    }

    /**
     * 获取xml属性
     *
     * @param context context
     * @param attrs   attrs
     */
    private void getAttributeSet(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleImageView, defStyleAttr, 0);
        if (attrs != null) {
            showSpacingTime = typedArray.getInt(R.styleable.RippleImageView_showSpacingTime, SHOW_SPACING_TIME);
            imageViewWidth = typedArray.getDimension(R.styleable.RippleImageView_imageViewWidth, IMAMGEVIEW_SIZE);
            imageViewHeigth = typedArray.getDimension(R.styleable.RippleImageView_imageViewHeigth, IMAMGEVIEW_SIZE);
            Drawable imageBg = typedArray.getDrawable(R.styleable.RippleImageView_imageViewBg);
            LayoutParams params_bg = new LayoutParams((int) (imageViewWidth + getResources().getDimension(R.dimen.px10)), (int) (imageViewWidth + getResources().getDimension(R.dimen.px10)));
            //添加一个规则
            params_bg.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            // 添加背景图片
            img_bg = new ImageView(context);
            img_bg.setBackground(imageBg);
            addView(img_bg, params_bg);
            typedArray.recycle();
        }
    }

    private void initView(Context context) {
        setLayout(context);
        for (int i = 0; i < imgs.length; i++) {
            mAnimationSet[i] = initAnimationSet();
        }
    }

    /**
     * 开始动态布局
     */
    private void setLayout(Context context) {
        LayoutParams params = new LayoutParams((int) (imageViewWidth + getResources().getDimension(R.dimen.px54)), (int) (imageViewHeigth + getResources().getDimension(R.dimen.px54)));
        //添加一个规则
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        /**
         * 添加水波纹图片
         * */
        for (int i = 0; i < SIZE; i++) {
            imgs[i] = new ImageView(context);
            imgs[i].setImageResource(R.drawable.calling_ripple);
            addView(imgs[i], params);
        }

    }

    /**
     * 初始化动画集
     *
     * @return
     */
    private AnimationSet initAnimationSet() {
        AnimationSet as = new AnimationSet(true);
        //缩放度：变大两倍
        ScaleAnimation sa = new ScaleAnimation(1f, 2f, 1f, 2f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(showSpacingTime * 3);
        // 设置循环
        sa.setRepeatCount(0);
        sa.setFillAfter(true);
        //透明度
        AlphaAnimation aa = new AlphaAnimation(1, 0.1f);
        aa.setDuration(showSpacingTime * 3);
        aa.setFillAfter(true);
        //设置循环
        aa.setRepeatCount(0);
        as.addAnimation(sa);
        as.addAnimation(aa);
        return as;
    }

    private static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 开始水波纹动画
     */
    public void startWaveAnimation() {

        for (int i = 0; i < SIZE; i++) {
            Message message = mHandler.obtainMessage();
            message.what = MSG_WAVE2_ANIMATION;
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            message.setData(bundle);
            mHandler.sendMessageDelayed(message, SHOW_SPACING_TIME * i + 1000);
        }
    }

    /**
     * 停止水波纹动画
     */
    public void stopWaveAnimation() {
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].clearAnimation();
        }
        mHandler.removeMessages(MSG_WAVE3_ANIMATION);
        mHandler.removeMessages(MSG_WAVE2_ANIMATION);
    }

    /**
     * 获取播放的速度
     */
    public int getShowSpacingTime() {
        return showSpacingTime;
    }

    /**
     * 设计播放的速度，默认是800毫秒
     */
    public void setShowSpacingTime(int showSpacingTime) {
        this.showSpacingTime = showSpacingTime;
    }

}
