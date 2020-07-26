package com.keepshare.slidecardlayouttest;

public class CardBean {

    private String picPath;
    private int index;

    public CardBean(int index, String picPath) {
        this.index = index;
        this.picPath = picPath;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
