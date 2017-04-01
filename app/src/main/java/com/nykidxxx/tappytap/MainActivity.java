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

    private RelativeLayout relativeLayoutFF;
    private TextView textViewCounter;
    private Button buttonBuyStuff;
    private Button buttonPlusOneTap;
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
        String editTextString = mSharedPreferences.getString(KEY_COUNT, "");

        if(editTextString == ""){
            tapCount = 0;
        }
        else {
            tapCount = Integer.parseInt(editTextString);
        }
        textViewCounter.setText(tapCount+"");

        View.OnClickListener listenToRelativeLayout = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapCount += tapAmount;
                textViewCounter.setText(tapCount+"");

                if(tapCount%10 == 0){
                mColor = mColorWheel.getColor();
                relativeLayoutFF.setBackgroundColor(mColor);
                }
            }
        };
        relativeLayoutFF.setOnClickListener(listenToRelativeLayout);

        buttonBuyStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tapCount >= 25){
                    Toast.makeText(MainActivity.this, "You've bought air", Toast.LENGTH_LONG).show();
                    tapCount -= 25;
                    textViewCounter.setText(tapCount+"");
                    mColor = mColorWheel.getColor();
                    relativeLayoutFF.setBackgroundColor(mColor);
                }
            }
        });

        View.OnLongClickListener toggleButtonFunction = new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                longClickFlag = true;
                buttonPlusOneTap.setText("(x2)");
                return true;
            }
        };

        buttonPlusOneTap.setOnLongClickListener(toggleButtonFunction);

        buttonPlusOneTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int multiplier = 1;

                if(longClickFlag == true) {
                    multiplier = 2;
                }

                if(tapCount >= 100 * multiplier){

                    tapCount -= 100 * multiplier;
                    textViewCounter.setText(tapCount+"");
                    tapAmount += 1 * multiplier;
                    Toast.makeText(MainActivity.this, "Tap now increases by " +
                            tapAmount + "!!!", Toast.LENGTH_LONG).show();
                    buttonPlusOneTap.setText("Tap +1");
                }
            }
        });



        Log.d(TAG, "Log for FFA OnCreate method");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_COUNT, tapCount+"");
        outState.putInt(KEY_COLOR, mColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        tapCount = Integer.parseInt(savedInstanceState.getString(KEY_COUNT));
        mColor = savedInstanceState.getInt(KEY_COLOR);

        textViewCounter.setText(tapCount+"");
        relativeLayoutFF.setBackgroundColor(mColor);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mEditor.putString(KEY_COUNT, textViewCounter.getText().toString());
        mEditor.apply();
    }
}












