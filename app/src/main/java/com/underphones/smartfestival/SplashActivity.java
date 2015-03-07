package com.underphones.smartfestival;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


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
        debug = (TextView) findViewById(R.id.textViewDevelop);
        splashImage = (ImageView) findViewById(R.id.imageView);
        context = (Activity) this;
        if (BuildConfig.DEBUG)
            debug.setVisibility(View.VISIBLE);
        else
            debug.setVisibility(View.GONE);

        task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //Check server and database
                try {

                    Picasso.with(context).load("").centerCrop().into(splashImage);
                    Thread.sleep(100);


                } catch (Exception e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                showDebugInfo();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        // acciones que se ejecutan tras los milisegundos
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                }, 700);


            }
        }.execute();
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
}