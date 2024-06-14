package com.darshan.prodigy_ad_03;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.text.MessageFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
TextView textView;
MaterialButton reset,start,stop;
int second,minute,miliSecond;
long milisecond,startTime,timeBuff,UpdateTime=0L;
Handler handler;
private final Runnable runnable=new Runnable() {
    @Override
    public void run() {
        milisecond= SystemClock.uptimeMillis()-startTime;
        UpdateTime=timeBuff+milisecond;
        second=(int )(UpdateTime/1000);
        minute=second/60;
        second=second%60;
        miliSecond=(int)(UpdateTime%1000);

        textView.setText(MessageFormat.format("{0}:{1}:{2}",minute,String.format(Locale.getDefault(),"%02d",second),String.format(Locale.getDefault(),"%02d",miliSecond)));
        handler.postDelayed(this,0);
    }
};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        reset=findViewById(R.id.reset);
        start=findViewById(R.id.start);
        stop=findViewById(R.id.stop);
        handler=new Handler(Looper.getMainLooper());
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime=SystemClock.uptimeMillis();
                handler.postDelayed(runnable,0);
                reset.setEnabled(false);
                stop.setEnabled(true);
                start.setEnabled(false);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeBuff+=milisecond;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                stop.setEnabled(false);
                start.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                milisecond=0L;
                startTime=0L;
                timeBuff=0l;
                UpdateTime=0L;
                second=0;
                minute=0;
                miliSecond=0;
                textView.setText("00:00:00");

            }
        });

        textView.setText("00:00:00");

    }
}