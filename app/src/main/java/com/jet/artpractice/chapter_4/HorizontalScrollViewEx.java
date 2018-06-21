package com.jet.artpractice.chapter_4;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/6/15 11:42
 */
public class HorizontalScrollViewEx extends ViewGroup {
    //实现弹性滑动
    private Scroller scroller;
    //计算X,Y轴手指滑动速度
    private VelocityTracker velocityTracker;
    //子元素的宽,高,当前要滑动到的子元素索引值
    private int childWidth;
    private int childHeight;
    private int childIndex;
    //MotionEvent坐标值
    //onInterceptTouchEvent中获取到的坐标值
    private int mLastInterceptX;
    private int mLastInterceptY;
    private int mLastX;
    private int mLastY;

    public HorizontalScrollViewEx(Context context) {
        this(context, null, 0);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(scroller == null){
            scroller = new Scroller(context);
            velocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //首先测量每个子元素
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //子元素测量结束,即可获取单个子元素的测量宽高
        int childCount = getChildCount();
        if(childCount <= 0){
            setMeasuredDimension(0, 0);
            return;
        }
        childWidth = getChildAt(0).getMeasuredWidth();
        childHeight = getChildAt(0).getMeasuredHeight();
        //对自身进行测量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("Jet","onMeasure:widthSize-"+widthSize+";heightSize:"+heightSize+";childWidth:"+childWidth+";childHeight:"+childHeight);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //宽高都是wrap_content,则宽为子元素宽度之和,高度为子元素高度
            setMeasuredDimension(childWidth * childCount, childHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //宽是wrap_content,则宽为子元素宽度之和,高度为原始值
            setMeasuredDimension(childWidth * childCount, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //高是wrap_content,则高是子元素高度,宽度为原始值
            setMeasuredDimension(widthSize, childHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("Jet","onLayout:"+l+";"+t+";"+r+";"+b);
        int childCount = getChildCount();
        if (childCount <= 0) {
            return;
        }
        int left = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if(child.getVisibility() != View.GONE){
                child.layout(left, 0, left + child.getMeasuredWidth(), child.getMeasuredHeight());
                left += child.getMeasuredWidth();
            }else{
                Log.e("Jet","onLayout:child.getVisibility() == View.GONE:"+i);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                //弹性滑动中,则停止
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //判断滑动时横向还是纵向,横向则拦截,纵向放过
                int deltaX = x - mLastInterceptX;
                int deltaY = y - mLastInterceptY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    //横向滑动拦截
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                //ACTION_UP默认不拦截
                intercepted = false;
                break;
            default:
                break;
        }
        mLastInterceptX = x;
        mLastInterceptY = y;
        mLastX = x;
        mLastY = y;
        return intercepted;
    }

    private void smoothScroll(int deltaX, int deltaY, int duration) {
        scroller.startScroll(getScrollX(), 0, deltaX, 0, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //注意:velocityTracker.addMovement(event);要在这儿添加事件;
        //如果在MotionEvent.ACTION_UP时候才执行addMovement,会导致 监听时间过短(远小于1000ms),使获取到的速度远低于准确值
        velocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                scrollBy(mLastX - x, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                velocityTracker.computeCurrentVelocity(1000);
                float velocityX = velocityTracker.getXVelocity();
                if (Math.abs(velocityX) >= 50) {
                    //横向滑动速度>=50px/s,则根据滚动方向,自动滑动以完整展示下一个或上一个子元素
                    childIndex = velocityX > 0 ? childIndex - 1 : childIndex + 1;
                } else {
                    //速度很小,则自动滑动以完整展示 当前显示比例最大的 子元素
                    childIndex = (scrollX + childWidth / 2) / childWidth;
                }
                //childeIndex限定条件:0<=childIndex<=childCount-1
                childIndex = Math.max(0, Math.min(childIndex, getChildCount() - 1));
                //要横向滑动的像素值
                int deltaX = childWidth * childIndex - scrollX;
                smoothScroll(deltaX, 0, (int) (2000.0F * Math.abs(deltaX) / childWidth));
                velocityTracker.clear();
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        //统一返回true
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        velocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}