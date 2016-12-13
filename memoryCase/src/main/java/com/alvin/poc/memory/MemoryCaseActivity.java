package com.alvin.poc.memory;

import android.os.Bundle;
import android.view.View;

import com.alvin.androidlib.CaseBaseActivity;
import com.alvin.androidlib.LogX;

public class MemoryCaseActivity extends CaseBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_case);
        findViewById(R.id.btn_allocation_tracking_case).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allocationTrackingCase();

            }
        });
        findViewById(R.id.btn_string_merge_string_builder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new StringBuilderThread().start();
            }
        });
    }

    private void allocationTrackingCase() {
        new StringAddThread().start();
    }

    private int MERGE_SIZE = 10000;

    class StringAddThread extends Thread {
        @Override
        public void run() {
            String result = "";
            int size = MERGE_SIZE;
            for (int i = 0; i < size; i++) {
                result += "This is a test\n";
            }
            String log=getClass().getSimpleName() + "end merge ";
            postToast(log);
            LogX.d(TAG,log);
        }
    }

    class StringBuilderThread extends Thread {
        @Override
        public void run() {
            String result = "";
            StringBuilder sb = new StringBuilder();
            int size = MERGE_SIZE;
            for (int i = 0; i < size; i++) {
                sb.append("This is a test\n");
            }
            result = String.valueOf(sb);
            LogX.d(TAG, result);
            postToast(getClass().getSimpleName() + "end merge ");
        }
    }
}
