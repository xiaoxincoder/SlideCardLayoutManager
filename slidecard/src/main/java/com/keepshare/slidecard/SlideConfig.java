package com.keepshare.slidecard;

import com.keepshare.slidecard.utils.ScreenUtils;

public class SlideConfig {

    public static final int DEFAULT_LEFT_MARGIN = ScreenUtils.dp2px(20);
    public static final int DEFAULT_TOP_MARGIN = ScreenUtils.dp2px(20);
    public static final int DEFAULT_RIGHT_MARGIN = ScreenUtils.dp2px(20);
    public static final int DEFAULT_BOTTOM_MARGIN = ScreenUtils.dp2px(20);
    public static final int DEFAULT_OFFSET_SIZE = ScreenUtils.dp2px(10);

    public static final float DEFAULT_SCALE_SIZE = 0.1F;
    public static final int DEFAULT_MAX_SHOW_NUM = 4;
    public static final float DEFAULT_OFFSET_RATE = 0.1F;
    public static final float DEFAULT_HEIGHT_RATE = 1.0F;
    public static final float DEFAULT_WIDTH_RATE = 1.0F;

    public int maxShowItemNum = DEFAULT_MAX_SHOW_NUM;
    public float offsetRate = DEFAULT_OFFSET_RATE;
    public float widthRate = DEFAULT_WIDTH_RATE;
    public float heightRate = DEFAULT_HEIGHT_RATE;
    public int offsetSize = DEFAULT_OFFSET_SIZE;
    public float scaleSize = DEFAULT_SCALE_SIZE;


    public int getOffsetSize() {
        return offsetSize;
    }

    public void setOffsetSize(int offsetSize) {
        this.offsetSize = offsetSize;
    }

    public float getScaleSize() {
        return scaleSize;
    }

    public void setScaleSize(float scaleSize) {
        this.scaleSize = scaleSize;
    }

    public float getWidthRate() {
        return widthRate;
    }

    public void setWidthRate(float widthRate) {
        this.widthRate = widthRate;
    }

    public float getHeightRate() {
        return heightRate;
    }

    public void setHeightRate(float heightRate) {
        this.heightRate = heightRate;
    }

    public int getMaxShowItemNum() {
        return maxShowItemNum;
    }

    public void setMaxShowItemNum(int maxShowItemNum) {
        this.maxShowItemNum = maxShowItemNum;
    }

    public float getOffsetRate() {
        return offsetRate;
    }

    public void setOffsetRate(float offsetRate) {
        this.offsetRate = offsetRate;
    }
}
