package com.jet.artpractice.chapter_4;

import android.graphics.Outline;
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
        Outline outline = null;
    }

    public void goCircleView(View view) {
        jump(CircleViewActivity.class);
    }

    public void goHSEx(View view) {
        jump(HorizontalScrollViewExActivity.class);
    }

    public void goExListView(View view) {
        jump(ExListViewTestActivity.class);
    }

    public void goStickLayout(View view) {
        jump(StickLayoutActivity.class);
    }

    public void goInvalidateRect(View view) {
        jump(InvalidateRectViewActivity.class);
    }

    public void goViewOutlineProvider(View view) {
        jump(ViewOutlineProviderActivity.class);
    }
}
