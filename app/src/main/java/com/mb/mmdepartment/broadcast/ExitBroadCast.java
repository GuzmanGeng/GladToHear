package com.mb.mmdepartment.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 退出程序的广播
 */
public class ExitBroadCast extends BroadcastReceiver {
    private static final String TAG = ExitBroadCast.class.getSimpleName();
    Activity activity = null;
    public ExitBroadCast(Activity activity) {
        this.activity = activity;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (null!=activity){
            activity.finish();
        }
    }
}
