package com.nykidxxx.tappytap;
// Created on 4/1/2017

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class ExternalOnClickListener implements View.OnClickListener {

    Taps mTap;

    public ExternalOnClickListener(Taps taps){
        mTap = taps;

    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.relativeLayoutFF:
                break;
            case R.id.buttonBuyStuff:
                break;
            case R.id.buttonPlusOneTap:
                break;

            default:
                break;
        }


    }

}
