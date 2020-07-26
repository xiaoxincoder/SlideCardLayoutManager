package com.keepshare.slidecard;

import android.graphics.Canvas;
import android.telecom.Call;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import java.util.List;

public class DragCallback extends ItemTouchHelper.SimpleCallback {

    private static final String TAG = "DragCallback";

    private ViewDragHelper viewDragHelper;

    private static final int SWIPE_DIRECT;
    private static final int DRAW_DIRECT;
    private CommonAdapter mAdapter;
    private SlideConfig config;

    static {
        SWIPE_DIRECT = ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT
                | ItemTouchHelper.UP
                | ItemTouchHelper.DOWN;

        DRAW_DIRECT = 0;
    }


    public DragCallback(CommonAdapter adapter) {
        super(DRAW_DIRECT, SWIPE_DIRECT);
        this.config = new SlideConfig();
        this.mAdapter = adapter;
    }

    public void setConfig(SlideConfig config) {
        this.config = config;
    }


    private final RecyclerView.OnItemTouchListener mOnItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            viewDragHelper.shouldInterceptTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            viewDragHelper.processTouchEvent(e);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.removeItemData(position);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX,
                            float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Log.i(TAG, "onChildDraw: " + isCurrentlyActive);
        double maxDistance = recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX * dX + dY * dY);
        double fraction = Math.min(distance / maxDistance, 1);
        int maxOffsetSize = (config.maxShowItemNum - 1) * config.offsetSize;

        // 显示的个数  4个
        int itemCount = recyclerView.getChildCount();

        for (int i = 0; i < itemCount; i++) {

            if (i == itemCount - 1) return;

            View view = recyclerView.getChildAt(i);

            int level = itemCount - i - 1;
            float offset = (float) (config.offsetSize * level - fraction * config.offsetSize);
            view.setTranslationY(Math.min(maxOffsetSize - config.offsetSize, offset));
            view.setScaleX((float) (1 - config.scaleSize * level + fraction * config.scaleSize));
        }
    }

}
