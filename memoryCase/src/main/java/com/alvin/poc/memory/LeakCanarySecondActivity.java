package com.alvin.poc.memory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alvin.androidlib.CaseBaseActivity;
import com.alvin.poc.PocApp;
import com.alvin.poc.memory.leak.RefManager;

public class LeakCanarySecondActivity extends CaseBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary_second);
        RefManager.getInstance().setContext(LeakCanarySecondActivity.this);
        finish();
    }

}
