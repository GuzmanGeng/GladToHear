package com.mb.mmdepartment.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.tools.sp.SPCache;

/**
 * Created by Administrator on 2015/9/7.
 */
public class TDevide {
    /**
     * @param activity 保存屏幕宽高
     */
    public static void saveDisplaySize(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SPCache.putInt(BaseConsts.SharePreference.SCREEN_WIDTH, displayMetrics.widthPixels);
        SPCache.putInt(BaseConsts.SharePreference.SCREEN_HEIGHT, displayMetrics.heightPixels);
    }

    /**
     * dp转化为px
     * @param i
     * @param baseActivity
     * @return
     */
    public static int dip2px(int size, Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(metrics);
        return (int) ((float) size * metrics.density + 0.5);
    }

    /**
     * 获取当前进程
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
