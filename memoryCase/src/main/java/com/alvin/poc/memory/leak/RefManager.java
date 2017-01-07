package com.alvin.poc.memory.leak;

import android.content.Context;

/**
 * Created by alvin on 17/1/6.
 */
public class RefManager {
    private static RefManager ourInstance = new RefManager();

    public static RefManager getInstance() {
        return ourInstance;
    }

    private RefManager() {
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }
}
