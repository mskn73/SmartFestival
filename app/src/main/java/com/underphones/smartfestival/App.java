package com.underphones.smartfestival;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

/**
 * Created by Juan on 07/03/2015.
 */
public class App extends Application {

        private static String TAG = "App";

        @Override
        public void onCreate() {
            super.onCreate();
            // your application starts from here

            Log.i(TAG, "Oncreate");
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
            // This is called when the overall system is running low on memory
            // and actively running processes should trim their memory usage
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            // Called by the system when the device configuration changes while your
            // component is running. Unlike activities Application doesn't restart when
            // a configuration changes
        }


        @Override
        public void onTerminate() {
            super.onTerminate();
            // This method is for use in emulated process environments only.
            // You can simply forget about it because it will never be called on real device
        }
}