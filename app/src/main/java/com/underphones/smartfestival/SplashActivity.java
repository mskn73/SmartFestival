package com.underphones.smartfestival;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;


public class SplashActivity extends ActionBarActivity {


    private static final String TAG = "SplashActivity";
    private AsyncTask<Void, Void, Void> task;
    private TextView debug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        debug = (TextView) findViewById(R.id.textViewDevelop);

        if (BuildConfig.DEBUG)
            debug.setVisibility(View.VISIBLE);
        else
            debug.setVisibility(View.GONE);

        task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //Check server and database
                try {
                    Thread.sleep(2000);
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