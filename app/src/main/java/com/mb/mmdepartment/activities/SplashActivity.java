package com.mb.mmdepartment.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.BaseActivity;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.login.Data;
import com.mb.mmdepartment.bean.login.getstartpic.Root;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.bean.user.User;
import com.mb.mmdepartment.biz.login.LoginBiz;
import com.mb.mmdepartment.biz.login.getpic.GetPic;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.network.OkHttp;
import com.mb.mmdepartment.tools.CustomToast;
import com.mb.mmdepartment.tools.log.Log;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

public class SplashActivity extends BaseActivity {
    private String path;
    private ImageView start_pic;
    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }
    @Override
    public void init(Bundle savedInstanceState) {
        mPushAgent.enable(mRegist);
        StatConfig.setDebugEnable(true);

        StatService.trackCustomBeginEvent(this, "onCreate", "");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        start_pic = (ImageView)findViewById(R.id.start_pic);
        getStartPic();
        initTask();
    }
    private  void  getStartPic(){
        GetPic pic = new GetPic();
        pic.getpic("3", "2", new RequestListener() {
            @Override
            public void onResponse(Response response) {
                try {
                    if(response.isSuccessful()) {
                        Gson gson = new Gson();
                        String json = response.body().string();
                        Log.i("json",json);
                        Root root = gson.fromJson(json,Root.class);
                        if(root.getStatus()==0){
                            path = root.getData().getAdverts().get(0).getAttach();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ImageLoader.getInstance().displayImage(path,start_pic);
                                }
                            });
                        }
                    }
                }catch (Exception e){e.printStackTrace();}
            }

            @Override
            public void onFailue(Request request, IOException e) {

            }
        });
    }

    /**
     * 设置toolbar
     * @param action
     * @param isTrue
     */
    @Override
    protected void setToolBar(ActionBar action,boolean isTrue) {

    }

    private IUmengRegisterCallback mRegist=new IUmengRegisterCallback() {
        @Override
        public void onRegistered(String s) {

        }
    };
    private void initTask() {
        final boolean isFirstInto= SPCache.getBoolean(
                BaseConsts.SharePreference.IS_FIRST_INTO, true);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isFirstInto) {
                    startActivity(SplashActivity.this, GuideActivity.class);
                    finish();
                }else {
                    String provience = SPCache.getString("provience","");
                    TApplication.user_id = SPCache.getString(BaseConsts.SharePreference.USER_ID,"");
                    if (!TextUtils.isEmpty(TApplication.user_id)){
                        LoginBiz biz=new LoginBiz();
                        biz.login(SPCache.getString(BaseConsts.SharePreference.USER_NAME, ""), SPCache.getString(BaseConsts.SharePreference.USER_PASS, ""), JPushInterface.getRegistrationID(SplashActivity.this), new RequestListener() {
                            @Override
                            public void onResponse(Response response) {
                                Gson gson = new Gson();
                                String json = null;
                                try {
                                    json = response.body().string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                final com.mb.mmdepartment.bean.login.Root root = gson.fromJson(json, com.mb.mmdepartment.bean.login.Root.class);
                                if (root.getStatus() == OkHttp.NET_STATE) {
                                    TApplication.user_id = root.getData().getUserid();
                                    TApplication.user_name = root.getData().getUsername();
                                    TApplication.integral = root.getData().getIntegral();
                                    TApplication.user_avatar = root.getData().getAvatar();
                                    TApplication.user_nick = root.getData().getNickname();
                                    TApplication.device_no = root.getData().getDevice_no();
                                    Data data = root.getData();
                                    TApplication.user.setUsername(data.getUsername());
                                    TApplication.user.setIntegral(data.getIntegral());
                                    TApplication.user.setNickname(data.getNickname());
                                    TApplication.user.setGender(data.getGender());
                                    TApplication.user.setYear(data.getYear());
                                    TApplication.user.setMonth(data.getMonth());
                                    TApplication.user.setDay(data.getDay());
                                    TApplication.user.setOccupation(data.getOccupation());
                                    TApplication.user.setArea(data.getArea());
                                    TApplication.user.setIncome_range(data.getIncome_range());
                                    TApplication.user.setContent(data.getContent());

                                    startActivity(SplashActivity.this, MainActivity.class);
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showToast(root.getError());
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailue(Request request, IOException e) {

                            }
                        });
                    }

                    if (provience == null || "".equals(provience)) {
                        startActivity(SplashActivity.this, WelcomActivity.class);
                        finish();
                    } else {
                        startActivity(SplashActivity.this, MainActivity.class, "provience", provience);
                        finish();
                    }
                }
            }
        },1500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TApplication.activities.remove(this);
    }
}
