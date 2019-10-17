package com.example.customerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerviewdemo.view.SlideLayout;

import java.util.ArrayList;
import java.util.List;

public class SlideActivity extends Activity {

    private ListView listView;
    private List<MyContentBean> myContentBeans;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_slide);
        setContentView(R.layout.activity_slide);
        listView = findViewById(R.id.lv_main);
        myContentBeans = new ArrayList<MyContentBean>();
        for (int i = 0; i < 100; i++) {
            myContentBeans.add(new MyContentBean("content" + i + "_" + i));
        }
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
    }


    private class MyContentBean {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        public MyContentBean(String name) {
            this.name = name;
        }
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return myContentBeans.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(SlideActivity.this, R.layout.item_slide, null);
                viewHolder = new ViewHolder();
                viewHolder.item_content = convertView.findViewById(R.id.item_content);
                viewHolder.item_menu = convertView.findViewById(R.id.item_menu);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final MyContentBean myContentBean = myContentBeans.get(position);
            viewHolder.item_content.setText(myContentBean.getName());
            viewHolder.item_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyContentBean myContentBean1 = myContentBeans.get(position);
                    Toast.makeText(SlideActivity.this, "" + myContentBean1.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.item_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SlideLayout slideLayout = (SlideLayout) v.getParent();
                    slideLayout.closeMenu();
                    myContentBeans.remove(myContentBean);
                    notifyDataSetChanged();

                }
            });
            final SlideLayout slideLayout = (SlideLayout) convertView;
            slideLayout.setOnStateChangeListener(new MyOnstateChangeListener());
            return convertView;
        }

        class ViewHolder {
            TextView item_content;
            TextView item_menu;

        }
    }
    private SlideLayout slideLayout;
    private class MyOnstateChangeListener implements SlideLayout.OnStateChangeListener
    {
        @Override
        public void onClose(SlideLayout layout) {
            if (slideLayout == layout)
            {
                slideLayout = null;
            }
        }

        @Override
        public void onDown(SlideLayout layout) {

            if (slideLayout != null && slideLayout != layout)
            {
                slideLayout.closeMenu();
            }
        }

        @Override
        public void onOpen(SlideLayout layout) {
            slideLayout = layout;
        }
    }
}
