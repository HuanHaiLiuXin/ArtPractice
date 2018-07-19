package com.jet.artpractice.chapter_7;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import com.jet.artpractice.R;

public class Chap7Activity extends AppCompatActivity {
    private ColorCircleView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chap7);
        getSupportActionBar().hide();
        v = findViewById(R.id.v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Chap7Activity.this,"你点老子试试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void test(View view) {
        /*v.animate().alpha(0.5F)
                .rotation(120)
                .scaleX(1.2F)
                .translationY(200)
                .x(300)
                .setInterpolator(new AnticipateOvershootInterpolator())
                .start();*/
        ObjectAnimator animator = ObjectAnimator.ofInt(v, "color", 0xff0000ff,0xffff0000).setDuration(2000);
// 使用自定义的 HslEvaluator
        animator.setEvaluator(new HsvEvaluator());
        animator.start();
    }
}
