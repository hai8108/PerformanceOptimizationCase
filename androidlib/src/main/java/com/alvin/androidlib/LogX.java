package com.alvin.androidlib;

import android.util.Log;

/**
 * Created by alvin on 16/4/13.
 */
public class LogX {

    private static boolean isDebug=true;

    public static void init(boolean debug){
        isDebug=debug;
    }

    private LogX(){

    }

    public static void v(String tag, String msg) {
        if (isDebug){
            Log.v(tag,msg);
        }
    }
    public static void v(String tag, String msg, Throwable tr) {
        if (isDebug){
            Log.v(tag,msg,tr);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug){
            Log.d(tag,msg);
        }
    }
    public static void d(String tag, String msg, Throwable tr) {
        if (isDebug){
            Log.d(tag,msg,tr);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug){
            Log.i(tag,msg);
        }
    }
    public static void i(String tag, String msg, Throwable tr) {
        if (isDebug){
            Log.i(tag,msg,tr);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug){
            Log.w(tag,msg);
        }
    }
    public static void w(String tag, String msg, Throwable tr) {
        if (isDebug){
            Log.w(tag,msg,tr);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug){
            Log.e(tag,msg);
        }
    }
    public static void e(String tag, String msg, Throwable tr) {
        if (isDebug){
            Log.e(tag,msg,tr);
        }
    }

    private static String getLocation() {
        final StackTraceElement[] traces = Thread.currentThread()
                .getStackTrace();
        StringBuilder sb=new StringBuilder();
        sb.append("");
        try{
            for (StackTraceElement trace : traces) {
                sb.append("[" + trace.getClassName() + ":"
                        + trace.getMethodName() + ":"
                        + trace.getLineNumber() + "] \n") ;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return new String(sb);

    }







}
