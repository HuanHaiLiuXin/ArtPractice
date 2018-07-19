package com.jet.artpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jet.artpractice.chapter_4.Main4Activity;
import com.jet.artpractice.chapter_7.Chap7Activity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void go4(View view) {
        jump(Main4Activity.class);
    }

    public void go7(View view) {
        jump(Chap7Activity.class);
    }
}
