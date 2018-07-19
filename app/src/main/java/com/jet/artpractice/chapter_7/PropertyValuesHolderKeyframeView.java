package com.jet.artpractice.chapter_7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/7/19 18:22
 */
public class PropertyValuesHolderKeyframeView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int progress = 0;
    private RectF rectF = new RectF();
    private float radius;
    {

    }

    public PropertyValuesHolderKeyframeView(Context context) {
        super(context);
    }

    public PropertyValuesHolderKeyframeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PropertyValuesHolderKeyframeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        radius = Math.min(getWidth()/2,getHeight()/2) - 100;
        rectF.set(getWidth()/2 - radius,getHeight()/2-radius,getWidth()/2+radius,getHeight()/2+radius);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(24);
        paint.setColor(Color.GRAY);
        canvas.drawArc(rectF,120.0F,300.0F,false,paint);
        paint.setColor(Color.parseColor("#FF3861"));
        canvas.drawArc(rectF,120.0F,300.0F*progress/100,false,paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setStrokeWidth(1);
        paint.setColor(Color.BLACK);
        paint.setTextSize(64);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(progress+"%",getWidth()/2,getHeight()/2,paint);
    }

    public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
        this.invalidate();
    }
}
