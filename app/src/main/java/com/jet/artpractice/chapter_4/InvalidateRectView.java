package com.jet.artpractice.chapter_4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/6/22 12:34
 */
public class InvalidateRectView extends View {
    private int color0 = Color.RED;

    public InvalidateRectView(Context context) {
        super(context);
        init();
    }
    public InvalidateRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public InvalidateRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(color0);
        Log.e("Jet","V:onDraw");
    }

    public void invalidateRect(){
        color0 = Color.GREEN;
        int left = 100;
        int right = 300;
        int top = 100;
        int bottom = 300;
        invalidate(new Rect(left,top,right,bottom));
    }
}
