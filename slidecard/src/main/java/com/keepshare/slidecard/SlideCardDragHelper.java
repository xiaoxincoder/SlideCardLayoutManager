package com.keepshare.slidecard;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

public class SlideCardDragHelper extends RecyclerView.ItemDecoration {

    private static final String TAG = "SlideCardDragHelper";

    private Context context;
    private RecyclerView parent;
    private ViewDragHelper mDragHelper;
    private GestureDetectorCompat mGestureDetector;
    private CommonAdapter mAdapter;
    private Spring springX, springY;
    private SlideConfig config;
    private float mTouchSlop;
    private View targetView;
    private float totalOffsetX, totalOffsetY;

    public SlideCardDragHelper(Context context, CommonAdapter mAdapter) {
        this.mAdapter = mAdapter;
        mGestureDetector = new GestureDetectorCompat(context,
                new MoveDetector());
        mGestureDetector.setIsLongpressEnabled(false);
        config = new SlideConfig();
    }

    public void attachToParent(RecyclerView parent) {
        if (parent == null) return;
        this.parent = parent;
        this.parent.addOnItemTouchListener(mOnItemTouchListener);
        this.mDragHelper = ViewDragHelper.create(parent, callback);
        initSpring();
    }


    private final ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            if (pointerId != 0) return false;
            int index =  parent.indexOfChild(child);
            Log.i(TAG, "tryCaptureView: pointId" + pointerId);
            totalOffsetX = totalOffsetY = 0;
            boolean isTarget = index == SlideConfig.DEFAULT_MAX_SHOW_NUM;
            if (isTarget) targetView = child;
            onStartDragging();
            return isTarget;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            totalOffsetX+=dx;
            totalOffsetY+= dy;
            Log.i(TAG, "onViewPositionChanged: left" + left + "top" + top);
            Log.i(TAG, "onViewPositionChanged: 左侧:" + changedView.getLeft() + "上侧" + changedView.getTop());
            startDrag(totalOffsetX, totalOffsetY);
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return 256;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.i(TAG, "onViewReleased: xvel" + xvel + "yvel" + yvel);
            animToTargetPosition(releasedChild, (int) xvel, (int) yvel);

        }
    };

    private void startDrag(float xvel, float yvel) {

        double maxDistance = parent.getWidth() * 0.5f;
        double distance = Math.sqrt(xvel * xvel + yvel * yvel);
        double fraction = Math.min(distance / maxDistance, 1);
        int maxOffsetSize = (config.maxShowItemNum - 1) * config.offsetSize;

        // 显示的个数  4个
        int itemCount = parent.getChildCount();

        for (int i = 0; i < itemCount - 1; i++) {

            View view = parent.getChildAt(i);

            int level = itemCount - i - 1;
            float offset = (float) (config.offsetSize * level - fraction * config.offsetSize);
//            Log.i(TAG, "startDrag: "+ offset);
            view.setTranslationY((int) Math.min(maxOffsetSize - config.offsetSize, offset));
            view.setScaleX((float) (1 - config.scaleSize * level + fraction * config.scaleSize));
        }
    }

    private void animToTargetPosition(View releasedView, float xvel, float yvel) {
        animTo(releasedView, (int) totalOffsetX,  (int)totalOffsetY);
    }


    private final RecyclerView.OnItemTouchListener mOnItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            boolean shouldIntercept = mDragHelper.shouldInterceptTouchEvent(e);
            boolean moveFlag = mGestureDetector.onTouchEvent(e);
            int action = e.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                if (mDragHelper.getViewDragState() == ViewDragHelper.STATE_SETTLING) {
                    mDragHelper.abort();
                }
//                orderViewStack();
                mDragHelper.processTouchEvent(e);
            }

            return shouldIntercept && moveFlag;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            try {
                mDragHelper.processTouchEvent(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };

    /**
     * 回弹动画
     */
    private void initSpring() {
        SpringConfig springConfig = SpringConfig.fromBouncinessAndSpeed(15, 20);
        SpringSystem mSpringSystem = SpringSystem.create();
        springX = mSpringSystem.createSpring().setSpringConfig(springConfig);
        springY = mSpringSystem.createSpring().setSpringConfig(springConfig);

        springX.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int xPos = (int) spring.getCurrentValue();
                setScreenX(xPos);
                startDrag(totalOffsetX-xPos - targetView.getLeft(), totalOffsetY);
//                parentView.onViewPosChanged(CardItemView.this);
            }
        });

        springY.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int yPos = (int) spring.getCurrentValue();
                setScreenY(yPos);
                startDrag(totalOffsetX, totalOffsetY-yPos-targetView.getTop());
//                parentView.onViewPosChanged(CardItemView.this);
            }
        });

    }

    public void setScreenX(int screenX) {
        targetView.offsetLeftAndRight(screenX - targetView.getLeft());
    }

    public void setScreenY(int screenY) {
        targetView.offsetTopAndBottom(screenY - targetView.getTop());
    }
    /**
     * 动画移动到某个位置
     */
    public void animTo(View targetView, int xPos, int yPos) {
        setCurrentSpringPos(targetView.getLeft(), targetView.getTop());
        springX.setEndValue(targetView.getLeft() - xPos);
        springY.setEndValue(targetView.getTop() - yPos);
    }

    /**
     * 设置当前spring位置
     */
    private void setCurrentSpringPos(int xPos, int yPos) {
        springX.setCurrentValue(xPos);
        springY.setCurrentValue(yPos);
    }

    public void onStartDragging() {
        springX.setAtRest();
        springY.setAtRest();
    }

    private class MoveDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx,
                                float dy) {
            // 拖动了，touch不往下传递
            return Math.abs(dy) + Math.abs(dx) > mTouchSlop;
        }
    }
}
