package com.nykidxxx.tappytap;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_FILE = "com.nykidxxx.thsharedpreferencesapp.preferences";
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;

    private static final String KEY_COUNT = "KEY_COUNT";
    private static final String KEY_TAP_AMOUNT = "KEY_TAP_AMOUNT";
    private static final String KEY_COLOR = "KEY_COLOR";

    public RelativeLayout relativeLayoutFF;
    public TextView textViewCounter;
    public Button buttonBuyStuff;
    public Button buttonPlusOneTap;
    private int tapCount;
    private int tapAmount;
    private boolean longClickFlag = false;

    private ColorWheel mColorWheel = new ColorWheel();

    public static final String TAG = MainActivity.class.getSimpleName();
    private int mColor = Color.parseColor(mColorWheel.mColors[8]);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);

        textViewCounter = (TextView) findViewById(R.id.textViewCounter);
        buttonBuyStuff = (Button) findViewById(R.id.buttonBuyStuff);
        buttonPlusOneTap = (Button) findViewById(R.id.buttonPlusOneTap);
        buttonPlusOneTap.setText("Tap +1");
        relativeLayoutFF = (RelativeLayout) findViewById(R.id.relativeLayoutFF);
        tapCount = 0;
        tapAmount = 1;

        mEditor = mSharedPreferences.edit();
        String keyTapCountString = mSharedPreferences.getString(KEY_COUNT, "");
//TODO: Shared preferences cant handle two strings...?
        if (keyTapCountString == "") {
            tapCount = 0;
            tapAmount = 1;
        } else {
            tapCount = Integer.parseInt(keyTapCountString);
        }

        textViewCounter.setText(tapCount + "");


        //TODO ===================
        View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickForAll(view);
            }
        };

        View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onLongClickForAll(view);
                return true;
            }
        };

        relativeLayoutFF.setOnClickListener(mOnClickListener);
        buttonBuyStuff.setOnClickListener(mOnClickListener);

        buttonPlusOneTap.setOnLongClickListener(mOnLongClickListener);
        buttonPlusOneTap.setOnClickListener(mOnClickListener);
        //TODO ===================

        Log.d(TAG, "Log for FFA OnCreate method");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_COUNT, tapCount + "");
        outState.putInt(KEY_COLOR, mColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        tapCount = Integer.parseInt(savedInstanceState.getString(KEY_COUNT));
        mColor = savedInstanceState.getInt(KEY_COLOR);

        textViewCounter.setText(tapCount + "");
        relativeLayoutFF.setBackgroundColor(mColor);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mEditor.putString(KEY_COUNT, tapCount+"");
        mEditor.putString(KEY_TAP_AMOUNT, tapAmount+"");
        mEditor.apply();
    }

    public void onClickForAll(View view) {

        switch (view.getId()) {
            case R.id.relativeLayoutFF:
                tapCount += tapAmount;
                textViewCounter.setText(tapCount + "");

                if (tapCount % 10 == 0) {
                    mColor = mColorWheel.getColor();
                    relativeLayoutFF.setBackgroundColor(mColor);
                }
                break;

            case R.id.buttonBuyStuff:
                if (tapCount >= 25) {
                    Toast.makeText(MainActivity.this, "You've bought air", Toast.LENGTH_LONG).show();
                    tapCount -= 25;
                    textViewCounter.setText(tapCount + "");
                    mColor = mColorWheel.getColor();
                    relativeLayoutFF.setBackgroundColor(mColor);
                }
                break;

            case R.id.buttonPlusOneTap:
                int multiplier = 1;

                if (longClickFlag == true) {
                    multiplier = 2;
                }

                if (tapCount >= 100 * multiplier) {

                    tapCount -= 100 * multiplier;
                    textViewCounter.setText(tapCount + "");
                    tapAmount += 1 * multiplier;
                    Toast.makeText(MainActivity.this, "Tap power is now " +
                            tapAmount + "!!!", Toast.LENGTH_LONG).show();
                    buttonPlusOneTap.setText("Tap +1");
                    longClickFlag = false;
                }
                else{
                    Toast.makeText(MainActivity.this, "Not enough taps!",
                            Toast.LENGTH_LONG).show();
                    buttonPlusOneTap.setText("Tap +1");
                    longClickFlag = false;
                }
                break;

            default:
                break;
        }
    }


    public void onLongClickForAll(View view) {
        switch (view.getId()) {
            case R.id.buttonPlusOneTap:
                longClickFlag = true;
                buttonPlusOneTap.setText("(x2)");
                break;

            default:
                break;
        }
    }
}










