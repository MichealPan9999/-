package com.example.customerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.customerviewdemo.util.DensityUtil;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PopupWindowActivity extends AppCompatActivity {


    private EditText et_input;
    private ImageView iv_down_arrow;
    private PopupWindow popupWindow;
    private ListView listview;
    private ArrayList<String> msgs;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);

        et_input = findViewById(R.id.et_popup);
        iv_down_arrow = findViewById(R.id.iv_icon);


        et_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupWindow == null)
                {
                    popupWindow = new PopupWindow(PopupWindowActivity.this);
                    popupWindow.setWidth(et_input.getWidth());
                    popupWindow.setHeight(600);


                    popupWindow.setContentView(listview);
                    popupWindow.setFocusable(true);

                }
                popupWindow.showAsDropDown(et_input,0,0);
            }
        });

        iv_down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null)
                {
                    popupWindow = new PopupWindow(PopupWindowActivity.this);
                    popupWindow.setWidth(et_input.getWidth());
                    popupWindow.setHeight(DensityUtil.dip2px(PopupWindowActivity.this,200));


                    popupWindow.setContentView(listview);
                    popupWindow.setFocusable(true);

                }
                popupWindow.showAsDropDown(et_input,0,0);
            }
        });

        msgs = new ArrayList<>();
        listview = new ListView(this);
        for (int i =0;i<500;i++)
        {
            msgs.add(i+"---aaa"+i);
        }

        myAdapter = new MyAdapter();

        listview.setAdapter(myAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = msgs.get(position);
                et_input.setText(msg);
                if (popupWindow !=null && popupWindow.isShowing())
                {
                    popupWindow.dismiss();
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return msgs.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null)
            {
                convertView = View.inflate(PopupWindowActivity.this,R.layout.item_layout,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
                viewHolder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }

            final String msg = msgs.get(position);
            viewHolder.tv_msg.setText(msg);
            viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    msgs.remove(msg);
                    myAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    static class ViewHolder{

        TextView tv_msg;
        ImageView iv_delete;
    }
}
