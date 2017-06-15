package com.nykidxxx.tappytap;
// Created on 4/1/2017


import android.graphics.Color;

public class Taps {

    private int mTapCount;
    private int mTapAmount;
    private int mColor;
    private boolean mLongClickFlag;

    public int getTapCount() {
        return mTapCount;
    }

    public void setTapCount(int tapCount) {
        mTapCount = tapCount;
    }

    public int getTapAmount() {
        return mTapAmount;
    }

    public void setTapAmount(int tapAmount) {
        mTapAmount = tapAmount;
    }

    public boolean isLongClickFlag() {
        return mLongClickFlag;
    }

    public void setLongClickFlag(boolean longClickFlag) {
        mLongClickFlag = longClickFlag;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }
}
