package com.alvin.androidlib;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CaseBaseActivity extends AppCompatActivity {


    private Handler m_handler;
    private Context context;

    protected final String TAG=getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_handler = new Handler();
        context = this;
    }

    private final Handler getHandler() {
        return m_handler;
    }

    public final boolean postDelayed(Runnable r, long delay) {
        Handler h = getHandler();
        if (h == null) {
            return false;
        }
        return h.postDelayed(r, delay);
    }

    public final boolean post(Runnable r) {
        Handler h = getHandler();
        if (h == null) {
            return false;
        }
        return h.post(r);
    }


    public final void sendMessage(int what, Object obj) {
        Handler handler = getHandler();
        if (handler == null)
            return;
        Message msg = handler.obtainMessage(what);
        if (obj != null) {
            msg.obj = obj;
        }
        handler.sendMessage(msg);
    }

    public final void sendMessage(int what) {
        if (what == -1)
            return;
        sendMessage(what, null);
    }

    private Toast mToast;

    public final void toast(int resId) {

        if (mToast == null) {
            mToast = Toast.makeText(this, resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }


    public final void toast(String text) {

        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public final void postToast(final String text) {
        post(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(text);
                }
                mToast.show();
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            m_handler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            m_handler = null;
        }
    }
}
