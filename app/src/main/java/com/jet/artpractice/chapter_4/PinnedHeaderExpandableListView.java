package com.jet.artpractice.chapter_4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/6/20 15:16
 */
public class PinnedHeaderExpandableListView extends ExpandableListView implements AbsListView.OnScrollListener {
    private View mHeaderView;   //头部
    private int mHeaderWidth;   //头部宽度
    private int mHeaderHeight;  //头部高度
    private View mTouchTarget;  //头部中被点击的View实例,可能是自身
    private OnScrollListener mScrollListener;               //
    private OnHeaderUpdateListener mHeaderUpdateListener;   //
    private boolean mActionDownHappened = false;            //头部是否被点击
    private boolean mIsHeaderGroupClickable = true;         //头部是否可点击
    private int mTouchSlop;

    public PinnedHeaderExpandableListView(Context context) {
        super(context);
        initView();
    }
    public PinnedHeaderExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    public PinnedHeaderExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setFadingEdgeLength(0);
        setOnScrollListener(this);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        if(l != this){
            mScrollListener = l;
        }else{
            mScrollListener = null;
        }
        super.setOnScrollListener(this);
    }

    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener,boolean isHeaderGroupClickable) {
        mIsHeaderGroupClickable = isHeaderGroupClickable;
        super.setOnGroupClickListener(onGroupClickListener);
    }

    public void setOnHeaderUpdateListener(OnHeaderUpdateListener listener) {
        this.mHeaderUpdateListener = listener;
        if(listener == null){
            mHeaderView = null;
            mHeaderWidth = mHeaderHeight = 0;
            return;
        }
        mHeaderView = listener.getPinnedHeader();
        int firstVisiblePos = getFirstVisiblePosition();
        int firstVisibleGroupPos = getPackedPositionGroup(getExpandableListPosition(firstVisiblePos));
        listener.updatePinnedHeader(mHeaderView,firstVisibleGroupPos);
        requestLayout();
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mHeaderView == null){
            return;
        }
        measureChild(mHeaderView,widthMeasureSpec,heightMeasureSpec);
        mHeaderWidth = mHeaderView.getMeasuredWidth();
        mHeaderHeight = mHeaderView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(mHeaderView == null){
            return;
        }
        int delta = mHeaderView.getTop();
        mHeaderView.layout(0, delta, mHeaderWidth, delta + mHeaderHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("Jet","PinnedHeaderExpandableListView:onDraw");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(mHeaderView != null){
            drawChild(canvas,mHeaderView,getDrawingTime());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int pos = pointToPosition(x,y);
        if(mHeaderView != null && mHeaderView.getLeft() <= x && mHeaderView.getRight() >= x && mHeaderView.getTop() <= y && mHeaderView.getBottom() >= y){
            if(ev.getAction() == MotionEvent.ACTION_DOWN){
                mTouchTarget = getTouchTarget(mHeaderView,x,y);
                mActionDownHappened = true;
            }else if(ev.getAction() == MotionEvent.ACTION_UP){
                View touchTarget = getTouchTarget(mHeaderView,x,y);
                if(touchTarget == mTouchTarget && touchTarget.isClickable()){
                    //头部中被点击的View实例可点击,则执行其点击事件
                    mTouchTarget.performClick();
                    invalidate(new Rect(0,0,mHeaderWidth,mHeaderHeight));
                }else if(mIsHeaderGroupClickable){
                    //执行默认逻辑,切换对应Group的展开状态
                    int group = getPackedPositionGroup(getExpandableListPosition(pos));
                    if(group != INVALID_POSITION && mActionDownHappened){
                        if(isGroupExpanded(group)){
                            collapseGroup(group);
                        }else{
                            expandGroup(group);
                        }
                    }
                }
                mActionDownHappened = false;
            }
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void requestRefreshHeader(){
        refreshHeader();
        invalidate(new Rect(0,0,mHeaderWidth,mHeaderHeight));
    }
    private void refreshHeader(){
        if(mHeaderView == null){
            return;
        }
        int firstVisiblePos = getFirstVisiblePosition();
        int pos = firstVisiblePos + 1;
        int firstVisibleGroupPos = getPackedPositionGroup(getExpandableListPosition(firstVisiblePos));
        int group = getPackedPositionGroup(getExpandableListPosition(pos));
        if(group == firstVisibleGroupPos + 1){
            View view = getChildAt(1);
            if(view == null){
                return;
            }
            if(view.getTop() <= mHeaderHeight){
                int delta = mHeaderHeight - view.getTop();
                mHeaderView.layout(0,-delta,mHeaderWidth,mHeaderHeight - delta);
            }else{
                mHeaderView.layout(0,0,mHeaderWidth,mHeaderHeight);
            }
        }else{
            mHeaderView.layout(0,0,mHeaderWidth,mHeaderHeight);
        }
        if(mHeaderUpdateListener != null){
            mHeaderUpdateListener.updatePinnedHeader(mHeaderView,firstVisibleGroupPos);
        }
    }

    private boolean isTouchPointInView(View view, int x, int y){
        if(view.isClickable() && view.getTop() <= y && view.getBottom() >= y && view.getLeft() <= x && view.getRight() >= x){
            return true;
        }
        return false;
    }
    private View getTouchTarget(View view, int x, int y){
        if(!(view instanceof ViewGroup)){
            return view;
        }
        ViewGroup parent = (ViewGroup) view;
        View target = null;
        int childCount = parent.getChildCount();
        boolean customOrder = isChildrenDrawingOrderEnabled();
        for(int i = childCount - 1; i >= 0; i--){
            int childIndex = customOrder ? getChildDrawingOrder(childCount,i) : i;
            View v = parent.getChildAt(childIndex);
            if(isTouchPointInView(v,x,y)){
                target = v;
                break;
            }
        }
        if(target == null){
            target = parent;
        }
        return target;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(mScrollListener != null){
            mScrollListener.onScrollStateChanged(view,scrollState);
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //伴随滚动实时更新顶部
        if(totalItemCount > 0){
            refreshHeader();
        }
        if(mScrollListener != null){
            mScrollListener.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);
        }
    }

    public interface OnHeaderUpdateListener{
        /**
         * 返回1个View对象即可
         * 注意:view必须要有LayoutParams
         * @return
         */
        public View getPinnedHeader();
        public void updatePinnedHeader(View headerView, int firstVisibleGroupPos);
    }
}
