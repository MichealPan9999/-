package com.example.customerviewdemo;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.customerviewdemo.bean.Person;
import com.example.customerviewdemo.view.IndexView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyContactsActivity extends Activity {

    private TextView tvIndexContent;
    private IndexView indexView;
    private ListView lvMain;
    private ArrayList<Person> persons;

    private Handler mHandler = new Handler();
    private IndexAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);
        tvIndexContent = findViewById(R.id.tv_word);
        indexView = findViewById(R.id.iv_words);
        lvMain = findViewById(R.id.lv_main);

        indexView.setmIndexChangeListener(new IndexView.onIndexChangeListener() {
            @Override
            public void onIndexChange(String word) {
                updateTextContent(word);
                updateListView(word);
            }
        });
        initData();
        adapter = new IndexAdapter();
        lvMain.setAdapter(adapter);
    }

    private void updateListView(String word) {
        for (int i = 0; i < persons.size(); i++) {
            String listword = persons.get(i).getPinyin().substring(0, 1);
            if (word.equals(listword)) {
                lvMain.setSelection(i);
                return;
            }
        }
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

    /**
     * 初始化数据
     */
    private void initData() {

        persons = new ArrayList<>();
        persons.add(new Person("张晓飞"));
        persons.add(new Person("杨光福"));
        persons.add(new Person("胡继群"));
        persons.add(new Person("刘畅"));

        persons.add(new Person("钟泽兴"));
        persons.add(new Person("尹革新"));
        persons.add(new Person("安传鑫"));
        persons.add(new Person("张骞壬"));

        persons.add(new Person("温松"));
        persons.add(new Person("李凤秋"));
        persons.add(new Person("刘甫"));
        persons.add(new Person("娄全超"));
        persons.add(new Person("张猛"));

        persons.add(new Person("王英杰"));
        persons.add(new Person("李振南"));
        persons.add(new Person("孙仁政"));
        persons.add(new Person("唐春雷"));
        persons.add(new Person("牛鹏伟"));
        persons.add(new Person("姜宇航"));

        persons.add(new Person("刘挺"));
        persons.add(new Person("张洪瑞"));
        persons.add(new Person("张建忠"));
        persons.add(new Person("侯亚帅"));
        persons.add(new Person("刘帅"));

        persons.add(new Person("乔竞飞"));
        persons.add(new Person("徐雨健"));
        persons.add(new Person("吴亮"));
        persons.add(new Person("王兆霖"));

        persons.add(new Person("阿三"));
        persons.add(new Person("李博俊"));


        //排序
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });

    }

    class IndexAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return persons.size();
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
            if (convertView == null) {
                convertView = View.inflate(MyContactsActivity.this, R.layout.item_person, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_word = (TextView) convertView.findViewById(R.id.tv_word);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String word = persons.get(position).getPinyin().substring(0, 1);
            String name = persons.get(position).getName();
            viewHolder.tv_word.setText(word);
            viewHolder.tv_name.setText(name);
            if (position == 0) {
                viewHolder.tv_word.setVisibility(View.VISIBLE);
            } else {
                //得到一个位置对应的字母，如果当前的字母和上一个相同，隐藏，否则就显示
                String preWord = persons.get(position - 1).getPinyin().substring(0, 1);
                if (preWord.equals(word)) {
                    viewHolder.tv_word.setVisibility(View.GONE);
                } else {
                    viewHolder.tv_word.setVisibility(View.VISIBLE);
                }
            }
            return convertView;
        }


    }

    class ViewHolder {
        TextView tv_word;
        TextView tv_name;
    }
}
