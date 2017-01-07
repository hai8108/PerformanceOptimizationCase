package com.alvin.poc.memory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.alvin.androidlib.CaseBaseActivity;
import com.alvin.androidlib.LogX;

import java.util.ArrayList;
import java.util.List;

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
        findViewById(R.id.btn_hprof_viewer_and_analyzer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hprofViewerAndAnalyzer();
            }
        });
        findViewById(R.id.btn_leakcanary_case).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leakCanaryCase();
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

    class Info{
        public Bitmap bitmap;

        public Info() {
        }
    }
    private List<Info> infos=new ArrayList<>();
    private void hprofViewerAndAnalyzer() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                synchronized (MemoryCaseActivity.class) {
                    int size = 1;
                    Info info=new Info();
                    info.bitmap=BitmapFactory.decodeResource(MemoryCaseActivity.this.getResources(),R.drawable.tv);
                    for (int i = 0; i < size; i++) {
                        infos.add(info);
                    }
                }
            }
        }.start();

    }

    private void leakCanaryCase() {
        startActivity(new Intent(MemoryCaseActivity.this,LeakCanarySecondActivity.class));

    }

}
