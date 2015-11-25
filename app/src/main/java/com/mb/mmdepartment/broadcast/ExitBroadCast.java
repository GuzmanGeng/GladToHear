package com.mb.mmdepartment.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.biz.lupinmodel.LupinModelBiz;
import com.mb.mmdepartment.listener.RequestListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import cn.jpush.android.api.JPushInterface;

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
    public void onReceive(final Context context, Intent intent) {
        LupinModelBiz lupinModelBiz = new LupinModelBiz();
        String json = lupinModelBiz.getlist(JPushInterface.getRegistrationID(context));
        lupinModelBiz.sendLuPinModel(json, TAG, new RequestListener() {
            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onFailue(Request request, IOException e) {

            }
        });
        for (AppCompatActivity activity : TApplication.activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        activity.finish();
    }
}
