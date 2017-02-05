package com.vin.launchtime;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by alvin on 17/2/4.
 */

public class LaunchApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        sleepInApplicationOnCreate();

    }

    private void sleepInApplicationOnCreate() {
        SystemClock.sleep(1000);
    }
}
