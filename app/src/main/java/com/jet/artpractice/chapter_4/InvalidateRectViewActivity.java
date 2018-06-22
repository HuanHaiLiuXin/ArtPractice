package com.jet.artpractice.chapter_4;

import android.os.Bundle;
import android.view.View;

import com.jet.artpractice.BaseActivity;
import com.jet.artpractice.R;

public class InvalidateRectViewActivity extends BaseActivity {
    private InvalidateRectView invalidateRectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invalidate_rect_view);
        invalidateRectView = findViewById(R.id.invalidateV);
    }

    public void testInvalidateRect(View view) {
        invalidateRectView.invalidateRect();
    }
}
