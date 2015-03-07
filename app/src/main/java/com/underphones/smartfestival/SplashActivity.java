package com.underphones.smartfestival;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.underphones.smartfestival.utils.Statistics;


public class SplashActivity extends ActionBarActivity {


    private static final String TAG = "SplashActivity";
    private ImageView splashImage;
    private AsyncTask<Void, Void, Void> task;
    private TextView debug;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImage = (ImageView) findViewById(R.id.imageView);
        context = (Activity) this;
        int delay = 200;
        updateImage(1);
        for (int i = 2; i < 13; i++) {
            final Integer finalI = i;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    updateImage(finalI);
                }
            }, i * delay);
        }


        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 14 * delay);


    }

    @Override
    protected void onStop() {
        if (task != null)
            task.cancel(true);
        super.onStop();
    }

    private void showDebugInfo() {
        if (!BuildConfig.DEBUG)
            return;
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void updateImage(int progress) {
        //Integer[] resolution = Statistics.getResolution(context);
        int drawable = 0;
        switch (progress) {
            case 0:
                drawable = R.drawable.ic_splash_image01;
                break;
            case 1:
                drawable = R.drawable.ic_splash_gajos01;
                break;
            case 2:
                drawable = R.drawable.ic_splash_gajos02;
                break;
            case 3:
                drawable = R.drawable.ic_splash_gajos03;
                break;
            case 4:
                drawable = R.drawable.ic_splash_gajos04;
                break;
            case 5:
                drawable = R.drawable.ic_splash_gajos05;
                break;
            case 6:
                drawable = R.drawable.ic_splash_gajos06;
                break;

            case 7:
                drawable = R.drawable.ic_splash_gajos07;
                break;

            case 8:
                drawable = R.drawable.ic_splash_gajos08;
                break;

            case 9:
                drawable = R.drawable.ic_splash_gajos09;
                break;

            case 10:
                drawable = R.drawable.ic_splash_gajos10;
                break;
            case 11:
                drawable = R.drawable.ic_splash_gajos11;
                break;
            case 12:
                drawable = R.drawable.ic_splash_image03;
                break;


        }
        //Picasso.with(context).load(drawable).resize(resolution[0], resolution[1]).centerCrop().into(splashImage);
        splashImage.setImageResource(drawable);
    }
}