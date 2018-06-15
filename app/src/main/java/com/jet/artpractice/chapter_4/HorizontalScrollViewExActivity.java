package com.jet.artpractice.chapter_4;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jet.artpractice.BaseActivity;
import com.jet.artpractice.MyUtils;
import com.jet.artpractice.R;

import java.util.ArrayList;

public class HorizontalScrollViewExActivity extends BaseActivity {
    private HorizontalScrollViewEx mListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_view_ex);
        initView();
    }

    private void  initView(){
        mListContainer = findViewById(R.id.container);
        int screenWidth = MyUtils.getScreenMetrics(this).widthPixels;
        LayoutInflater inflater = getLayoutInflater();
        View page;
        TextView tv;
        for(int i = 0; i < 3; i++){
            page = inflater.inflate(R.layout.content_layout,mListContainer,false);
            page.getLayoutParams().width = screenWidth;
            tv = page.findViewById(R.id.title);
            tv.setText("page " + (i + 1));
            page.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(page);
            mListContainer.addView(page);
        }
    }
    private void createList(View layout){
        ListView listView = layout.findViewById(R.id.list);
        ArrayList<String> items = new ArrayList<String>();
        for(int i=0;i<50;i++){
            items.add("name " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.content_list_item,R.id.name,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HorizontalScrollViewExActivity.this, "click item",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
