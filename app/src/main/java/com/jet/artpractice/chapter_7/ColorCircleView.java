package com.jet.artpractice.chapter_7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/7/19 17:55
 */
public class ColorCircleView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int color = Color.TRANSPARENT;

    public ColorCircleView(Context context) {
        super(context);
    }

    public ColorCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = Math.min(getWidth(),getHeight()) / 3;
        paint.setColor(color);
        canvas.drawCircle(getWidth()/2,getHeight()/2,radius,paint);
    }
}
