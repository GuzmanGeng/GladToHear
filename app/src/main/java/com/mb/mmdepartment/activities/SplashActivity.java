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
import com.mb.mmdepartment.bean.login.getstartpic.Root;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.biz.login.LoginBiz;
import com.mb.mmdepartment.biz.login.getpic.GetPic;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.tools.log.Log;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;
import com.umeng.message.IUmengRegisterCallback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

public class SplashActivity extends BaseActivity {
    private final String TAG= SplashActivity.class.getSimpleName();
    private String path;
    private ImageView start_pic;
    private LuPinModel luPinModel;
    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onResume() {
        super.onResume();
        luPinModel = new LuPinModel();
        luPinModel.setName("appStart");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setOperationtime(sdf.format(new Date()));
        luPinModel.setState("start");
        luPinModel.setType("app");
//        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        luPinModel.setEndtime(sdf.format(new Date()));
        TApplication.luPinModels.add(luPinModel);
        StatService.onPause(this);
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
                    TApplication.user_name = SPCache.getString(BaseConsts.SharePreference.USER_NAME, "");
                    TApplication.integral = SPCache.getString(BaseConsts.SharePreference.USER_SCORE, "");
                    TApplication.user_avatar = SPCache.getString(BaseConsts.SharePreference.USER_LITTLE_IMAGE, "");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(TApplication.user_id)){
                                showToast("登陆成功");
                            }
                        }
                    });

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
}
