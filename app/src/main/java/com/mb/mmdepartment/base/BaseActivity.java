package com.mb.mmdepartment.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.biz.lupinmodel.LupinModelBiz;
import com.mb.mmdepartment.listener.RequestListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.stat.StatService;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.broadcast.ExitBroadCast;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.tools.TDevide;
import com.mb.mmdepartment.tools.ToastControl;
import com.mb.mmdepartment.tools.log.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import cn.jpush.android.api.JPushInterface;

/**
 * 基类的activity.
 */
public abstract class BaseActivity extends AppCompatActivity implements ToastControl,DialogControl{
    public static boolean isForeground;
    public final String TAG=BaseActivity.class.getSimpleName();
    private ActionBar actionBar;
    public PushAgent mPushAgent;
    private static String lastToast=null;
    private static long lastToastTime=0;
    private boolean _isVisible=false;
    public static Map<String,String> paremas=new HashMap<>();
    private View progress;
    private LayoutInflater inflater;
    private LinearLayout progress_container;
    private View loadView;
    private String start_time;
    private String resum_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TApplication.activities.add(this);
        inflater = LayoutInflater.from(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayout());
        initParameters();
        init(savedInstanceState);
        initToolBar();
//        if(!isAppOnForeground()){
//            LuPing("appEnterBackground", "app", "background",new Date(),null);
//            TApplication.isAlive = false;
//        }
    }

    /**
     * 检查网络链接状态
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    public ActionBar getCustomBar() {
        return actionBar != null ? actionBar : null;
    }
    /**
     * 调用此方法可以初始化布局文件
     * @return
     */
    public abstract int getLayout();
    /**
     * 初始化参数的方法
     * @param savedInstanceState
     */
    public abstract void init(Bundle savedInstanceState);


    /**
     * 初始化固定参数
     */
    private void initParameters() {
        //保存屏幕尺寸信息
        TDevide.saveDisplaySize(this);
        mPushAgent=PushAgent.getInstance(this);
        mPushAgent.onAppStart();
    }

    /**
     * 不带参数的跳转
     * @param activity
     * @param clas
     */
    public void startActivity(Activity activity,Class clas){
        Intent intent=new Intent(activity,clas);
        startActivity(intent);
    }

    /**
     * intent带参数的跳转
     * @param activity
     * @param clas
     */
    public void startActivity(Activity activity,Class clas,String key,String extras){
        Intent intent=new Intent(activity,clas);
        intent.putExtra(key,extras);
        startActivity(intent);
    }
    /**
     * intent带多个参数的跳转
     * @param activity
     * @param clas
     */
    public void startActivity(Activity activity,Class clas,String[] keys,String[] extras){
        Intent intent=new Intent(activity,clas);
        for (int i=0;i<keys.length;i++){
            intent.putExtra(keys[i],extras[i]);
        }
        startActivity(intent);
    }

    /**
     *  程序是否在前台运行
     * @return
     */
    public boolean isAppOnForeground(){
        ActivityManager activityManager = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packagename = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        if(appProcessInfos == null){
            return false;
        }
        for(ActivityManager.RunningAppProcessInfo appProcessInfo:appProcessInfos){
            if(appProcessInfo.processName.equals(packagename) &&
                    appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                return true;
            }
        }
        return false;
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
//        LupinModelBiz lupinModelBiz = new LupinModelBiz();
//        String json = lupinModelBiz.getlist(JPushInterface.getRegistrationID(this));
//        lupinModelBiz.sendLuPinModel(json, TAG, new RequestListener() {
//            @Override
//            public void onResponse(Response response) {
//                if (response.isSuccessful()) {
//                    Gson gson = new Gson();
//                    try {
//                        String json = response.body().string();
//                        com.mb.mmdepartment.bean.lupinmodel.Root root =
//                                gson.fromJson(json, com.mb.mmdepartment.bean.lupinmodel.Root.class);
//                        if (root.getStatus() == 0) {
//                        } else {
//                        }
//                    } catch (Exception e) {
//                    }
//                }
//            }
//            @Override
//            public void onFailue(Request request, IOException e) {
//                TApplication.luPinModels = TApplication.luPinModelList;
//                TApplication.luPinModelList.clear();
//            }
//        });
    }

    /**
     * 后台
     * @param name
     * @param type
     * @param state
     * @param operation_time
     */
    public void LuPing(String name,String type,String state,Date operation_time){
        LuPinModel save = new LuPinModel();
        save.setName(name);
        save.setType(type);
        save.setState(state);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        start_time=sdf.format(operation_time);
        save.setOperationtime(start_time);
        TApplication.luPinModels.add(save);
    }

    /**
     * 带有source的
     * @param name
     * @param type
     * @param state
     * @param operation_time
     */
    public void LuPingWithSource(String name,String type,String state,String source,Date operation_time){
        LuPinModel save = new LuPinModel();
        save.setName(name);
        save.setType(type);
        save.setState(state);
        save.setSource(source);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        start_time=sdf.format(operation_time);
        save.setOperationtime(start_time);
        TApplication.luPinModels.add(save);
    }
    /**
     * 带有source的
     * @param name
     * @param type
     * @param state
     * @param operation_time
     */
    public void LuPingWithSelectId(String name,String type,String state,String source,String sel_id,Date operation_time){
        LuPinModel save = new LuPinModel();
        save.setName(name);
        save.setType(type);
        save.setState(state);
        save.setSource(source);
        save.setSelect_shop_id(sel_id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        start_time=sdf.format(operation_time);
        save.setOperationtime(start_time);
        TApplication.luPinModels.add(save);
    }

    /**
     * 页面离开时候的录屏
     * @param name
     * @param type
     * @param state
     * @param end_time
     */
    public void LuPingDestory(String name,String type,String state,Date end_time){
        LuPinModel save = new LuPinModel();
        save.setName(name);
        save.setType(type);
        save.setState(state);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        save.setEndtime(sdf.format(end_time));
        if (!TextUtils.isEmpty(resum_time)) {
            save.setOperationtime(resum_time);
        }
        TApplication.luPinModels.add(save);
    }

    /**
     * 初始化开始时间 必须在Luping之前调用
     * @param operation_time
     */
    public void startResumTime(Date operation_time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        resum_time=sdf.format(operation_time);
    }
    /**
     * 带有bundle的跳转
     * @param activity
     * @param bundle
     * @param key
     * @param clas
     */
    public void startActivity(Activity activity,Bundle bundle,String key,Class clas){
        Intent intent=new Intent(activity,clas);
        intent.putExtra(key, bundle);
        startActivity(intent);
    }

    /**
     * 实现parcelable接口的参数
     * @param activity
     * @param parcelable
     * @param key
     * @param clas
     */
    public void startActivity(Activity activity,Parcelable parcelable,String key,Class clas){
        Intent intent=new Intent(activity,clas);
        intent.putExtra(key,parcelable);
        startActivity(intent);
    }

    /**
     * 初始化toolbar
     */
    protected void initToolBar(){
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                setToolBar(actionBar,true);
            }
        } catch (NullPointerException e) {
        }
    }
    protected abstract void setToolBar(ActionBar action,boolean isTrue);
    public void showToast(int message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    @Override
    public void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, 17);
    }

    @Override
    public void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    @Override
    public void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, 17);
    }

    @Override
    public void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    @Override
    public void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, 17);
    }

    @Override
    public void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, 17, args);
    }

    @Override
    public void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, 17);
    }

    @Override
    public void showToast(int message, int duration, int icon,
                          int gravity) {
        showToast(TApplication.getContext().getString(message), duration, icon, gravity);
    }

    @Override
    public void showToast(int message, int duration, int icon,
                          int gravity, Object... args) {
        showToast(TApplication.getContext().getString(message, args), duration, icon, gravity);
    }

    /**
     * 弹出框toast
     * @param message
     * @param duration
     * @param icon
     * @param gravity
     */
    @Override
    public void showToast(String message, int duration, int icon,
                          int gravity) {
        if (message != null && !message.equalsIgnoreCase("")) {
            long time = System.currentTimeMillis();
            if (!message.equalsIgnoreCase(lastToast)
                    || Math.abs(time - lastToastTime) > 2000) {
                View view = LayoutInflater.from(TApplication.getContext()).inflate(
                        R.layout.toast_view, null);
                ((TextView) view.findViewById(R.id.title_tv)).setText(message);
                if (icon != 0) {
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setImageResource(icon);
                    (view.findViewById(R.id.icon_iv))
                            .setVisibility(View.VISIBLE);
                }
                Toast toast = new Toast(this);
                toast.setView(view);
                toast.setGravity(Gravity.BOTTOM | gravity, 0, TDevide.dip2px(84, this));
                toast.setDuration(duration);
                toast.show();
                lastToast = message;
                lastToastTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        _isVisible = true;
        super.onResume();
        startResumTime(new Date());
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
        StatService.onResume(this);
    }
    @Override
    protected void onPause() {
        _isVisible=false;
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
        StatService.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 开始动画
     * @param view
     */
    public void setProgress(View view) {
        if (progress != null) {
            return;
        }
        loadView = view;
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        ViewParent parent = view.getParent();
        FrameLayout container = new FrameLayout(TApplication.getContext());
        ViewGroup group = (ViewGroup) parent;
        int index = group.indexOfChild(view);
        group.removeView(view);
        group.addView(container, index, lp);
        container.addView(view);
        if (inflater != null) {
            progress = inflater.inflate(R.layout.loading_dialog, null);
            progress_container = (LinearLayout) progress
                    .findViewById(R.id.progress_container);
            container.addView(progress);
            progress_container.setTag(view);
            view.setVisibility(View.GONE);
        }
        group.invalidate();
    }

    /**
     * 结束动画
     */
    public void endProgress() {
        if (progress_container != null) {
            //这个与progress_container为Tag关系的是ListView对象，即progress_container.getTag()为ListView对象
            ((View) progress_container.getTag()).setVisibility(View.VISIBLE);

            progress_container.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);
        }
    }
}
