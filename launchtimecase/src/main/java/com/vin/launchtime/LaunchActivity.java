package com.vin.launchtime;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        sleepInActivityCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sleepInActivityResume();
    }

    private void sleepInActivityCreate() {
        SystemClock.sleep(1000);
    }
    private void sleepInActivityResume() {
        SystemClock.sleep(1000);
    }
}
