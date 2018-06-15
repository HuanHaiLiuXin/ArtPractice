package com.jet.artpractice.chapter_4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jet.artpractice.MyUtils;
import com.jet.artpractice.R;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/6/15 10:38
 */
public class CircleView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int circleColor = Color.RED;
    private int mDefaultWidth = 400;
    private int mDefaultHeight = 400;

    public CircleView(Context context) {
        this(context,null,0);
    }
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        circleColor = typedArray.getColor(R.styleable.CircleView_circle_color,circleColor);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mDefaultWidth,mDefaultHeight);
        }else if(widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mDefaultWidth,heightSize);
        }else if(heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,mDefaultHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int screenWidth = MyUtils.getScreenMetrics(getContext()).widthPixels;
        int screenHeight = MyUtils.getScreenMetrics(getContext()).heightPixels;
        int radius = Math.min(Math.min(screenWidth-paddingLeft-paddingRight,getWidth()-paddingLeft-paddingRight),Math.min(screenHeight-paddingTop-paddingBottom,getHeight()-paddingTop-paddingBottom)) / 2;
        paint.setColor(circleColor);
        canvas.drawCircle(paddingLeft + radius,paddingTop + radius,radius,paint);
    }
}
