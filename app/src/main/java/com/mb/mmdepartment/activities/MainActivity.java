package com.mb.mmdepartment.activities;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.lupinmodel.LuPinModel;
import com.mb.mmdepartment.bean.referesh.RefereshRoot;
import com.mb.mmdepartment.biz.referesh.RefereshBiz;
import com.mb.mmdepartment.fragment.main.MainFragment;
import com.mb.mmdepartment.listener.RequestListener;
import com.mb.mmdepartment.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tencent.stat.StatService;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends FragmentActivity implements View.OnClickListener,RequestListener{
    private MainFragment mainFragment;
    private ImageView user_center;
    private TextView tv_search,main_local_tv;
    private String provence;
    private SlidingMenu slidingMenu;
    private TextView tv_userName;
    private TextView tv_score;
    private CircleImageView iv_hearder_default;
    private String net_verson_code;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    PackageInfo info = null;
                    PackageManager manager= getPackageManager();
                    try {
                        info = manager.getPackageInfo(getPackageName(), 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    String version_code = String.valueOf(info.versionCode);
                    int first_net_version_code = Integer.valueOf(net_verson_code.substring(0, 1));
                    int second_net_version_code = Integer.valueOf(net_verson_code.substring(2, 3));
                    int third_net_version_code = Integer.valueOf(net_verson_code.substring(4, 5));

                    int first_version_code = Integer.valueOf(version_code.substring(0, 1));
                    int second_version_code = Integer.valueOf(version_code.substring(1, 2));
                    int third_version_code = Integer.valueOf(version_code.substring(2, 3));

                    if (first_net_version_code > first_version_code) {
                        setUpdataDialog();
                    }else if (second_net_version_code > second_version_code) {
                        setUpdataDialog();
                    }else if (third_net_version_code > third_version_code) {
                        setUpdataDialog();
                    }
                    break;
                case 5:
                    openFile(file);
                    break;
            }
        }
    };
    private AlertDialog.Builder dialog;
    private String filePath;
    private File file;
    private String start_time;
    private String resum_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        provence=intent.getStringExtra("provience");
        mainFragment=new MainFragment();
        changeFragment(mainFragment);
        initSlideMenu();
        initView();
        setListeners();
    }

    private void initSlideMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.RIGHT);
        // 设置触摸屏幕的模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        slidingMenu.setBehindOffsetRes(R.dimen.hundred_eighty_dip);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        final View view = LayoutInflater.from(this).inflate(R.layout.activity_user_center, null);
        slidingMenu.setMenu(view);
        initSlideMenuView(view);
        tv_userName= (TextView)view.findViewById(R.id.user_center_login_username);
        tv_score=(TextView)view.findViewById(R.id.user_center_regist_score);
        iv_hearder_default = (CircleImageView) view.findViewById(R.id.headImageView);
        if (!TextUtils.isEmpty(TApplication.user_id)) {
            tv_score.setClickable(false);
            String score = TApplication.integral;
            if (TextUtils.isEmpty(score)) {
                tv_score.setText("0积分");
            } else {
                tv_score.setText(score + "积分");
            }
            tv_userName.setText(TApplication.user_name);
            ImageLoader.getInstance().displayImage(TApplication.user_avatar, iv_hearder_default, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    ((ImageView)view).setImageResource(R.mipmap.iv_hearder_default);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    if (TextUtils.isEmpty(TApplication.user_avatar)) {
                        ((ImageView) view).setImageResource(R.mipmap.iv_hearder_default);
                    } else {
                        ((ImageView)view).setImageBitmap(bitmap);
                    }
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (tv_userName != null && tv_score != null) {
            if (!TextUtils.isEmpty(TApplication.user_id)) {
                tv_score.setClickable(false);
                String score = TApplication.integral;
                if (TextUtils.isEmpty(score)) {
                    tv_score.setText("0积分");
                } else {
                    tv_score.setText(score + "积分");
                }
                tv_userName.setText(TApplication.user_name);
                ImageLoader.getInstance().displayImage(TApplication.user_avatar, iv_hearder_default, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        ((ImageView) view).setImageResource(R.mipmap.iv_hearder_default);
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        if (TextUtils.isEmpty(TApplication.user_avatar)) {
                            ((ImageView) view).setImageResource(R.mipmap.iv_hearder_default);
                        } else {
                            ((ImageView) view).setImageBitmap(bitmap);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {

                    }
                });
            }else {
                tv_userName.setText("登陆");
                tv_score.setClickable(true);
                tv_score.setText("注册");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startResumTime(new Date());
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
        StatService.onResume(this);
        getVersionCode();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
        StatService.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LuPingDestory("main", "page", "end", new Date());
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
     * 设置升级对话框
     */
    private void setUpdataDialog() {
        dialog = new AlertDialog.Builder(this);
        dialog.setMessage("您的App有新的版本,请升级后再使用");
        dialog.setTitle("提示");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        file= downLoadFile(filePath);
                        handler.sendEmptyMessage(5);
                    }
                }).start();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 获取版本信息
     */
    public void getVersionCode(){
        RefereshBiz biz = new RefereshBiz();
        biz.getVersionNam("android", this);
    }
    private void initSlideMenuView(View v) {
        v.findViewById(R.id.user_center_login_username).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.user_center_regist_score).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });
        v.findViewById(R.id.jifen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TApplication.user_id == null || TextUtils.isEmpty(TApplication.user_id)) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, MyScoreActivity.class);
                    startActivity(intent);
                }
            }
        });
        v.findViewById(R.id.pinglun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TApplication.user_id == null || TextUtils.isEmpty(TApplication.user_id)) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, MyChatActivity.class);
                    startActivity(intent);
                }
            }
        });
        v.findViewById(R.id.my_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(TApplication.user_id)) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, MyAccuntActivity.class);
                    startActivity(intent);
                }

            }
        });
        v.findViewById(R.id.setting_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MySettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setListeners() {
        user_center.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        main_local_tv.setOnClickListener(this);
    }

    private void initView() {
        user_center = (ImageView) findViewById(R.id.user_center);
        tv_search = (TextView) findViewById(R.id.tv_search);
        main_local_tv = (TextView) findViewById(R.id.main_local_tv);
        if (provence != null) {
            main_local_tv.setText(provence);
        } else {
            main_local_tv.setText("上海");
            TApplication.city_id="50";
        }
    }

    /**
     * 设置fragment
     * @param targetFragment
     */
    private void changeFragment(Fragment targetFragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, targetFragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_center:
                slidingMenu.toggle();
                break;
            case R.id.tv_search:
                Intent intent_search = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent_search);
                break;
            case R.id.main_local_tv:
                //此处要进行购物车选中数据判断
                Intent intent = new Intent(MainActivity.this, WelcomActivity.class);
                intent.putExtra("setLocation", true);
                startActivityForResult(intent, 200);
                break;
        }
    }

    @Override
    public void onResponse(Response response) {
        if (response.isSuccessful()) {
            String json = null;
            try {
                json = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            RefereshRoot root = gson.fromJson(json, RefereshRoot.class);
            if (root.getStatus() == 0) {
                net_verson_code = root.getData().get(0).getV_code();
                filePath = root.getData().get(0).getFilepath();
                handler.sendEmptyMessage(0);
            }
        }
    }

    @Override
    public void onFailue(Request request, IOException e) {

    }

    /**
     * 通过地址获取文件
     * @param httpUrl
     * @return
     */
    public File downLoadFile(String httpUrl) {
        final String fileName = "nq.apk";
        File tmpFile = new File("//sdcard");
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        final File file = new File("//sdcard//" + fileName);
        try {
            URL url = new URL(httpUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[256];
                conn.connect();
                double count = 0;
                if (conn.getResponseCode() >= 400) {

                } else {
                    while (count <= 100) {
                        if (is != null) {
                            int numRead = is.read(buf);
                            if (numRead <= 0) {
                                break;
                            } else {
                                fos.write(buf, 0, numRead);
                            }
                        } else {
                            break;
                        }
                    }
                }
                conn.disconnect();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 通过文件安装apk
     * @param file
     */
    private void openFile(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode==RESULT_OK) {
//            if (requestCode == 200) {
//                String provience_result = data.getStringExtra("provience");
//                main_local_tv.setText(provience_result);
//            }else if (requestCode == 300||requestCode==400||requestCode==500) {
//                if ("".equals(TApplication.user_id)) {
////                    user_center_login_username.setText("登陆");
////                    user_center_regist_score.setText("注册");
//                }else {
////                    if (TApplication.user != null) {
////                        user_center_login_username.setText(TApplication.user.getNickname());
////                    } else {
////                        user_center_login_username.setText(TApplication.user_name);
////                    }
//                }
//            }
//        }
//    }
}
