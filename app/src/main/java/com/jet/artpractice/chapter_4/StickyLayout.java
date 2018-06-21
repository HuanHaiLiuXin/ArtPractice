package com.jet.artpractice.chapter_4;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import java.util.NoSuchElementException;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/6/21 14:18
 */
public class StickyLayout extends LinearLayout {
    public interface OnGiveUpTouchEventListener {
        public boolean giveUpTouchEvent(MotionEvent event);
    }

    private View mHeader;
    private View mContent;
    private OnGiveUpTouchEventListener mGiveUpTouchEventListener;
    //header高度 单位px
    private int mOriginalHeaderHeight;
    private int mHeaderHeight;
    public static final int STATUS_EXPANDED = 1;
    public static final int STATUS_COLLAPSED = 2;
    private int mStatus = STATUS_EXPANDED;
    private int mTouchSlop;
    //分别记录上次滑动的坐标
    private int mLastX;
    private int mLastY;
    //onInterceptTouchEvent
    private int mLastXIntercept;
    private int mLastYIntercept;
    //
    private static final int TAN = 2;
    private boolean mIsSticky = true;
    private boolean mInitDataSucceed = false;
    private boolean mDisallowInterceptTouchEventOnHeader = false;

    public void setOnGiveUpTouchEventListener(OnGiveUpTouchEventListener l) {
        this.mGiveUpTouchEventListener = l;
    }

    public void setSticky(boolean isSticky) {
        this.mIsSticky = isSticky;
    }

    public void requestDisallowInterceptTouchEventOnHeader(boolean disallowIntercept) {
        this.mDisallowInterceptTouchEventOnHeader = disallowIntercept;
    }

    public StickyLayout(Context context) {
        super(context);
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initData() {
        int headerId = getResources().getIdentifier("sticky_header", "id", getContext().getPackageName());
        int contentId = getResources().getIdentifier("sticky_content", "id", getContext().getPackageName());
        if (headerId != 0 && contentId != 0) {
            mHeader = findViewById(headerId);
            mContent = findViewById(contentId);
            mOriginalHeaderHeight = mHeader.getMeasuredHeight();
            mHeaderHeight = mOriginalHeaderHeight;
            mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            if (mHeaderHeight > 0) {
                mInitDataSucceed = true;
            }
        } else {
            throw new NoSuchElementException("布局中缺少id为 sticky_header 和 sticky_content 的View");
        }
    }

    public void setOriginalHeaderHeight(int originalHeaderHeight) {
        this.mOriginalHeaderHeight = originalHeaderHeight;
    }

    public int getHeaderHeight() {
        return mHeaderHeight;
    }

    public void setHeaderHeight(int height) {
        if (!mInitDataSucceed) {
            initData();
        }
        if (height < 0) {
            height = 0;
        } else if (height > mOriginalHeaderHeight) {
            height = mOriginalHeaderHeight;
        }
        if (height == 0) {
            mStatus = STATUS_COLLAPSED;
        } else {
            mStatus = STATUS_EXPANDED;
        }
        if (mHeader != null && mHeader.getLayoutParams() != null) {
            mHeader.getLayoutParams().height = height;
            mHeader.requestLayout();
            mHeaderHeight = height;
        }
    }

    public void setHeaderHeight(int height, boolean modifyOriginalHeaderHeight) {
        if (modifyOriginalHeaderHeight) {
            setOriginalHeaderHeight(height);
        }
        setHeaderHeight(height);
    }

    public void smoothSetHeaderHeight(final int from, final int to, long duration, final boolean modifyOriginalHeaderHeight) {
        final int frameCount = (int) (duration / 1000f * 30) + 1;
        final float partation = (to - from) / (float) frameCount;
        new Thread("Thread#smoothSetHeaderHeight") {
            @Override
            public void run() {
                for (int i = 1; i <= frameCount; i++) {
                    final int height;
                    if (i == frameCount) {
                        height = to;
                    } else {
                        height = (int) (from + partation * i);
                    }
                    post(new Runnable() {
                        @Override
                        public void run() {
                            setHeaderHeight(height);
                        }
                    });
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (modifyOriginalHeaderHeight) {
                    setOriginalHeaderHeight(to);
                }
            }
        }.start();
    }

    public void smoothSetHeaderHeight(final int from, final int to, long duration) {
        smoothSetHeaderHeight(from, to, duration, false);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && (mHeader == null || mContent == null)) {
            initData();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int intercepted = 0;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mLastXIntercept = x;
                mLastYIntercept = y;
                intercepted = 0;
                break;
            case MotionEvent.ACTION_UP:
                mLastXIntercept = mLastYIntercept = 0;
                intercepted = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaY) <= Math.abs(deltaX) || Math.abs(deltaY) <= mTouchSlop) {
                    //Y轴上位移<=X轴位移 或 位移过低,不拦截
                    intercepted = 0;
                } else if (y <= getHeaderHeight()) {
                    //事件在Header上发生,只要Header没有被禁止拦截事件,一律拦截
                    if (mDisallowInterceptTouchEventOnHeader) {
                        intercepted = 0;
                    } else {
                        intercepted = 1;
                    }
                } else {
                    //事件在content上发生
                    if (deltaY > 0 && getHeaderHeight() >= mOriginalHeaderHeight) {
                        //Header已经完全展示,且向下滑,Header已经不能继续拉伸了,不拦截
                        intercepted = 0;
                    } else if (deltaY < 0 && getHeaderHeight() <= 0) {
                        //Header已经完全收缩,且向上滑,Header已经不能继续收缩了,不拦截
                        intercepted = 0;
                    } else {
                        //其余情况一律拦截
                        intercepted = 1;
                    }
                }
                break;
            default:
                break;
        }
        return intercepted != 0 && mIsSticky;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mIsSticky) {
            return true;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                //改变Header的高度
                int deltaY = y - mLastY;
                mHeaderHeight += deltaY;
                setHeaderHeight(mHeaderHeight);
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起,则根据当前Header的高度和原始高度的比例,决定完全展开,或者完全收缩Header
                if (mHeaderHeight <= 0 || mHeaderHeight >= mOriginalHeaderHeight) {
                } else {
                    int to = 0;
                    if (mHeaderHeight <= mOriginalHeaderHeight * 0.5) {
                        to = 0;
                        mStatus = STATUS_COLLAPSED;
                    } else {
                        to = mOriginalHeaderHeight;
                        mStatus = STATUS_EXPANDED;
                    }
                    //慢慢滑向终点
                    smoothSetHeaderHeight(mHeaderHeight, to, 500);
                }
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }
}