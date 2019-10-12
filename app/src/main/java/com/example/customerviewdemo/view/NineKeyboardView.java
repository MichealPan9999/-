package com.example.customerviewdemo.view;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customerviewdemo.R;

import java.util.ArrayList;

public class NineKeyboardView implements View.OnClickListener {
    private final ViewGroup viewGroup;
    private EditText mFilterView;
    private int idLastCommand = 0;        //最后一个命令
    private ArrayList<String> filters = new ArrayList<>(20);
    private onKeyboardFilterCallback callback;

    private LinearLayout VideoCallButton;
    private LinearLayout AudioCallButton;

    public interface onKeyboardFilterCallback {
        void onFilterChanged();

        //拨号，type区别语音给视频拨号
        void onDialNumberCall(String number, int type);
    }

    public NineKeyboardView(ViewGroup keyboard) {
        // TODO Auto-generated constructor stub
        viewGroup = keyboard;
    }

    public void init(onKeyboardFilterCallback listener) {
        mFilterView = (EditText) viewGroup.findViewById(R.id.filter_edit);
        //禁止弹出系统键盘
        mFilterView.setShowSoftInputOnFocus(false);
        callback = listener;
        for (OneButtonInfo buttonInfo : buttons) {
            ViewGroup button = (ViewGroup) viewGroup.findViewById(buttonInfo.id);
            TextView textView1 = (TextView) button.findViewById(R.id.nineKeybordButtonTextView1);
            if (buttonInfo.id_text != 0) {
                //需要显示特定的字符串
                String label = viewGroup.getResources().getString(buttonInfo.id_text);
                textView1.setText(label);
            } else {
                if (!TextUtils.isEmpty(buttonInfo.firstText))
                    textView1.setText(buttonInfo.firstText);
            }
            //button.setNineKeyListener(onNineKeyListener);
            button.setOnClickListener(onButtonClickListerner);
        }
        mFilterView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mFilterView.setSelection(mFilterView.length());
            }
        });

        //第四行
        VideoCallButton = viewGroup.findViewById(R.id.button40);
        AudioCallButton = viewGroup.findViewById(R.id.button41);
        VideoCallButton.setOnClickListener(this);
        AudioCallButton.setOnClickListener(this);
    }

    public boolean isBackspace() {
        return idLastCommand == R.id.button32;
    }

    public boolean isClear() {
        return idLastCommand == R.id.button30;
    }

    /**
     * 获取当前过滤的字符串
     *
     * @param sep 字符串分隔符
     * @return 当前过滤的字符串。例如用户按下了 1 2ABC，则返回 1;2ABC(假定sep=;)
     * 如果为空表示不需要过滤
     */
    public String getFilterString(String sep) {
        if (filters.size() == 0) return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filters.size(); i++) {
            if ((i != 0) && !TextUtils.isEmpty(sep)) {
                sb.append(sep);
            }
            sb.append(filters.get(i));
        }
        return sb.toString();
    }

    /**
     * 获取正则表达式的过滤字符串
     *
     * @return 当前过滤的字符串。例如用户按下了 1 2ABC，则返回 [1][2ABC]
     * 如果为空表示不需要过滤
     */
    public String getRegularFilterString() {
        if (filters.size() == 0) return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filters.size(); i++) {
            sb.append("[")
                    .append(filters.get(i))
                    .append("]");
        }
        return sb.toString();
    }

    /**
     * 获取显示在TextView上的过滤字符串
     * 一般仅仅是第一个字符，当前版本下都是数字
     */
    private String getShowFilterString() {

        if (filters.size() == 0) return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filters.size(); i++) {
            sb.append(filters.get(i).charAt(0));
        }
        return sb.toString();
    }

    private View.OnClickListener onButtonClickListerner = (new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            idLastCommand = v.getId();
            //Log.i("AAA", "idLastCommand=" + idLastCommand + ", Clear=" + R.id.button30 + ", BS=" + R.id.button32);
            if (NineKeyboardView.this.isBackspace()) {
                //去除一项
                if (filters.size() > 0) {
                    int editSectionPostion = mFilterView.getSelectionStart();
                    if (editSectionPostion > 0) {
                        filters.remove(editSectionPostion - 1);
                    }
                }
            } else if (NineKeyboardView.this.isClear()) {
                //去除所有
                filters.clear();
            } else {
                OneButtonInfo buttonInfo = NineKeyboardView.this.findButtonById(v.getId());
                filters.add(buttonInfo.firstText);
            }
            mFilterView.setText(NineKeyboardView.this.getShowFilterString());
            if (callback != null) {
                callback.onFilterChanged();
            }
        }
    });

    /**
     * 定义每一个按钮的信息
     */
    private static class OneButtonInfo {
        int id;                    //按钮的ID
        String firstText;        //第一行文字。后面可能会转变成ID
        int id_text;            //按钮的文字（Clear等特殊按钮）!=0表示使用文字

        OneButtonInfo(int id, String firstText, String secondText, int id_text) {
            this.id = id;
            this.firstText = firstText;
            this.id_text = id_text;
        }
    }

    private static final OneButtonInfo[] buttons = new OneButtonInfo[]{
            //第一行
            new OneButtonInfo(R.id.button00, "1", null, 0),
            new OneButtonInfo(R.id.button01, "2", "ABC", 0),
            new OneButtonInfo(R.id.button02, "3", "DEF", 0),
            //第二行
            new OneButtonInfo(R.id.button10, "4", "GHI", 0),
            new OneButtonInfo(R.id.button11, "5", "JKL", 0),
            new OneButtonInfo(R.id.button12, "6", "MNO", 0),
            //第三行
            new OneButtonInfo(R.id.button20, "7", "PQRS", 0),
            new OneButtonInfo(R.id.button21, "8", "TUV", 0),
            new OneButtonInfo(R.id.button22, "9", "WXYZ", 0),
            //第四行
            new OneButtonInfo(R.id.button30, null, null, 0),
            new OneButtonInfo(R.id.button31, "0", null, 0),
            new OneButtonInfo(R.id.button32, null, null, 0),
    };

    private OneButtonInfo findButtonById(int id) {
        for (OneButtonInfo buttonInfo : buttons) {
            if (buttonInfo.id == id) return buttonInfo;
        }
        return null;
    }

    /**
     * 视频通话按键跟语音通话按键操作
     *
     * @param view
     */
    public static int VIDEO_DIAL_CALL = 0x100;
    public static int AUDIO_DIAL_CALL = 0x101;

    @Override
    public void onClick(View view) {

        String dialNumber = mFilterView.getText().toString();
        switch (view.getId()) {
            //处理视频拨号按键响应
            case R.id.button40:
                callback.onDialNumberCall(dialNumber, VIDEO_DIAL_CALL);

                break;
            //处理语音拨号按键响应
            case R.id.button41:
                callback.onDialNumberCall(dialNumber, AUDIO_DIAL_CALL);
                break;
        }
    }
}
