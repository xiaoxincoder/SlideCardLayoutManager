package com.keepshare.slidecard;

import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.rebound.Spring;

public class SlideCardLayoutManager extends RecyclerView.LayoutManager {

    private Context context;
    private SlideConfig config;

    public SlideCardLayoutManager(Context context) {
        this.context = context;
        config = new SlideConfig();
    }

    public void setConfig(SlideConfig config) {
        this.config = config;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {

        if (state.isPreLayout()) return;

        detachAndScrapAttachedViews(recycler);

        int maxOffsetSize = (config.maxShowItemNum - 1) * config.offsetSize;
        // 8个
        int itemCount = getItemCount();
        int bottomPosition = Math.min(config.maxShowItemNum, itemCount - 1);

        for (int i = bottomPosition; i >= 0; i--) {

            //复用
            View view = recycler.getViewForPosition(i);
            addView(view);

            measureChildWithMargins(view, 0, 0);

            // 求padding的总大小
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);

            // 布局
            layoutDecoratedWithMargins(view,
                    widthSpace / 2,
                    heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));

            view.setTranslationY(Math.min(maxOffsetSize - config.offsetSize, i * config.offsetSize));
            view.setScaleX(1 - 0.1f * i);
        }
    }



    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
