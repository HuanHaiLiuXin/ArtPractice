package com.jet.artpractice.chapter_4;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/6/22 14:33
 */
public class InvalidatePView extends LinearLayout {
    public InvalidatePView(Context context) {
        super(context);
        init();
    }

    public InvalidatePView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InvalidatePView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("Jet","P:onDraw");
    }
}
