package com.jet.artpractice.chapter_4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jet.artpractice.BaseActivity;
import com.jet.artpractice.R;

public class Main4Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
    }

    public void goCircleView(View view) {
        jump(CircleViewActivity.class);
    }

    public void goHSEx(View view) {
        jump(HorizontalScrollViewExActivity.class);
    }
}
