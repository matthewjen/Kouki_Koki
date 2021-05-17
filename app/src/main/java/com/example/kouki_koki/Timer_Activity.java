package com.example.kouki_koki;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Timer_Activity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonStartTime, buttonStopTime, returnToMenu;
    //private EditText edtTimerValue;
    private TextView textViewShowTime;
    private CountDownTimer countDownTimer;

    private long totalTimeCountInMilliseconds;
    private Queue<Integer> timeMap = new LinkedList<Integer>();
    int i = -1;
    ProgressBar mProgressBar, mProgressBar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Typeface MRegular = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Medium.ttf");

        buttonStartTime = (Button) findViewById(R.id.button_timerview_start);
        buttonStopTime = (Button) findViewById(R.id.button_timerview_stop);
        //to be implemented: returnToMenu = (Button) findViewById(R.id.ReturnToMenu);
        buttonStartTime.setTypeface(MRegular);
        buttonStopTime.setTypeface(MRegular);

        textViewShowTime = (TextView) findViewById(R.id.textView_timerview_time);
        //testing
        //edtTimerValue = (EditText) findViewById(R.id.textview_timerview_back);

        buttonStartTime.setOnClickListener(this);
        buttonStopTime.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar_timerview);
        mProgressBar1 = (ProgressBar) findViewById(R.id.progressbar1_timerview);

        textViewShowTime.setTypeface(MRegular);
        //edtTimerValue.setTypeface(MMedium);
        //timer test values
        timeMap.add(10);
        timeMap.add(5);
        timeMap.add(125);
        // TBI: returnToMenu.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {

            if (v.getId() == R.id.button_timerview_start) {
                setTimer();
                buttonStartTime.setVisibility(View.INVISIBLE);
                buttonStopTime.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);

                startTimer();

                mProgressBar1.setVisibility(View.VISIBLE);

            } else if (v.getId() == R.id.button_timerview_stop) {

                countDownTimer.cancel();
                countDownTimer.onFinish();
                mProgressBar1.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                //testing
                //edtTimerValue.setVisibility(View.VISIBLE);
                buttonStartTime.setVisibility(View.VISIBLE);
                buttonStopTime.setVisibility(View.INVISIBLE);

            }//TBI: else if (v.getId() == R.id.ReturnToMenu){}
    }
    private void setTimer(){
        int time = 0;

        if (!timeMap.isEmpty()) {
            time = timeMap.poll();
        } else Toast.makeText(Timer_Activity.this, "Nothing left!", Toast.LENGTH_LONG).show();
        totalTimeCountInMilliseconds = time * 1000;
        mProgressBar1.setMax(time * 1000);

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                mProgressBar1.setProgress((int) (leftTimeInMilliseconds));
                textViewShowTime.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));
            }

            @Override
            public void onFinish() {
                if(!timeMap.isEmpty()){
                    int minutes = timeMap.peek()/60, seconds = timeMap.peek() % 60;
                    if(minutes>=10){
                        if(seconds<10){
                            textViewShowTime.setText(minutes+":0"+seconds);
                        } else {
                            textViewShowTime.setText(minutes+":"+seconds);
                        }
                    } else {
                        if(seconds<10){
                            textViewShowTime.setText("0"+minutes+":0"+seconds);
                        } else {
                            textViewShowTime.setText("0"+minutes+":"+seconds);
                        }
                    }
                } else {
                    textViewShowTime.setText("Done!");
                    //TBI: returnToMenu.setVisibility(View.VISIBLE);
                }

                textViewShowTime.setVisibility(View.VISIBLE);
                buttonStartTime.setVisibility(View.VISIBLE);
                buttonStopTime.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar1.setVisibility(View.GONE);

            }

        }.start();
    }
}