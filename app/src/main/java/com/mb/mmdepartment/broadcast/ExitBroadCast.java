package com.mb.mmdepartment.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
    public void onReceive(Context context, Intent intent) {
        if (null!=activity){
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
            activity.finish();
        }
    }
}
